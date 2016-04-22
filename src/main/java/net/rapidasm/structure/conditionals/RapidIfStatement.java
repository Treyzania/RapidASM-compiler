package net.rapidasm.structure.conditionals;

import net.rapidasm.antlr.RapidASMParser.ConditionalBlockContext;
import net.rapidasm.structure.RapidBranchingStatement;
import net.rapidasm.structure.RapidStatementBlock;

public class RapidIfStatement extends RapidBranchingStatement {

	private ConditionalBlockContext context;
	
	public RapidIfStatement(RapidStatementBlock parent, BranchGenerationType type, Likelihood like, Conditional cond, ConditionalBlockContext ctx) {
		
		super(parent, type, like, cond);
		
		this.context = ctx;
		
	}
	
	@Override
	public int getLine() {
		return this.context.start.getLine();
	}

	@Override
	public String getBlockLabelPrefix() {
		return "branch";
	}
	
}
