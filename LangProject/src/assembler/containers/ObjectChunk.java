package assembler.containers;

import java.util.ArrayList;

import main.Util;

public class ObjectChunk {
	
	private String label; //Label (symbol) of the chunk
	private byte[] chunk; //Raw chunk data
	private ArrayList<Reloc> relocs;
	
	private ArrayList<DebugLabel> debugLabels; //Label used in the hex debugger
	
	private int zero, internalPointer;
	
	public ObjectChunk(String label, byte[] chunk, ArrayList<Reloc> relocs) {
		this.label = label;
		this.chunk = chunk;
		this.relocs = relocs;
		this.zero = 0;
		this.internalPointer = 0;
		debugLabels = new ArrayList<DebugLabel>();
	}
	
	public ObjectChunk(String label, byte[] chunk) {
		this.label = label;
		this.chunk = chunk;
		this.relocs = new ArrayList<Reloc>();
		this.zero = 0;
		this.internalPointer = 0;
		debugLabels = new ArrayList<DebugLabel>();
	}
	
	public ObjectChunk(String label) {
		this.label = label;
		this.relocs = new ArrayList<Reloc>();
		this.zero = 0;
		this.internalPointer = 0;
		debugLabels = new ArrayList<DebugLabel>();
	}
	
	public void addParsedInstruction(ParsedInstruction ins, DebugLabel d) {
		if (chunk == null) chunk = new byte[0];
		if (ins.getReloc() != null) {
			ins.getReloc().setAddress(ins.getReloc().getAddress() + internalPointer); //Offset the reloc by the pointer
			relocs.add(ins.getReloc());
		}
		d.setAddress(d.getAddress() + internalPointer); //Offset the debugLabel by the pointer
		debugLabels.add(d);
		chunk = Util.concatArrays(chunk, ins.getChunk()); //Append instruction to byte array
		internalPointer += ins.getChunk().length;
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
	
	public void addDebugLabel(DebugLabel label) {
		debugLabels.add(label);
	}
	
	public ArrayList<DebugLabel> getDebugLabels() {
		return debugLabels;
	}
	
}
