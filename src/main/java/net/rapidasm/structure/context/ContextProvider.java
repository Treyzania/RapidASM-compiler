package net.rapidasm.structure.context;

public interface ContextProvider {

	/**
	 * 
	 * @return If can be looked at deeper in the tree in addition to further out.
	 */
	public boolean isBacktrackable();
	
	/**
	 * 
	 * @return The Context object representing all the "things" that are available in the thing.
	 */
	public Context getContext();
	
}
