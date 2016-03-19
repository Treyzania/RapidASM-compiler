package net.rapidasm.structure;

import java.util.ArrayList;
import java.util.List;

import net.rapidasm.BinarySource;

public class RapidStatementBlock extends RapidStatement implements Child<StatementBlockParent>, StatementBlockParent {

	private RapidSubroutine owningSubroutine;
	private RapidStatementBlock parentBlock;
	
	public List<RapidStatement> statements;
	
	public RapidStatementBlock() {
		this.statements = new ArrayList<>();
	}
	
	public RapidStatementBlock(RapidSubroutine owner) {
		
		this();
		System.out.println("SB WITH SUB");
		this.owningSubroutine = owner;
		
	}
	
	public RapidStatementBlock(RapidStatementBlock parent) {
		
		this();
		System.out.println("SB WITH BLOCK");
		this.parentBlock = parent;
		
	}
	
	public void addStatement(RapidStatement statement) {
		this.statements.add(statement);
	}
	
	@Override
	public StatementBlockParent getStructuralParent() {
		
		// Can return a null, but we won't know what to return otherwise.
		return this.owningSubroutine != null ? this.owningSubroutine : this.parentBlock;
		
	}

	@Override
	public void addLines(BinarySource src) {
		
		src.addComment("BLOCK: " + this.hashCode() + " (" + this.statements.size() + ")");
		
		for (RapidStatement rs : this.statements) {
			rs.addLines(src);
		}
		
		src.addComment("END BLOCK: " + this.hashCode());
		
	}

	@Override
	public RapidStatementBlock getBody() {
		return this; // Let's hope this doesn't cause any stack overflows.
	}
	
}
