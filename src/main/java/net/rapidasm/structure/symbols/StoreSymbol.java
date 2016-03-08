package net.rapidasm.structure.symbols;

import net.rapidasm.structure.DataSize;
import net.rapidasm.structure.subroutines.RapidSection;

public class StoreSymbol extends StandaloneSymbol {

	public DataSize size;
	public long data;
	
	public StoreSymbol(RapidSection parent, DataSize size, long data) {
		
		super(parent);
		
		this.size = size;
		this.data = data;
		
	}
	
	@Override
	public String toString() {
		return String.format("%s{width=%s, value=%s}", this.getClass().getSimpleName(), this.size, this.data);
	}
	
}
