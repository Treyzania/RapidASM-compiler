package net.rapidasm.structure;

import net.rapidasm.Module;

public interface Child<T> {

	public T getStructuralParent();
	
	public default Module getRootModule() {
		return (Module) this.getFirstEncounteredAncestorOfType(Module.class);
	}
	
	public default RapidSubroutine getContainingSubroutine() {
		return (RapidSubroutine) this.getFirstEncounteredAncestorOfType(RapidSubroutine.class);
	}
	
	public default RapidSection getContainingSection() {
		return (RapidSection) this.getFirstEncounteredAncestorOfType(RapidSection.class);
	}
	
	public default Object getFirstEncounteredAncestorOfType(Class<?> type) {
		
		Object currentNode = this.getStructuralParent();
		
		while (!type.isAssignableFrom(currentNode.getClass())) {
			
			if (currentNode instanceof Child) {
				
				// Where the magic happens.
				currentNode = ((Child<?>) currentNode).getStructuralParent();
				
			} else {
				return null; // The current node can't have a parent, apparently.  Just return null.
			}
			
		}
		
		return currentNode;
		
	}
	
}
