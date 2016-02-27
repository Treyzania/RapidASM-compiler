package net.rapidasm.structure.symbols;

import java.util.List;

public interface Labelable {

	public void addLabel(LabelSymbol symb);
	public List<LabelSymbol> getLabels();
	
}
