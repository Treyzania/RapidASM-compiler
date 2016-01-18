package net.rapidasm.structure.subroutines;

import java.util.ArrayList;
import java.util.List;

import net.rapidasm.structure.context.Context;
import net.rapidasm.structure.context.ContextItem;
import net.rapidasm.structure.context.ContextProvider;
import net.rapidasm.structure.symbols.RapidSymbol;

public class RapidSection implements ContextProvider {

	public String name;

	public List<RapidSubroutine> subroutines;
	public List<RapidSymbol> symbols;

	public void addSubroutine(RapidSubroutine sub) {
		this.subroutines.add(sub);
	}
	
	public void addObject(RapidSymbol obj) {
		this.symbols.add(obj);
	}

	@Override
	public boolean isBacktrackable() {
		return true;
	}

	@Override
	public Context getContext() {
		
		Context c = new Context();
		
		// Apply the other contexts.
		for (RapidSubroutine sub : this.subroutines) c = c.mergeContexts(sub.getContext());
		
		// Add symbols.
		List<ContextItem> symbolCIs = new ArrayList<ContextItem>();
		for (RapidSymbol symb : this.symbols) symbolCIs.add(symb.getContextItem());
		c = c.mergeContexts(new Context(symbolCIs));
		
		return c;
		
	}
	
}
