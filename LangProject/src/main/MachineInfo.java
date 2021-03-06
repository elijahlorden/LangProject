package main;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MachineInfo {
	
	/***
	 * List legal register aliases
	 */
	@SuppressWarnings("serial")
	public static HashMap<String, Integer> registerAliases = new HashMap<String, Integer>() {{
			put("r0", 0); //r0 - r7 (Registers 0 - 7) are general purpose registers (nonvolatile)
			put("r1", 1);
			put("r2", 2);
			put("r3", 3);
			put("r4", 4);
			put("r5", 5);
			put("r6", 6);
			put("r7", 7);
			put("r8", 8);
			put("r9", 9);
			put("r10", 10);
			put("r11", 11);
			put("r12", 12);
			put("r13", 13);
			put("r14", 14);
			put("r15", 15);
			put("ip", 8); //ip (Register 8) is the instruction pointer
			put("sp", 9); //sp (Register 9) is the stack pointer
			
	}};
	
	/***
	 * List of legal opcodes
	 */
	@SuppressWarnings("serial")
	public static final Map<String, Integer> opcodes = new HashMap<String, Integer>() {{
			put("NOP", 0); //No operation
			put("LDI1", 1); //Load Immediate (1-byte)
			put("LDI2", 2); //Load Immediate (2-byte)
			put("LDI3", 3); //Load Immediate (3-byte)
			put("LDI1R", 4); //Load IP + Immediate (1-byte)
			put("LDI2R", 5); //Load IP + Immediate (2-byte)
			put("LDI3R", 6); //Load IP + Immediate (3-byte)
			put("MOV", 7); //Register transfer
	}};

}
