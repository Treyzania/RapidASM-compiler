package net.rapidasm.structure.symbols;

import net.rapidasm.BinarySource;
import net.rapidasm.antlr.RapidASMParser.QuantityContext;
import net.rapidasm.structure.Assemblable;
import net.rapidasm.structure.DataType;
import net.rapidasm.structure.RapidSection;

public class ValueSymbol extends Symbol implements Assemblable {

	public String name;
	public DataType type;
	public QuantityContext value;
	
	public ValueSymbol(RapidSection parent, String name, DataType size, QuantityContext data) {
		
		super(parent);
		
		this.name = name;
		this.type = size;
		this.value = data;
		
	}
	
	@Override
	public String toString() {
		return String.format("%s{name=%s, width=%s}", this.getClass().getSimpleName(), this.name, this.type.name());
	}

	@Override
	public void addLines(BinarySource src) {
		
		String expression = this.value.getText();
		
		if (expression.matches("\\[.*\\]")) { // [ blah ]
			expression = expression.substring(1, expression.length() - 1).trim().replace('!', '.');
		}
		
		src.addLabel(this.name);
		src.addCode(String.format(
			".%s %s",
			this.type.definer,
			expression
		));
		
	}
	
}
