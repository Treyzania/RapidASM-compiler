package net.rapidasm;

import java.io.File;

import net.rapidasm.antlr.RapidWalkerControler;

public class ModuleBuilder {

	public ModuleBuilder() {
		
	}
	
	public Module createModule(String filename) {
		return this.createModule(new File(filename));
	}
	
	public Module createModule(File file) {
		
		// TODO ANTLR stuff to do this.
		
		RapidWalkerControler sg = new RapidWalkerControler();
		
		return null;
		
	}
	
	
	
}
