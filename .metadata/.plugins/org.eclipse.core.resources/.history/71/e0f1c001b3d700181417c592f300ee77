package assembler.containers;

public class Reloc {
	
	private int address;
	private String operation;
	/*
	 * Reloc operations:
	 * LINKLOCAL	- link to local symbol
	 * LINKGLOBAL	- link to global symbol
	 */
	private String symbol;
	
	public Reloc(int address, String operation, String symbol) {
		this.address = address;
		this.operation = operation;
		this.symbol = symbol;
	}

	public int getAddress() {
		return address;
	}

	public String getOperation() {
		return operation;
	}

	public String getSymbol() {
		return symbol;
	}
	
	@Override
	public String toString() {
		return String.format("%06X %s %s", address, operation, symbol);
	}
	
}
