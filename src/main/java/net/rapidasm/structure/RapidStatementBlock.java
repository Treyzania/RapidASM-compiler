package net.rapidasm.structure;

import java.util.ArrayList;
import java.util.List;

import net.rapidasm.AsmLine;
import net.rapidasm.structure.context.Context;
import net.rapidasm.structure.context.ContextItem;
import net.rapidasm.structure.context.ContextItemProvider;
import net.rapidasm.structure.context.ContextProvider;
import net.rapidasm.structure.subroutines.RapidStatement;

public class RapidStatementBlock extends RapidStatement implements ContextProvider {

	public List<RapidStatement> statements;
	
	public void addStatement(RapidStatement statement) {
		this.statements.add(statement);
	}
	
	@Override
	public List<AsmLine> getAssembly() {
		
		return null;
		
	}

	@Override
	public boolean isBacktrackable() {
		return false;
	}

	@Override
	public Context getContext() {
		
		Context c = new Context();
		List<ContextItem> items = new ArrayList<>();
		
		for (RapidStatement statement : this.statements) {
			
			if (statement instanceof ContextProvider) c = c.mergeContexts(((ContextProvider) statement).getContext());
			if (statement instanceof ContextItemProvider) items.add(((ContextItemProvider) statement).getContextItem());
			
		}
		
		// Individual context items.
		c = c.mergeContexts(new Context(items));
		
		return c;
		
	}

}
