package net.rapidasm.structure.symbols;

public class LabelSymbol extends Symbol {

	private String name;
	
	public LabelSymbol(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
}
