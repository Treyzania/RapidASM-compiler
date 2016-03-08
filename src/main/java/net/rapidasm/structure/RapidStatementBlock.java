package net.rapidasm.structure;

import java.util.List;

import net.rapidasm.AsmLine;
import net.rapidasm.structure.subroutines.RapidStatement;
import net.rapidasm.structure.subroutines.RapidSubroutine;
import net.rapidasm.structure.subroutines.StatementBlockParent;

public class RapidStatementBlock extends RapidStatement implements Child<StatementBlockParent>, StatementBlockParent {

	private RapidSubroutine owningSubroutine;
	private RapidStatementBlock parentBlock;
	
	public List<RapidStatement> statements;
	
	public RapidStatementBlock(RapidSubroutine owner) {
		this.owningSubroutine = owner;
	}
	
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
	public StatementBlockParent getStructuralParent() {
		
		// Can return a null, but we won't know what to return otherwise.
		return this.owningSubroutine != null ? this.owningSubroutine : this.parentBlock;
		
	}
	
}
