package net.rapidasm.structure.subroutines;

import net.rapidasm.BinarySource;
import net.rapidasm.antlr.RapidASMParser.ConditionalBlockContext;
import net.rapidasm.structure.Child;
import net.rapidasm.structure.RapidStatementBlock;

public class RapidIfStatement extends RapidStatement implements Child<StatementBlockParent>, StatementBlockParent {

	private RapidStatementBlock parent, body;
	private ConditionalBlockContext context;
	
	public RapidIfStatement(RapidStatementBlock parent,  ConditionalBlockContext ctx) {
		
		this.parent = parent;
		this.body = new RapidStatementBlock(this.parent);
		
		this.context = ctx;
		
	}
	
	@Override
	public void addLines(BinarySource src) {
		
		// TODO If begin code
		src.addComment("IF AT : " + this.context.start.getLine());
		this.body.addLines(src);
		// TODO if end code
		
	}

	@Override
	public StatementBlockParent getStructuralParent() {
		return this.parent;
	}

	@Override
	public RapidStatementBlock getBody() {
		return this.body;
	}

}
