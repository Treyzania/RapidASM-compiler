package net.rapidasm.structure;

import java.util.List;

import net.rapidasm.structure.symbols.LabelSymbol;
import net.rapidasm.structure.symbols.Labelable;

public abstract class RapidStatement implements Assemblable, Labelable {
	
	public List<LabelSymbol> labels;
	
	public final RapidStatementBlock parent;
	
	public RapidStatement(RapidStatementBlock block) {
		this.parent = block;
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
