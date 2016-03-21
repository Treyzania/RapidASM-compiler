package net.rapidasm.structure.symbols;

import net.rapidasm.BinarySource;
import net.rapidasm.antlr.RapidASMParser.QuantityContext;
import net.rapidasm.structure.Assemblable;
import net.rapidasm.structure.DataSize;
import net.rapidasm.structure.RapidSection;

public class ValueSymbol extends Symbol implements Assemblable {

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

	@Override
	public void addLines(BinarySource src) {
		
		// FIXME The actual value storing doesn't exactly work right at all.
		src.addCode(String.format(
			"%s %s %s",
			this.name,
			this.size.keyword,
			this.value.getText()
		));
		
	}
	
}
