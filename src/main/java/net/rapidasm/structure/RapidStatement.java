package net.rapidasm.structure;

import java.util.List;

import net.rapidasm.structure.symbols.RapidLabel;
import net.rapidasm.structure.symbols.Labelable;

public abstract class RapidStatement implements Assemblable, Labelable {
	
	public List<RapidLabel> labels;
	
	public final RapidStatementBlock parent;
	
	public RapidStatement(RapidStatementBlock block) {
		this.parent = block;
	}
	
	@Override
	public void addLabel(RapidLabel symb) {
		this.labels.add(symb);
	}

	@Override
	public List<RapidLabel> getLabels() {
		return this.labels;
	}
	
}
