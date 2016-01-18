package net.rapidasm.structure.symbols;

import net.rapidasm.structure.context.ContextItem;
import net.rapidasm.structure.context.ContextItemProvider;
import net.rapidasm.structure.context.ContextItemType;

public class RapidSymbol implements ContextItemProvider {

	public SymbolType type;
	public String[] operands;
	
	public RapidSymbol(SymbolType type, String[] opers) {
		
		this.type = type;
		this.operands = opers;
		
	}

	@Override
	public ContextItem getContextItem() {
		
		if (type == SymbolType.SKIP) return null;
		
		return new ContextItem(operands[1], ContextItemType.SPECIAL);
		
	}
	
}
