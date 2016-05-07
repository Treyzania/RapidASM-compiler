package net.rapidasm.asm;


public class AsmLabel {
	
	public final String name;
	protected AsmInstruction reference;
	
	public AsmLabel(String name) {
		this.name = name;
	}
	
	public AsmInstruction getInstruction() {
		return this.reference;
	}
	
	public static enum Type {
		
		DEFINED,
		COMPILER;
		
	}
	
}
