package net.rapidasm.structure.symbols;

import net.rapidasm.structure.Child;
import net.rapidasm.structure.RapidSection;

public class StandaloneSymbol extends Symbol implements Child<RapidSection> {

	private RapidSection parentSection;
	
	public StandaloneSymbol(RapidSection parent) {
		this.parentSection = parent;
	}
	
	@Override
	public RapidSection getStructuralParent() {
		return this.parentSection;
	}

}
