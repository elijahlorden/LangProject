package assembler.containers;

public class Reloc {
	
	public static final String LINKLOCAL = "LINKLOCAL";
	public static final String CONST1 = "CONST1";
	public static final String CONST2 = "CONST2";
	public static final String CONST3 = "CONST3";
	public static final String LINKGLOBAL = "LINKGLOBAL";
	
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
	private Integer ln;
	
	public Reloc(int address, String operation, String symbol, int ln) {
		this.address = address;
		this.operation = operation;
		this.symbol = symbol;
		this.max = Integer.MAX_VALUE;
		this.min = Integer.MIN_VALUE;
		this.ln = ln;
	}
	
	public Reloc(int address, String operation, String symbol, int min, int max, int ln) {
		this.address = address;
		this.operation = operation;
		this.symbol = symbol;
		this.max = max;
		this.min = min;
		this.ln = ln;
	}

	public int getAddress() {
		return address;
	}
	
	public int getLn() {
		return ln;
	}
	
	public void setAddress(int address) {
		this.address = address;
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
