package net.rapidasm.structure;

import java.util.ArrayList;
import java.util.List;

import net.rapidasm.BinarySource;

public class RapidStatementBlock extends RapidStatement implements Child<StatementBlockParent>, StatementBlockParent {

	private RapidSubroutine owningSubroutine;
	
	public List<RapidStatement> statements;
	
	public RapidStatementBlock(RapidSubroutine owner) {
		
		super(null);
		this.owningSubroutine = owner;
		
		this.statements = new ArrayList<>();
		
	}
	
	public RapidStatementBlock(RapidStatementBlock parent) {
		
		super(parent);
		this.statements = new ArrayList<>();
		
	}
	
	public void addStatement(RapidStatement statement) {
		this.statements.add(statement);
	}
	
	@Override
	public StatementBlockParent getStructuralParent() {
		
		// Can return a null, but we won't know what to return otherwise.
		return this.owningSubroutine != null ? this.owningSubroutine : this.parent;
		
	}

	@Override
	public void addLines(BinarySource src) {
		
		src.addComment("BLOCK: " + this.hashCode() + " (" + this.statements.size() + ", parent:" + this.getStructuralParent().getClass().getSimpleName() + ")");
		
		for (RapidStatement rs : this.statements) {
			rs.addLines(src);
		}
		
		src.addComment("END BLOCK: " + this.hashCode());
		
	}

	@Override
	public RapidStatementBlock getBody() {
		return this;
	}
	
}
