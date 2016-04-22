package net.rapidasm.structure.loops;

import net.rapidasm.BinarySource;
import net.rapidasm.antlr.RapidASMParser.WhileBlockBeforeContext;
import net.rapidasm.arch.Instruction;
import net.rapidasm.structure.RapidStatementBlock;
import net.rapidasm.structure.conditionals.BranchGenerationType;
import net.rapidasm.structure.conditionals.Conditional;
import net.rapidasm.structure.conditionals.Likelihood;

public class RapidWhileBeforeBlock extends RapidWhileBlock {

	private WhileBlockBeforeContext context;
	
	public RapidWhileBeforeBlock(RapidStatementBlock parent, BranchGenerationType type, Likelihood like, Conditional cond, WhileBlockBeforeContext ctx) {
		
		super(parent, type, like, cond);
		
		this.context = ctx;
		
	}
	
	@Override
	public int getLine() {
		return this.context.start.getLine();
	}

	@Override
	public void addCodeLines(BinarySource src) {
		
		super.addCodeLines(src);
		src.addInstruction(Instruction.JUMP, this.getBeginLabel());
		
	}
	
}
