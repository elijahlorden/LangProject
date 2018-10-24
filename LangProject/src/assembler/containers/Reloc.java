package assembler.containers;

public class Reloc {
	
	private int address;
	private String operation;
	/*
	 * Reloc operations:
	 * LINKLOCAL	- link to local symbol
	 * CONST1		- replace with 1-byte local constant
	 * CONST2		- replace with 2-byte local constant
	 * CONST3		- replace with 3-byte local constant
	 * LINKGLOBAL	- link to global symbol
	 */
	private String symbol;
	private Integer max;
	private Integer min;
	
	public Reloc(int address, String operation, String symbol) {
		this.address = address;
		this.operation = operation;
		this.symbol = symbol;
		this.max = Integer.MAX_VALUE;
		this.min = Integer.MIN_VALUE;
	}
	
	public Reloc(int address, String operation, String symbol, int min, int max) {
		this.address = address;
		this.operation = operation;
		this.symbol = symbol;
		this.max = max;
		this.min = min;
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
		if (min != null && max != null) {
			return String.format("%06X %s %s %d %d", address, operation, symbol, min, max);
		}
		return String.format("%06X %s %s", address, operation, symbol);
	}
	
}
