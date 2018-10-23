package main;

import java.io.IOException;
import java.util.BitSet;

import assembler.Assembler;
import assembler.DataLexer;

public class Main {
	
	
	
	public static void main(String[] args) throws IOException {
		System.out.println(Util.byteArrayToString(DataLexer.parseSWord("8388606", 0, false)));
		Assembler asm = new Assembler("Program.asm");
		asm.lex();
	}

}
