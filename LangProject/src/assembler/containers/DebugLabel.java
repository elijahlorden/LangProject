package assembler.containers;

import main.Enum.DebugType;

public class DebugLabel {
	
	private int address, length, ln;
	private String label, filename, lineText;
	
	private DebugType type;
	
	public DebugLabel(int address, int length, String label, String filename, DebugType type, int ln, String lineText) {
		this.address = address;
		this.length = length;
		this.label = label;
		this.type = type;
		this.filename = filename;
		this.ln = ln;
		this.lineText = lineText;
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

	public int getLn() {
		return ln;
	}

	public void setLn(int ln) {
		this.ln = ln;
	}

	public String getLineText() {
		return lineText;
	}

	public void setLineText(String lineText) {
		this.lineText = lineText;
	}
	
	
	
	
	
	
	
}
