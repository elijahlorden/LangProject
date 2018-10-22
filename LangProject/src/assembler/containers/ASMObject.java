package assembler.containers;

import java.util.ArrayList;

public class ASMObject {
	
	private ArrayList<ObjectChunk> chunks;
	private byte[] compiledObject;
	private ArrayList<Reloc> compiledRelocs;
	
	public ASMObject() {
		this.chunks = new ArrayList<ObjectChunk>();
	}
	
	/***
	 * Add a discrete chunk to the object
	 * @param o
	 */
	public void addChunk(ObjectChunk o) {
		chunks.add(o);
	}
	
	/***
	 * Compile the object
	 * - Concatenates all chunks into a single byte array
	 * - Translates all chunk relocs and adds them to a single list
	 */
	public void compile() {
		/*
		 * For each chunk
		 * - add it to the main byte array
		 * - translate and add its relocs to the relocs list
		 */
	}
	
	
	
}
