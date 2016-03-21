package net.rapidasm.structure.conditionals;

import net.rapidasm.BinarySource;
import net.rapidasm.antlr.RapidASMParser.ConditionalBlockContext;
import net.rapidasm.structure.RapidStatementBlock;

public class RapidIfStatement extends RapidStatementBlock {

	private ConditionalBlockContext context;
	
	public RapidIfStatement(RapidStatementBlock parent, ConditionalBlockContext ctx) {
		
		super(parent);
		
		this.context = ctx;
		
	}
	
	@Override
	public void addLines(BinarySource src) {
		
		// TODO If begin code
		src.addComment("IF AT : " + this.context.start.getLine());
		super.addLines(src); // The code for the block itself.
		// TODO If end code
		
	}
	
}
