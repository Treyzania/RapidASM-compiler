package net.rapidasm.structure.symbols;

import net.rapidasm.structure.DataSize;
import net.rapidasm.structure.context.ContextItem;

public class StoreSymbol extends Symbol {

	public DataSize size;
	public long data;
	
	public StoreSymbol(DataSize size, long data) {
		
		this.size = size;
		this.data = data;
		
	}

	@Override
	public ContextItem getContextItem() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		return String.format("%s{width=%s, value=%s}", this.getClass().getSimpleName(), this.size, this.data);
	}
	
}
