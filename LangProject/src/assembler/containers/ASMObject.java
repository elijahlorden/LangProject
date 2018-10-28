package assembler.containers;

import java.util.ArrayList;
import java.util.HashMap;

import main.Util;

public class ASMObject {
	
	private ArrayList<ObjectChunk> chunks;
	private byte[] compiledObject;
	private ArrayList<Reloc> compiledRelocs;
	
	private HashMap<String, Integer> symbolLocs;
	private HashMap<String, byte[]> constants;
	
	private int compilePointer;
	
	public ASMObject() {
		this.chunks = new ArrayList<ObjectChunk>();
		this.constants = new HashMap<String, byte[]>();
		this.symbolLocs = new HashMap<String, Integer>();
	}
	
	/***
	 * Add a discrete chunk to the object
	 * @param o
	 */
	public void addChunk(ObjectChunk o) {
		chunks.add(o);
	}
	
	/***
	 * Add a constant to the list of constants
	 * @param label the symbol of the constant
	 * @param value the value of the constant
	 * @param ln the current line number
	 */
	public void addConstant(String label, byte[] value, int ln) {
		if (constants.containsKey(label)) Util.error("Assembler", "Cannot redefine constant '" + label + "'", ln);
		constants.put(label, value);
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
		 * - add its location to the symbol map
		 * - translate and add its relocs to the relocs list
		 */
		for (ObjectChunk c : chunks) {
			c.setZero(compilePointer); //Set the chunk address to the next address
			symbolLocs.put(c.getLabel(), compilePointer); //Store the location of this chunk
			appendByteArray(c.getChunk()); //Append the chunk to the byte array
			compilePointer += c.length(); //Set the next address to the byte after the chunk
		}
		
	}
	
	/***
	 * Get the combined length of all chunks in this object (in bytes)
	 * @return the length of all chunks in this object (in bytes)
	 */
	private int getAllChunksLength() {
		int l = 0;
		for (ObjectChunk c : chunks) {
			l += c.length();
		}
		return l;
	}
	
	/***
	 * Append a byte array to the compiled byte array
	 * @param arr the array to be appended
	 */
	private void appendByteArray(byte[] arr) {
		for (int i=0; i<arr.length; i++) {
			compiledObject[compilePointer+i] = arr[i];
		}
	}
	
	/***
	 * Print out a hex dump of the compiled object array
	 */
	public void dump() {
		System.out.printf("DUMP: %d bytes\n", compiledObject.length);
		System.out.print(Util.byteArrayToHexString(compiledObject, 10));
	}
	
	
	
	
	
}
