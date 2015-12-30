package net.rapidasm.structure;

import java.util.List;

import net.rapidasm.AsmLine;
import net.rapidasm.structure.subroutines.RapidStatement;

public class RapidStatementBlock extends RapidStatement {

	public List<RapidStatement> statements;
	
	public void addStatement(RapidStatement statement) {
		this.statements.add(statement);
	}
	
	@Override
	public List<AsmLine> getAssembly() {
		
		return null;
		
	}

}
