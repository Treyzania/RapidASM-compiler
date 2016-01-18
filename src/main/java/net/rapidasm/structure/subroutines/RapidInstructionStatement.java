package net.rapidasm.structure.subroutines;

import java.util.List;

import net.rapidasm.AsmLine;
import net.rapidasm.arch.Instruction;
import net.rapidasm.structure.RapidOperand;

public class RapidInstructionStatement extends RapidStatement {

	public Instruction type;
	public RapidOperand[] operands;
	public String label;
	
	@Override
	public List<AsmLine> getAssembly() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
