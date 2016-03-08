package net.rapidasm.structure.symbols;

import net.rapidasm.antlr.RapidASMParser.QuantityContext;
import net.rapidasm.structure.DataSize;
import net.rapidasm.structure.subroutines.RapidSection;

public class ValueSymbol extends StandaloneSymbol {

	public String name;
	public DataSize size;
	public QuantityContext value;
	
	public ValueSymbol(RapidSection parent, String name, DataSize size, QuantityContext data) {
		
		super(parent);
		
		this.name = name;
		this.size = size;
		this.value = data;
		
	}
	
	@Override
	public String toString() {
		return String.format("%s{name=%s, width=%s}", this.getClass().getSimpleName(), this.name, this.size.name());
	}
	
}
