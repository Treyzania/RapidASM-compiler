package net.rapidasm.structure;

import java.util.ArrayList;
import java.util.List;

import net.rapidasm.structure.symbols.RapidLabel;
import net.rapidasm.structure.symbols.Labelable;

public class SectionPopulant implements Child<RapidSection>, Labelable {

	private RapidSection parent;
	private List<RapidLabel> labels;
	
	public SectionPopulant(RapidSection parent) {
		
		this.parent = parent;
		
		this.labels = new ArrayList<>();
		
	}
	
	@Override
	public void addLabel(RapidLabel symb) {
		this.labels.add(symb);
	}

	@Override
	public List<RapidLabel> getLabels() {
		return this.labels;
	}

	@Override
	public RapidSection getStructuralParent() {
		return this.parent;
	}

}
