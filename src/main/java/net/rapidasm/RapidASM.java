package net.rapidasm;

import net.rapidasm.structure.RapidSection;
import net.rapidasm.structure.RapidSubroutine;
import net.rapidasm.structure.SectionPopulant;
import net.rapidasm.structure.symbols.Symbol;

public class RapidASM {
	
	public static void main(String[] args) {
		
		String fileName = args[0];
		
		ModuleBuilder mb = new ModuleBuilder();
		Module mod = mb.createModule(fileName);
		
		System.out.println("done: " + mod);
		System.out.println("sections: (" + mod.sections.size() + ")");
		for (RapidSection s : mod.sections) {
			
			System.out.println("\tsection: " + s.name);
			
			for (SectionPopulant child : s.children) {
				
				if (child instanceof RapidSubroutine) {
					
					RapidSubroutine rs = (RapidSubroutine) child;
					System.out.println("\t\tsubroutine: " + rs.name + " (" + rs.callingConvention.name + ")");
					
				} else if (child instanceof Symbol) {
					System.out.println("\t\tsymbol: " + child.toString());
				} else {
					System.out.println("\t\tthing: " + child.getClass().getSimpleName() + " " + child.toString());
				}
				
			}
			
		}
		
		BinarySource bs = new BinarySource(mod.architecture);
		mod.addLines(bs);
		
		System.out.println("\n========\n");
		System.out.println(bs.toString());
		
	}
	
}
