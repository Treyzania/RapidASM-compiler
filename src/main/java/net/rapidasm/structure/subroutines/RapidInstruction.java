package net.rapidasm.structure.subroutines;

import net.rapidasm.BinarySource;
import net.rapidasm.arch.Instruction;
import net.rapidasm.structure.Assemblable;
import net.rapidasm.structure.RapidOperand;

public class RapidInstruction implements Assemblable {

	public Instruction type;
	public RapidOperand[] operands;
	
	@Override
	public void addLines(BinarySource src) {
		// TODO Auto-generated method stub
	}
	
}
