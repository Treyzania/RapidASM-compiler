package net.rapidasm.arch;

public class InstructionType {

	public final String name;
	public final int operandCount; 
	
	public InstructionType(String name, int operandCount) {
		
		this.name = name;
		this.operandCount = operandCount;
		
	}
	
}
