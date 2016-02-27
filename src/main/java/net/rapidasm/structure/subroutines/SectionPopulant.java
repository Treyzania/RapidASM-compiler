package net.rapidasm.structure.subroutines;

import java.util.ArrayList;
import java.util.List;

import net.rapidasm.structure.symbols.LabelSymbol;
import net.rapidasm.structure.symbols.Labelable;

public class SectionPopulant implements Labelable {

	private List<LabelSymbol> labels;
	
	public SectionPopulant() {
		
		this.labels = new ArrayList<>();
		
	}
	
	@Override
	public void addLabel(LabelSymbol symb) {
		this.labels.add(symb);
	}

	@Override
	public List<LabelSymbol> getLabels() {
		return this.labels;
	}

}
