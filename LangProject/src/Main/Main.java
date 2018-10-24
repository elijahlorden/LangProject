package main;

import java.io.IOException;
import java.util.BitSet;

import assembler.Assembler;
import assembler.DataLexer;

public class Main {
	
	
	
	public static void main(String[] args) throws IOException {
		System.out.println(Util.byteArrayToBinaryString(DataLexer.parseSWord("257", 0, true)));
		System.out.println(Util.byteArrayToHexString(DataLexer.parseSWord("257", 0, true), 5));
		Assembler asm = new Assembler("Program.asm");
		asm.lex();
		asm.compile();
	}

}
