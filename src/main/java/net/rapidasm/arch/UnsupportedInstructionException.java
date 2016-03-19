package net.rapidasm.arch;

public class UnsupportedInstructionException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7459598183436662971L;
	
	public UnsupportedInstructionException(String string) {
		super(string);
	}
	
}
