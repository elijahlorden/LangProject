package assembler.containers;

import java.util.ArrayList;

public class ASMObject {
	
	private ArrayList<ObjectChunk> chunks;
	private byte[] compiledObject;
	private ArrayList<Reloc> compiledRelocs;
	
	public ASMObject() {
		this.chunks = new ArrayList<ObjectChunk>();
	}
	
	public void addChunk(ObjectChunk o) {
		chunks.add(o);
	}
	
	public void compile() {
		/*
		 * For each chunk
		 * - add it to the main byte array
		 * - translate and add its relocs to the relocs table
		 * After compilation
		 * - Execute any 'LINKLOCAL' relocs
		 */
	}
	
	
	
}
