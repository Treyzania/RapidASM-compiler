package net.rapidasm.structure.context;

import java.util.List;

public class Context {

	private List<ContextItem> context;
	
	public Context() {
		
	}
	
	public Context(List<ContextItem> items) {
		this.context = items;
	}
	
	public Context mergeContexts(Context other) {
		return mergeContexts(this, other);
	}
	
	public static Context mergeContexts(Context a, Context b) {
		
		List<ContextItem> totalItems = a.context;
		totalItems.addAll(b.context);
		
		return new Context(totalItems);
		
	}
	
}
