package net.rapidasm.asm;

import net.rapidasm.structure.DataSize;

public class AsmOperand {
	
	public final String value;
	public final DataSize size;
	
	public AsmOperand(String value, DataSize size) {
		
		this.value = value;
		this.size = size;
		
	}
	
}
