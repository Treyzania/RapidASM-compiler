package net.rapidasm.structure.symbols;

import net.rapidasm.BinarySource;
import net.rapidasm.structure.Assemblable;
import net.rapidasm.structure.DataSize;
import net.rapidasm.structure.subroutines.RapidSection;

public class StoreSymbol extends StandaloneSymbol implements Assemblable {

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
	
	@Override
	public void addLines(BinarySource src) {
		
		src.addCode(String.format(
			"%s %s",
			this.size.keyword,
			Long.toString(this.data)
		));
		
	}
	
}
