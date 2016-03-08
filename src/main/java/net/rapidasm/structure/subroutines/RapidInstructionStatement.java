package net.rapidasm.structure.subroutines;

import net.rapidasm.BinarySource;
import net.rapidasm.arch.Instruction;
import net.rapidasm.structure.RapidOperand;

public class RapidInstructionStatement extends RapidStatement {

	public Instruction type;
	public RapidOperand[] operands;
	
	@Override
	public void addLines(BinarySource src) {
		
	}
	
}
