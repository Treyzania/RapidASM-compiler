package net.rapidasm.structure.symbols;

import java.util.List;

public interface Labelable {

	public void addLabel(RapidLabel symb);
	public List<RapidLabel> getLabels();
	
}
