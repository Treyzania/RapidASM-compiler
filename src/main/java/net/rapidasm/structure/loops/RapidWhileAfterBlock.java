package net.rapidasm.structure.loops;

import net.rapidasm.BinarySource;
import net.rapidasm.antlr.RapidASMParser.WhileBlockAfterContext;
import net.rapidasm.structure.RapidStatementBlock;
import net.rapidasm.structure.conditionals.BranchGenerationType;
import net.rapidasm.structure.conditionals.Conditional;
import net.rapidasm.structure.conditionals.Likelihood;

public class RapidWhileAfterBlock extends RapidWhileBlock {

	private WhileBlockAfterContext context;
	
	public RapidWhileAfterBlock(RapidStatementBlock parent, BranchGenerationType type, Likelihood like, Conditional cond, WhileBlockAfterContext ctx) {
		
		super(parent, type, like, cond);
		
		this.context = ctx;
		
	}

	@Override
	public void addConditionalLines(BinarySource src) {
		// TODO This code is a bit more interesting than before checks and regular branches.
	}

	@Override
	public int getLine() {
		return this.context.start.getLine();
	}

}
