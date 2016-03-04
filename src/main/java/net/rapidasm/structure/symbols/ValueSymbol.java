package net.rapidasm.structure.symbols;

import net.rapidasm.antlr.RapidASMParser.QuantityContext;
import net.rapidasm.structure.DataSize;
import net.rapidasm.structure.context.ContextItem;

public class ValueSymbol extends Symbol {

	public String name;
	public DataSize size;
	public QuantityContext value;
	
	public ValueSymbol(String name, DataSize size, QuantityContext data) {
		
		this.name = name;
		this.size = size;
		this.value = data;
		
	}
	
	@Override
	public ContextItem getContextItem() {
		return null;
	}
	
	@Override
	public String toString() {
		return String.format("%s{name=%s, width=%s}", this.getClass().getSimpleName(), this.name, this.size.name());
	}
	
}
