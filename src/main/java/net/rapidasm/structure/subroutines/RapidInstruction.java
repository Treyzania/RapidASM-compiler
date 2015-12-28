package net.rapidasm.structure.subroutines;

import java.util.List;

import net.rapidasm.AsmLine;
import net.rapidasm.arch.Instruction;
import net.rapidasm.structure.Assemblable;
import net.rapidasm.structure.RapidOperand;

public class RapidInstruction implements Assemblable {

	public Instruction type;
	public RapidOperand[] operands;
	
	@Override
	public List<AsmLine> getAssembly() {
		
		return null;
		
	}
	
}
