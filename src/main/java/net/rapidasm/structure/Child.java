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
		
		if (type == null) throw new IllegalArgumentException("You can't pass a null to this!");
		
		Object currentNode = this.getStructuralParent();
		while (!type.isAssignableFrom(currentNode.getClass())) {
			
			if (currentNode instanceof Child) {
				
				// Where the magic happens.
				Object prevNode = currentNode;
				currentNode = ((Child<?>) currentNode).getStructuralParent();
				if (currentNode == null) throw new NullPointerException("The parent of this " + prevNode.getClass().getSimpleName() + " is apparently null!");
				
			} else {
				throw new NullPointerException("The current node isn't a Child<?>!  It's actually a " + currentNode.getClass().getSimpleName());
				//return null; // The current node can't have a parent, apparently.  Just return null.
			}
			
		}
		
		return currentNode;
		
	}
	
}
