package assembler.containers;

import java.util.ArrayList;

public class ObjectChunk {
	
	private String label; //Label (symbol) of the chunk
	private byte[] chunk; //Raw chunk data
	private ArrayList<Reloc> relocs;
	
	public ObjectChunk(String label, byte[] chunk, ArrayList<Reloc> relocs) {
		this.label = label;
		this.chunk = chunk;
		this.relocs = relocs;
	}
	
	public ObjectChunk(String label, byte[] chunk) {
		this.label = label;
		this.chunk = chunk;
		this.relocs = new ArrayList<Reloc>();
	}

	public String getLabel() {
		return label;
	}

	public byte[] getChunk() {
		return chunk;
	}

	public ArrayList<Reloc> getRelocs() {
		return relocs;
	}
	
	
	
	
	
	
	
	
	
	
}