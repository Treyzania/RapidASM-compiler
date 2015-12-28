package net.rapidasm.arch;

public class Instruction {

	public final String name;
	public final int operandCount; 
	
	public Instruction(String name, int operandCount) {
		
		this.name = name;
		this.operandCount = operandCount;
		
	}
	
}
