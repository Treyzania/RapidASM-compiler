package net.rapidasm.structure;

import net.rapidasm.BinarySource;
import net.rapidasm.arch.Architecture;

public abstract class Operand {

	private final Architecture arch;
	
	protected Operand(Architecture arch) {
		this.arch = arch;
	}
	
	public abstract String getValue();
	
	// The cache here is the register we store it in before doing whatever with it, might not be necessary.
	public abstract void setup(String cache, BinarySource src);
	public abstract void cleanup(String cache, BinarySource src);
	
	/**
	 * 
	 * @return An instance of an Operand ready to be used.
	 */
	public static Operand getOperand(Architecture arch) { // FIXME TODO Make this work.
		return null;
	}
	
}
