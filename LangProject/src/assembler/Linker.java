package assembler;

import java.util.ArrayList;
import java.util.HashMap;

import assembler.containers.ASMObject;
import assembler.containers.DebugLabel;
import assembler.containers.Reloc;
import main.Util;

public class Linker {
	
	private byte[] compiledCode;
	private HashMap<String, Integer> symbols;
	private ArrayList<Reloc> relocs;
	private ArrayList<DebugLabel> debugLabels;
	
	private int compilePointer;
	
	public Linker() {
		this.compiledCode = new byte[0];
		this.symbols = new HashMap<String, Integer>();
		this.relocs = new ArrayList<Reloc>();
		this.debugLabels = new ArrayList<DebugLabel>();
		this.compilePointer = 0;
	}
	
	/***
	 * Add an object to the linker:
	 *  - Append its byte array to the internal byte array
	 *  - Translate and add its Relocs to the internal list of Relocs
	 *  - Translate and add its symbols to the internal list of symbols
	 *  - Translate and add its DebugLabels to the internal list of DebugLabels
	 * @param obj
	 */
	public void addObject(ASMObject obj) {
		compiledCode = Util.concatArrays(compiledCode, obj.getCompiledObject()); //Append it to the byte array
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}