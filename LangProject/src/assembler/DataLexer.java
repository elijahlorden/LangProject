package assembler;

import main.Util;

public class DataLexer {
	public static byte[] translateData(String data, String type, int ln) {
		Boolean unsigned = false;
		byte[] rawData = null;
		if (type.charAt(0) == '_') {
			unsigned = true;
			type = type.substring(1);
		}
		switch(type) {
		case "byte":
			rawData =  getByte(data, unsigned, ln);
		case "word":
			break;
		case "sword":
			break;
		case "ascii":
			if (unsigned) Util.error("Assembler", "Type 'ascii' cannot be declared as unisgned", ln);
			break;
		case "asciiz":
			if (unsigned) Util.error("Assembler", "Type 'asciiz' cannot be declared as unisgned", ln);
			break;
		case "block":
			if (unsigned) Util.error("Assembler", "Type 'block' cannot be declared as unisgned", ln);
			break;
		default:
			Util.error("Assembler", "Unknown data type '" + type + "'", ln);
			break;
		}
		return rawData;
	}
	
	private static byte parseByte(String data, int ln, boolean unsigned) { //1-byte values
		if (data.charAt(0) == '\'') {
			if (data.charAt(data.length()-1) != '\'' || data.length() > 3) Util.error("Assembler", "Malformed character", ln);
			return (byte) data.charAt(1);
		}
		if (unsigned) {
			return (byte) (Integer.parseInt(data) & 0xFF);
		} else {
			return Byte.parseByte(data);
		}
	}
	
	private static byte[] parseWord(String data, int ln, boolean unsigned) { //2-byte values
		Short n;
		if (unsigned) {
			n = (short) (Integer.parseInt(data) & 0xFFFF);
		} else {
			n = Short.parseShort(data);
		}
		return new byte[] {(byte) (n & 0xFF00), (byte) (n & 0x00FF)};
	}
	
	public static byte[] parseSWord(String data, int ln, boolean unsigned) { //3-byte values
		if (unsigned) {
			Integer n = Integer.parseUnsignedInt(data);
			return new byte[] {(byte) (n & 0xFF0000), (byte) (n & 0x00FF00), (byte) (n & 0x0000FF)};
		} else {
			Integer n = Integer.parseInt(data);
			n = n << 23; //Arithmatic left shift to join the sword with the sign bit
			n = n >>> 23; //Logical right shift to fix the position of the sword
			return new byte[] {(byte) (n & 0xFF0000), (byte) (n & 0x00FF00), (byte) (n & 0x0000FF)};
		}
	}
	
	private static byte[] getByte(String data, boolean unsigned, int ln) {
		byte[] out = null;
		try {
			if (data.indexOf(',') != -1) {
				String[] parts = data.split(",");
				out = new byte[parts.length];
				for (int i=0;i<parts.length;i++) out[i] = parseByte(parts[i], ln, unsigned);
			} else {
				out = new byte[] {parseByte(data, ln, unsigned)};
			}
			
		} catch (NumberFormatException e) {
			Util.error("Assembler", "Malformed byte", ln);
		}
		return out;
	}
	
	
	
	
	
	
	
	
}
