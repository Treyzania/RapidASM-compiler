package net.rapidasm.structure.symbols;

import net.rapidasm.BinarySource;
import net.rapidasm.structure.Assemblable;
import net.rapidasm.structure.RapidSection;

public class SkipSymbol extends Symbol implements Assemblable {

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

	@Override
	public void addLines(BinarySource src) {
		src.addCode(String.format(".skip %s", this.bytesSkipped));
	}
	
}
