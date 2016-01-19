package net.rapidasm.structure.symbols;

public enum SymbolType {

	VALUE("value", 3), // Generates operand 3, of width operand 1, and provides a pointer the the data to the context named operand 2.
	STORE("store", 2), // Generates operand 2, sized at operand 1
	SKIP("skip", 1), // Generates operand 1 null bytes.
	LABEL("label", 1); // Provides to the context a pointer named operand 1 to the *immediately following* instruction. 
	
	public String name;
	public int operandCount;
	
	private SymbolType(String name, int operands) {
		
		this.name = name;
		this.operandCount = operands;
		
	}
	
	public boolean usesEqualsSign() {
		return operandCount == 3;
	}
	
}
