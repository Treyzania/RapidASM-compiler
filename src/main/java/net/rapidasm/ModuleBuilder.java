package net.rapidasm;

import java.io.File;

public class ModuleBuilder {

	public ModuleBuilder() {
		
	}
	
	public Module createModule(String filename) {
		return this.createModule(new File(filename));
	}
	
	public Module createModule(File file) {
		
		// TODO ANTLR stuff to do this.
		
		return null;
		
	}
	
}
