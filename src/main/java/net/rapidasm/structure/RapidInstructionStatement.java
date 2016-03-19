package net.rapidasm.structure;

import net.rapidasm.BinarySource;
import net.rapidasm.antlr.RapidASMParser.InstructionContext;

public class RapidInstructionStatement extends RapidStatement implements Child<RapidStatementBlock> {

	private RapidStatementBlock parent;
	public InstructionContext context;
	
	public RapidInstructionStatement(RapidStatementBlock parent, InstructionContext context) {
		
		this.parent = parent;
		this.context = context;
		
	}
	
	@Override
	public void addLines(BinarySource src) {
		
		String instruction = this.context.ALPHANUM().getText();
		
		String args = this.context.instructionArgs().getText().trim();
		if (args.length() == 0) {
			src.addCode(instruction);
		} else {
			src.addCode(String.format("%s %s", instruction, args));
		}
		
	}

	@Override
	public RapidStatementBlock getStructuralParent() {
		return this.parent;
	}
	
}
