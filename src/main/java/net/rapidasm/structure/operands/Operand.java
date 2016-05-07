package net.rapidasm.structure.operands;

import net.rapidasm.BinarySource;
import net.rapidasm.arch.Architecture;
import net.rapidasm.asm.AsmOperand;
import net.rapidasm.asm.DataSource;
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
	
	public String getActualOperand() {
		return this.getAsmOperand().value;
	}
	
	public abstract DataSize getResultingDataSize();
	
	public abstract AsmOperand getAsmOperand();
	public abstract DataSource getSource();
	
}
