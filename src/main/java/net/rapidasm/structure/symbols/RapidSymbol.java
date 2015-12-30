package net.rapidasm.structure.symbols;

public class RapidSymbol {

	public SymbolType type;
	public String[] operands;
	
	public RapidSymbol(SymbolType type, String[] opers) {
		
		this.type= type;
		this.operands = opers;
		
	}
	
}
