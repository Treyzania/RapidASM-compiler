package net.rapidasm.structure.symbols;

import net.rapidasm.structure.subroutines.RapidSection;

public class SkipSymbol extends StandaloneSymbol {

	private int bytesSkipped;
	
	public SkipSymbol(RapidSection parent, int bytes) {
		
		super(parent);
		
		this.bytesSkipped = bytes;
		
	}
	
	public int getBytesSkipped() {
		return this.bytesSkipped;
	}
	
	@Override
	public String toString() {
		return String.format("%s{skipped=%s}", this.getClass().getSimpleName(), this.bytesSkipped);
	}
	
}
