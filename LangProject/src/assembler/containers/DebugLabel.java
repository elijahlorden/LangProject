package assembler.containers;

import main.Enum.DebugType;

public class DebugLabel {
	
	private int address, length;
	private String label, filename;
	
	private DebugType type;
	
	public DebugLabel(int address, int length, String label, String filename, DebugType type) {
		this.address = address;
		this.length = length;
		this.label = label;
		this.type = type;
		this.filename = filename;
	}

	public int getAddress() {
		return address;
	}
	
	public void setAddress(int address) {
		this.address = address;
	}
	
	public int getLength() {
		return length;
	}

	public String getLabel() {
		return label;
	}

	public DebugType getType() {
		return type;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	
	
	
	
	
}
