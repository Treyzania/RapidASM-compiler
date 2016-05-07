package net.rapidasm.structure;

import net.rapidasm.BinarySource;

public interface Assemblable {

	@Deprecated
	public void addLines(BinarySource src);
	
	// FIXME This probably needs to be reworked. 
	//public List<Action> getActions();
	
}
