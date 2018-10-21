package assembler;

import java.util.HashMap;

public class ObjectChunk {
	
	private String label; //Label (symbol) of the chunk
	private byte[] chunk; //Raw chunk data
	private HashMap<Integer, String> localRefs; //Positions in the chunk (start of 3-byte addresses) which reference a local symbol
	private HashMap<Integer, String> globalRefs; //Positions in the chunk (start of 3-byte addresses) which reference a global symbol
	
	public ObjectChunk(String label, byte[] chunk, HashMap<Integer, String> localRefs, HashMap<Integer, String> globalRefs) {
		this.label = label;
		this.chunk = chunk;
		this.localRefs = localRefs;
		this.globalRefs = globalRefs;
	}
	
	public ObjectChunk(String label, byte[] chunk) {
		this.label = label;
		this.chunk = chunk;
		this.localRefs = new HashMap<Integer, String>();
		this.globalRefs = new HashMap<Integer, String>();
	}
	
	
	
	
	
	
	
	
	
	
}
