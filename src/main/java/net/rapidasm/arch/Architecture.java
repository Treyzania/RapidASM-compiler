package net.rapidasm.arch;

public abstract class Architecture {

	// TODO Add list of instructions.
	
	/**
	 * 
	 * @return The size of a pointers in bytes.
	 */
	public abstract int getPointerSize();
	
	/**
	 * 
	 * @return The maximum bytes of memory that can be addressed. 
	 */
	public abstract long getMaximumAddressableMemory();
	
	/**
	 * 
	 * @return The size of a word in bytes.
	 */
	public abstract int getWordSize(); 
	
	/**
	 * 
	 * @return The short, common name of this architecture.
	 */
	public abstract String getShortName();
	
}
