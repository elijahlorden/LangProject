package assembler;

import assembler.containers.ParsedInstruction;
import assembler.containers.Reloc;
import main.MachineInfo;
import main.Util;

public class InstructionParser {
	
	public static final String[] byteLengthMappings = {"N/A", "byte", "word", "sword"};
	
	/***
	 * Parse an instruction into an ObjectChunk, relocs included when necessary
	 * @param line whitespace-delimited input line
	 * @param ln current line number
	 * @return the input parsed into an ObjectChunk
	 */
	public static ParsedInstruction translateInstruction(String line, int ln) {
		String[] parts = line.split("\\s+");
		String ins = parts[0].toUpperCase();
		try {
			return (
				(ins.substring(0, 3).equals("LDI")) ? parseLDI(parts, ln) : //Parse LDI instruction
				(ins.substring(0, 3).equals("LDA")) ? parseLDA(parts, ln) : //Parse LDA instruction
				
				
				
				
				
				
				
				null
			);
		} catch(NumberFormatException e) {
			Util.error("Assembler", "Malformed number", ln);
		}
		return null;
	}
	
	/***
	 * Get a register id from the input
	 * Will accept register aliases prefixed with a dollar sign ($)
	 * @param s the string to translate
	 * @return the input string translated into a register address
	 */
	public static byte getRegister(String s, int ln) throws NumberFormatException {
		if (s.length() < 1) Util.error("Assembler", "Incorrectly formatted register ID/alias", ln);
		if (s.charAt(0) == '$') {
			Integer mapping = MachineInfo.registerAliases.get(s.substring(1));
			if (mapping != null) {
				return (byte) (mapping & 0xFF);
			} else {
				Util.error("Assembler", "Unknown register alias", ln);
				return 0;
			}
		} else {
			return (byte) (Integer.parseInt(s) & 0xFF);
		}
	}
	
	/***
	 * Get the binary register argument for the provided register ID
	 * @param r register ID to translate
	 * @return the register ID translated into a register argument
	 */
	public static byte[] getRegisterArg(byte r) {
		return new byte[] {(byte)(r << 4)};
	}
	
	/***
	 * Get the binary register argument for the provided register IDs
	 * @param r1 first register ID to translate
	 * @param r2 second register ID to translate
	 * @return the register IDs translated into a register argument
	 */
	public static byte[] getRegisterArg(byte r1, byte r2) {
		return new byte[] {(byte) ((byte)((r1 << 4) & 0xF0) | (byte)(r2 & 0x0F))};
	}
	
	/***
	 * Get the binary register argument for the provided register IDs
	 * @param r1 first register ID to translate
	 * @param r2 second register ID to translate
	 * @param r3 third register ID to translate
	 * @return the register IDs translated into a register argument
	 */
	public static byte[] getRegisterArg(byte r1, byte r2, byte r3) {
		return new byte[] {(byte) ((byte)((r1 << 4) & 0xF0) | (byte)(r2 & 0x0F)), (byte)(r3 << 4)};
	}
	
	/***
	 * Check if the provided string can be safely parsed into an integer
	 * @param s the string to check
	 * @return true if the string can be parsed into an integer, otherwise false
	 */
	public static boolean isNumber(String s) {
		try {
			Integer.parseInt(s);	
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	/***
	 * Parse an LDI (Load immediate) instruction
	 * Format: LDI Register ImmediateValue
	 * Register may be either a number or register alias (ex. r0, sp)
	 * ImmediateValue must be an immediate constant (1-byte, 2-byte, or 3-byte)
	 * @param line whitespace-delimited input line
	 * @param ln current line number
	 * @return the input parsed into a ParsedInstruction
	 */
	public static ParsedInstruction parseLDI(String[] line, int ln) throws NumberFormatException {
		if (line.length < 3) Util.error("Assembler", "Incorrectly formatted LDI instruction", ln);
		byte[] regArg = getRegisterArg(getRegister(line[1], ln));
		Integer al, opcode;
		if (line[0].length() >= 4 && line[0].charAt(3) != '_' && line[0].charAt(3) != 'U') {
			al = Integer.parseInt(String.valueOf(line[0].charAt(3)));
			String op = (al == 1) ? "LDI1" : (al == 2) ? "LDI2" : (al == 3) ? "LDI3" : "ERR";
			if (op.equals("ERR")) Util.error("Assembler", "LDI cannot load more than 3 bytes", ln);
			opcode = MachineInfo.opcodes.get(op);
		} else {
			al = 1; //default to 1 byte argument
			opcode = MachineInfo.opcodes.get("LDI1"); //default to LDI1 (LDI == LDI1)
		}
		boolean isUnsigned = (line[0].charAt(line[0].length()-1) == '_' || line[0].charAt(line[0].length()-1) == 'U'); //LDIX_ & LDIXU == unsigned LDI
		int maxN = (isUnsigned) ? ((int)Math.pow(2,(al*8)))-1 : ((int)Math.pow(2,((al*8)-1)))-1; //max immediate number
		int minN = (isUnsigned) ? 0 : -((int)Math.pow(2,((al*8)-1))); //min immediate number
		if (isNumber(line[2])) {
			int n = Integer.parseInt(line[2]);
			if (n > maxN || n < minN) Util.error("Assembler", "Immediate operand out of bounds", ln);
			byte[] nBytes = DataParser.parseType(byteLengthMappings[al], line[2], ln, isUnsigned);
			return new ParsedInstruction(Util.concatArrays(new byte[] {(byte) opcode.intValue()}, regArg, nBytes));
		} else {
			String relocOp = (al == 1) ? "CONST1" : (al == 2) ? "CONST2" : (al == 3) ? "CONST3" : "ERR";
			if (relocOp.equals("ERR")) Util.error("Assembler", "Unknown error creating reloc", ln);
			Reloc reloc = new Reloc(2, relocOp, line[2], minN, maxN, ln); //Reloc will start at byte 3
			byte[] space = new byte[al];
			return new ParsedInstruction(Util.concatArrays(new byte[] {(byte) opcode.intValue()}, regArg, space), reloc);
		}
	}
	
	/***
	 * Parse an LDA (Load address) instruction
	 * Format: LDA Register Symbol
	 * Register may be either a number or register alias (ex. r0, sp)
	 * Symbol may refer to any local symbol (ex. someSymbol), or a global symbol by addressing another file (ex. someFile.someSymbol)
	 * @param line whitespace-delimited input line
	 * @param ln current line number
	 * @return the input parsed into a ParsedInstruction
	 */
	public static ParsedInstruction parseLDA(String[] line, int ln) throws NumberFormatException {
		if (line.length < 3) Util.error("Assembler", "Incorrectly formatted LDA instruction", ln);
		byte[] regArg = getRegisterArg(getRegister(line[1], ln));
		boolean isGlobal = line[2].contains(".");
		if (line[2].contains(".")) { //Parse global reloc
			Reloc reloc = new Reloc(2, Reloc.LINKGLOBAL, line[2], ln);
			return new ParsedInstruction(new byte[] {(byte) MachineInfo.opcodes.get("LDI2").intValue(), regArg[0], 0, 0, 0}, reloc);
		} else { //Parse local reloc
			Reloc reloc = new Reloc(2, Reloc.LINKLOCAL, line[2], ln);
			return new ParsedInstruction(new byte[] {(byte) MachineInfo.opcodes.get("LDI3").intValue(), regArg[0], 0, 0}, reloc);
		}
	}
	
	
	
	
	
	
}
