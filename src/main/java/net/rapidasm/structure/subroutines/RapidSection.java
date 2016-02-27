package net.rapidasm.structure.subroutines;

import java.util.ArrayList;
import java.util.List;

import net.rapidasm.structure.context.Context;
import net.rapidasm.structure.context.ContextItem;
import net.rapidasm.structure.context.ContextItemProvider;
import net.rapidasm.structure.context.ContextProvider;

public class RapidSection implements ContextProvider {

	public String name;

	public List<SectionPopulant> children;
	
	public void addChild(SectionPopulant sp) {
		this.children.add(sp);
	}
	
	@Override
	public boolean isBacktrackable() {
		return true;
	}

	@Override
	public Context getContext() {
		
		Context c = new Context();
		
		for (SectionPopulant sp : this.children) {
			
			if (sp instanceof ContextProvider) c = c.mergeContexts(((ContextProvider) sp).getContext());
			if (sp instanceof ContextItemProvider) {
				
				List<ContextItem> l = new ArrayList<>(); 
				l.add(((ContextItemProvider) sp).getContextItem());
				c = c.mergeContexts(new Context(l));
				
			}
			
		}
		
		return c;
		
	}
	
}
