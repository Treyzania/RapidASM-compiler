package net.rapidasm.structure.symbols;

import net.rapidasm.structure.context.ContextItem;

public class LabelSymbol extends RapidSymbol {

	private String name;
	
	public LabelSymbol(String name) {
		
		this.name = name;
		System.out.println("FOUND LABEL: " + this.getName());
		
	}
	
	public String getName() {
		return this.name;
	}

	@Override
	public ContextItem getContextItem() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
