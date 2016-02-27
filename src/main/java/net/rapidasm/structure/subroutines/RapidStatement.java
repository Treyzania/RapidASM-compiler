package net.rapidasm.structure.subroutines;

import java.util.List;

import net.rapidasm.structure.Assemblable;
import net.rapidasm.structure.symbols.LabelSymbol;
import net.rapidasm.structure.symbols.Labelable;

public abstract class RapidStatement implements Assemblable, Labelable {
	
	public List<LabelSymbol> labels;

	@Override
	public void addLabel(LabelSymbol symb) {
		this.labels.add(symb);
	}

	@Override
	public List<LabelSymbol> getLabels() {
		return this.labels;
	}
	
	// TODO Make children for conditionals, loops, instructions, etc.
	
	
}
