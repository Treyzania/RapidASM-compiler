package net.rapidasm.structure.operands;

import net.rapidasm.BinarySource;
import net.rapidasm.arch.Architecture;
import net.rapidasm.structure.DataSize;
import net.rapidasm.structure.RapidSubroutine;

public abstract class Operand {

	protected final Architecture arch;
	protected final RapidSubroutine subroutine;
	
	protected Operand(Architecture arch, RapidSubroutine sub) {
		
		this.arch = arch;
		this.subroutine = sub;
		
	}
	
	public void setup(BinarySource src) {
		
	}
	
	public void cleanup(BinarySource src) {
		
	}
	
	public abstract String getActualOperand();
	
	// The cache here is the register we store it in before doing whatever with it, might not be necessary.
	public abstract boolean needsRegisterCache();
	public void setCacheRegister(String registre) {
		
	}
	
	public abstract DataSize getResultingDataSize();
	
}
