package assembler.containers;

import java.util.ArrayList;

import main.Util;

public class ASMObject {
	
	private ArrayList<ObjectChunk> chunks;
	private byte[] compiledObject;
	private ArrayList<Reloc> compiledRelocs;
	
	private int compilePointer;
	
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
		compiledObject = new byte[getAllChunksLength()];
		compilePointer = 0;
		/*
		 * For each chunk
		 * - add it to the main byte array
		 * - translate and add its relocs to the relocs list
		 */
		for (ObjectChunk c : chunks) {
			c.setZero(compilePointer); //Set the chunk address to the next address
			appendByteArray(c.getChunk()); //Append the chunk to the byte array
			compilePointer += c.length(); //Set the next address to the byte after the chunk
		}
		
	}
	
	private int getAllChunksLength() {
		int l = 0;
		for (ObjectChunk c : chunks) {
			l += c.length();
		}
		return l;
	}
	
	private void appendByteArray(byte[] arr) {
		for (int i=0; i<arr.length; i++) {
			compiledObject[compilePointer+i] = arr[i];
		}
	}
	
	public void dump() {
		System.out.printf("DUMP: %d bytes\n", compiledObject.length);
		System.out.print(Util.byteArrayToHexString(compiledObject, 10));
	}
	
	
	
	
	
}
