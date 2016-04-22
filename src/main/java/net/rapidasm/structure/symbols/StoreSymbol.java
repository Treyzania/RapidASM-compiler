package net.rapidasm.structure.symbols;

import net.rapidasm.BinarySource;
import net.rapidasm.structure.Assemblable;
import net.rapidasm.structure.DataType;
import net.rapidasm.structure.RapidSection;

public class StoreSymbol extends Symbol implements Assemblable {

	public DataType type;
	public long data;
	
	public StoreSymbol(RapidSection parent, DataType size, long data) {
		
		super(parent);
		
		this.type = size;
		this.data = data;
		
	}
	
	@Override
	public String toString() {
		return String.format("%s{width=%s, value=%s}", this.getClass().getSimpleName(), this.type, this.data);
	}
	
	@Override
	public void addLines(BinarySource src) {
		
		src.addCode(String.format(
			".%s %s",
			this.type.definer,
			Long.toString(this.data)
		));
		
	}
	
}
