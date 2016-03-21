package net.rapidasm.structure;

import net.rapidasm.BinarySource;
import net.rapidasm.antlr.RapidASMParser.WhileBlockContext;

public class RapidWhileBlock extends RapidStatementBlock {

	private WhileBlockContext context;
	
	public RapidWhileBlock(RapidStatementBlock parent, WhileBlockContext ctx) {
		
		super(parent);
		
		this.context = ctx;
		
	}
	
	@Override
	public void addLines(BinarySource src) {
		
		// TODO If begin code
		src.addComment("LOOP AT : " + this.context.start.getLine());
		super.addLines(src); // The code for the block itself.
		// TODO If end code
		
	}
	
	
}
