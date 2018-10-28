package main;

import java.io.IOException;

import assembler.Assembler;
import assembler.DataParser;
import assembler.InstructionParser;
import assembler.containers.ParsedInstruction;

public class Main {
	
	
	
	public static void main(String[] args) throws IOException {
		//System.out.println(Util.byteArrayToBinaryString(DataLexer.parseSWord("257", 0, true)));
		//System.out.println(Util.byteArrayToHexString(DataLexer.parseSWord("257", 0, true), 5));
		//ParsedInstruction p = InstructionParser.parseLDI("LDI $r1 label".split(" "), 0);
		//System.out.println(Util.byteArrayToHexString(p.getChunk(),10));
		//System.out.println(p.getReloc());
		
		
		Assembler asm = new Assembler("Program.asm");
		asm.parse();
		asm.compile();
	}

}
