package assembler.containers;

import java.util.ArrayList;

public class ObjectChunk {
	
	private String label; //Label (symbol) of the chunk
	private byte[] chunk; //Raw chunk data
	private ArrayList<Reloc> relocs;
	
	private int zero;
	
	public ObjectChunk(String label, byte[] chunk, ArrayList<Reloc> relocs) {
		this.label = label;
		this.chunk = chunk;
		this.relocs = relocs;
		this.zero = 0;
	}
	
	public ObjectChunk(String label, byte[] chunk) {
		this.label = label;
		this.chunk = chunk;
		this.relocs = new ArrayList<Reloc>();
		this.zero = 0;
	}
	
	public ObjectChunk(String label) {
		this.label = label;
		this.relocs = new ArrayList<Reloc>();
		this.zero = 0;
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
	
	public void addReloc(Reloc r) {
		relocs.add(r);
	}
	
	public int length() {
		return chunk.length;
	}
	
	public int getZero() {
		return zero;
	}
	
	public void setZero(int zero) {
		this.zero = zero;
	}
	
	
	
	
	
}
