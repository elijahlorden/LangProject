package assembler.containers;

import java.util.ArrayList;
import java.util.HashMap;

import main.Util;

public class ASMObject {
	
	private ArrayList<ObjectChunk> chunks;
	private byte[] compiledObject;
	private ArrayList<Reloc> compiledRelocs;
	
	private ArrayList<DebugLabel> debugLabels;
	
	private HashMap<String, Integer> symbolLocs;
	private HashMap<String, byte[]> constants;
	
	private int compilePointer;
	
	public ASMObject() {
		this.chunks = new ArrayList<ObjectChunk>();
		this.constants = new HashMap<String, byte[]>();
		this.symbolLocs = new HashMap<String, Integer>();
		this.debugLabels = new ArrayList<DebugLabel>();
		this.compiledRelocs = new ArrayList<Reloc>();
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
			addRelocs(c.getRelocs(), compilePointer); //Translate and add any relocs to the object
			addDebugLabels(c.getDebugLabels(), compilePointer); //Translate and add the debug labels to the object
			compilePointer += c.length(); //Set the next address to the byte after the chunk
		}
	}
	
	/***
	 * Perform all constant relocs
	 * This method will execute any 'CONST#' relocs and remove them from the reloc list
	 * This must be done after compile and before the object is passed to the static linker
	 */
	public void doConstRelocs() {
		for (Reloc reloc : compiledRelocs) {
			byte[] constArray = constants.get(reloc.getSymbol());
			switch(reloc.getOperation()) {
			case Reloc.CONST1:
				if (constArray == null) Util.error("Assembler", "Constant '" + reloc.getSymbol() + "' does not exist");
				if (constArray.length > 1) Util.warn("Assembler", "Constant '" + reloc.getSymbol() + "' is of incorrect width for statement on line " + reloc.getLn() + " (The constant value will be truncated)");
				compiledObject[reloc.getAddress()] = constArray[0];
				compiledRelocs.remove(reloc);
				break;
			case Reloc.CONST2:
				if (constArray == null) Util.error("Assembler", "Constant '" + reloc.getSymbol() + "' does not exist");
				if (constArray.length > 2) Util.warn("Assembler", "Constant '" + reloc.getSymbol() + "' is of incorrect width for statement on line " + reloc.getLn() + " (The constant value will be truncated)");
				compiledObject[reloc.getAddress()] = constArray[0];
				compiledObject[reloc.getAddress()+1] = (constArray.length >= 2) ? constArray[1] : (byte) 0;
				compiledRelocs.remove(reloc);
				break;
			case Reloc.CONST3:
				if (constArray == null) Util.error("Assembler", "Constant '" + reloc.getSymbol() + "' does not exist");
				if (constArray.length > 3) Util.warn("Assembler", "Constant '" + reloc.getSymbol() + "' is of incorrect width for statement on line " + reloc.getLn() + " (The constant value will be truncated) BIT-WIDTHS OVER 24 ARE NOT SUPPORTED");
				compiledObject[reloc.getAddress()] = constArray[0];
				compiledObject[reloc.getAddress()+1] = (constArray.length >= 2) ? constArray[1] : (byte) 0;
				compiledObject[reloc.getAddress()+2] = (constArray.length >= 3) ? constArray[2] : (byte) 0;
				compiledRelocs.remove(reloc);
				break;
			default:
				break;
			}
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
	
	/***
	 * Get the compiled byte array
	 * @return the compiled byte array
	 */
	public byte[] getCompiledObject() {
		return compiledObject;
	}
	
	/***
	 * Get the list of DebugLabels for the compiled object
	 * @return the list of DebugLabels for the compiled object
	 */
	public ArrayList<DebugLabel> getDebugLabels() {
		return debugLabels;
	}
	
	/***
	 * Add DebugLabel objects to the compiled DenugLabel list
	 * @param labels the list of DebugLabel objects to add
	 * @param compilePointer pointer to the current compile address
	 */
	public void addDebugLabels(ArrayList<DebugLabel> labels, int compilePointer) {
		for (DebugLabel d : labels) {
			d.setAddress(d.getAddress() + compilePointer);
			debugLabels.add(d);
		}
	}
	
	/***
	 * Add Reloc objects to the compiled Reloc list
	 * @param relocs the list of Reloc objects to add
	 * @param compilePointer pointer to the current compile address
	 */
	public void addRelocs(ArrayList<Reloc> relocs, int compilePointer) {
		for (Reloc r : relocs) {
			r.setAddress(r.getAddress() + compilePointer);
			compiledRelocs.add(r);
		}
	}
	
}
