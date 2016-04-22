package net.rapidasm.structure;

import net.rapidasm.structure.conditionals.BranchGenerationType;
import net.rapidasm.structure.conditionals.Conditional;
import net.rapidasm.structure.conditionals.Likelihood;

public abstract class RapidWhileBlock extends RapidBranchingStatement {

	public RapidWhileBlock(RapidStatementBlock parent, BranchGenerationType type, Likelihood like, Conditional cond) {
		super(parent, type, like, cond);
	}
	
	@Override
	public String getBlockLabelPrefix() {
		return "loop";
	}
	
}
