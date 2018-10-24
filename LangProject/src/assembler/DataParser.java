package assembler;

import main.Util;

public class DataParser {
	
	/***
	 * Translates data into its binary representation
	 * @param data the string representation of the data to be translated
	 * @param type the type of data to translate
	 * @param ln the current line number
	 * @return binary representation of the data
	 */
	public static byte[] translateData(String data, String type, int ln) {
		Boolean unsigned = false;
		byte[] rawData = null;
		if (type.charAt(0) == '_') {
			unsigned = true;
			type = type.substring(1);
		}
		try {
			switch(type) {
			case "byte":
			case "word":
			case "sword":
				rawData =  getNumericData(data, type, unsigned, ln);
				break;
			case "ascii":
				if (unsigned) Util.error("Assembler", "Type 'ascii' cannot be declared as unisgned", ln);
				rawData = getTextData(data, ln, false);
				break;
			case "asciiz":
				if (unsigned) Util.error("Assembler", "Type 'asciiz' cannot be declared as unisgned", ln);
				rawData = getTextData(data, ln, true);
				break;
			case "block":
				if (unsigned) Util.error("Assembler", "Type 'block' cannot be declared as unisgned", ln);
				rawData = getBlock(data);
				break;
			default:
				Util.error("Assembler", "Unknown data type '" + type + "'", ln);
				break;
			}
		} catch (NumberFormatException e) {
			Util.error("Assembler", "Malformed data", ln);
		}
		//System.out.println();
		//Util.printArray(rawData);
		return rawData;
	}
	
	/***
	 * Attempts to parse a single byte from the input
	 * Will accept character values enclosed in single quotes (ex. 'a')
	 * @param data data to be parsed
	 * @param ln current line number
	 * @param unsigned if true, will parse the data as unsigned
	 * @return binary representation of the data
	 * @throws NumberFormatException
	 */
	public static byte[] parseByte(String data, int ln, boolean unsigned) throws NumberFormatException { //1-byte values
		if (data.charAt(0) == '\'') {
			if (data.charAt(data.length()-1) != '\'' || data.length() > 3) Util.error("Assembler", "Malformed character", ln);
			return new byte[] {(byte) data.charAt(1)};
		}
		if (unsigned) {
			return new byte[] {(byte) (Integer.parseInt(data) & 0xFF)};
		} else {
			return new byte[] {Byte.parseByte(data)};
		}
	}
	
	/***
	 * Attempts to parse a 2-byte word from the input
	 * @param data data to be parsed
	 * @param ln current line number
	 * @param unsigned if true, will parse the data as unsigned
	 * @return binary representation of the data
	 * @throws NumberFormatException
	 */
	public static byte[] parseWord(String data, int ln, boolean unsigned) throws NumberFormatException { //2-byte values
		Short n;
		if (unsigned) {
			n = (short) (Integer.parseInt(data) & 0xFFFF);
		} else {
			n = Short.parseShort(data);
		}
		return new byte[] {(byte) (n & 0xFF), (byte) ((n>>>8) & 0xFF)};
	}
	
	/***
	 * Attempts to parse a 3-byte word from the input
	 * @param data data to be parsed
	 * @param ln current line number
	 * @param unsigned if true, will parse the data as unsigned
	 * @return binary representation of the data
	 * @throws NumberFormatException
	 */
	public static byte[] parseSWord(String data, int ln, boolean unsigned) throws NumberFormatException { //3-byte values
		if (unsigned) {
			Integer n = Integer.parseUnsignedInt(data);
			return new byte[] {(byte) (n & 0xFF), (byte) ((n>>>8) & 0xFF), (byte) ((n>>>16) & 0xFF)};
		} else {
			Integer n = Integer.parseInt(data);
			n = n << 8; //Arithmatic left shift to join the sword with the sign bit
			n = n >>> 8; //Logical right shift to fix the position of the sword
			return new byte[] {(byte) (n & 0xFF), (byte) ((n>>>8) & 0xFF), (byte) ((n>>>16) & 0xFF)};
		}
	}
	
	/***
	 * Returns the correct parse method for the given type
	 * @param type the type of data to parse
	 * @param data the data to be parsed
	 * @param ln the current line number
	 * @param unsigned if true, will parse the data as unsigned
	 * @return binary representation of the data
	 * @throws NumberFormatException
	 */
	public static byte[] parseType(String type, String data, int ln, boolean unsigned) throws NumberFormatException {
		switch(type) {
			case "byte":
				return parseByte(data, ln, unsigned);
			case "word":
				return parseWord(data, ln, unsigned);
			case "sword":
				return parseSWord(data, ln, unsigned);
			default:
				Util.error("Assembler", "Unknown type" + type, ln);
				return null;
		}
	}
	
	/***
	 * Attempts to parse the input (numeric) into its binary representation
	 * Data can be entered in array format by delimiting elements with a comma (,)
	 * @param data data to be parsed
	 * @param type type of data to be parsed
	 * @param unsigned if true, will parse the data as unsigned
	 * @param ln current line number
	 * @return binary representation of the data
	 * @throws NumberFormatException
	 */
	public static byte[] getNumericData(String data, String type, boolean unsigned, int ln) throws NumberFormatException {
		byte[] out = new byte[0];
		if (data.indexOf(',') != -1) {
			String[] parts = data.split(",");
			for (int i=0;i<parts.length;i++) out = Util.concatArrays(out, parseType(type, parts[i], ln, unsigned));
		} else {
			out = parseType(type, data, ln, unsigned);
		}
		return out;
	}
	
	/***
	 * Attempts to parse the input (string) into its binary representation
	 * @param data the data to be parsed
	 * @param ln the current line number
	 * @param terminate if true, will terminate the string with a null character (ASCII 0)
	 * @return binary representation of the data
	 * @throws NumberFormatException
	 */
	public static byte[] getTextData(String data, int ln, boolean terminate) throws NumberFormatException {
		byte[] str = new byte[data.length()-2];
		if (data.charAt(0) != '"' || data.charAt(data.length()-1) != '"') Util.error("Assembler", "Malformed string", ln);
		for (int i=1; i<data.length()-1; i++) {
			str[i-1] = (byte) data.charAt(i);
		}
		if (terminate) {
			str = Util.concatArrays(str, new byte[] {0});
		}
		return str;
	}
	
	/***
	 * Returns an empty byte array for the given size
	 * @param data the size of byte array to create
	 * @return an empty byte array
	 * @throws NumberFormatException
	 */
	public static byte[] getBlock(String data) throws NumberFormatException {
		return new byte[Integer.parseInt(data)];
	}
	
	
	
	
}
