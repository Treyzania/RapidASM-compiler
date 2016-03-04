package net.rapidasm.structure.symbols;

import net.rapidasm.structure.context.ContextItem;

public class SkipSymbol extends Symbol {

	private int bytesSkipped;
	
	public SkipSymbol(int bytes) {
		this.bytesSkipped = bytes;
	}
	
	public int getBytesSkipped() {
		return this.bytesSkipped;
	}
	
	@Override
	public ContextItem getContextItem() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		return String.format("%s{skipped=%s}", this.getClass().getSimpleName(), this.bytesSkipped);
	}
	
}
