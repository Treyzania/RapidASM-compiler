package net.rapidasm.structure.loops;

import net.rapidasm.structure.RapidStatementBlock;
import net.rapidasm.structure.conditionals.BranchGenerationType;
import net.rapidasm.structure.conditionals.Conditional;
import net.rapidasm.structure.conditionals.Likelihood;
import net.rapidasm.structure.conditionals.RapidBranchingStatement;

public abstract class RapidWhileBlock extends RapidBranchingStatement {

	public RapidWhileBlock(RapidStatementBlock parent, BranchGenerationType type, Likelihood like, Conditional cond) {
		super(parent, type, like, cond);
	}
	
	@Override
	public String getBlockLabelPrefix() {
		return "loop";
	}
	
}
