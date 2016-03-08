package net.rapidasm.structure;

import java.util.List;

import net.rapidasm.AsmLine;
import net.rapidasm.structure.subroutines.RapidStatement;

public class RapidStatementBlock extends RapidStatement implements Child<RapidStatementBlock> {

	private RapidStatementBlock parentBlock;
	public List<RapidStatement> statements;
	
	public RapidStatementBlock(RapidStatementBlock parent) {
		this.parentBlock = parent;
	}
	
	public void addStatement(RapidStatement statement) {
		this.statements.add(statement);
	}
	
	@Override
	public List<AsmLine> getAssembly() {
		
		return null;
		
	}
	
	@Override
	public RapidStatementBlock getStructuralParent() {
		return this.parentBlock;
	}

}
