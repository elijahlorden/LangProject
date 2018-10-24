package assembler.containers;

public class ParsedInstruction {
	
	private byte[] chunk;
	private Reloc reloc;
	
	public ParsedInstruction(byte[] chunk, Reloc reloc) {
		this.chunk = chunk;
		this.reloc = reloc;
	}
	
	public ParsedInstruction(byte[] chunk) {
		this.chunk = chunk;
		this.reloc = null;
	}

	public byte[] getChunk() {
		return chunk;
	}

	public Reloc getReloc() {
		return reloc;
	}
	
	
}
