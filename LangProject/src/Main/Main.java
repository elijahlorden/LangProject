package main;

import java.io.IOException;

import assembler.Assembler;

public class Main {
	
	
	
	public static void main(String[] args) throws IOException {
		Assembler asm = new Assembler("Program.asm");
		asm.lex();
	}

}
