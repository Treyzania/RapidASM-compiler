package net.rapidasm;

import net.rapidasm.structure.subroutines.RapidSection;

public class RapidASM {
	
	public static void main(String[] args) {
		
		String fileName = args[0];
		
		ModuleBuilder mb = new ModuleBuilder();
		Module mod = mb.createModule(fileName);
		
		System.out.println("done: " + mod);
		System.out.println("sections: (" + mod.sections.size() + ")");
		for (RapidSection s : mod.sections) {
			System.out.println("\t" + s.name);
		}
		
	}
	
}
