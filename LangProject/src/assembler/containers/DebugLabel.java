package assembler.containers;

import main.Enum.DebugType;

public class DebugLabel {
	
	private int address;
	private String label;
	
	private DebugType type;
	
	public DebugLabel(int address, String label, DebugType type) {
		this.address = address;
		this.label = label;
		this.type = type;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public DebugType getType() {
		return type;
	}

	public void setType(DebugType type) {
		this.type = type;
	}
	
	
	
	
	
	
	
}
