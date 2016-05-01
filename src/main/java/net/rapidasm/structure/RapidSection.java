package net.rapidasm.structure;

import java.util.ArrayList;
import java.util.List;

import net.rapidasm.BinarySource;
import net.rapidasm.Module;
import net.rapidasm.structure.symbols.RapidLabel;
import net.rapidasm.structure.symbols.Labelable;

public class RapidSection implements Child<Module>, Assemblable {

	private Module module;
	public String name;

	public List<SectionPopulant> children;
	
	public RapidSection(Module mod, String name) {
		
		this.module = mod;
		
		this.children = new ArrayList<>();
		this.name = name;
		
	}
	
	public void addChild(SectionPopulant sp) {
		this.children.add(sp);
	}
	
	@Override
	public Module getStructuralParent() {
		return this.module;
	}
	
	@Override
	public void addLines(BinarySource src) {
		
		src.addLabel("section_" + this.name); // A label to the section declaration.
		
		// FIXME Make this more generic.
		if (this.name.equals("TEXT")) {
			src.addCode(".text");
		} else if (this.name.equals("DATA")) {
			src.addCode(".data");
		} else if (this.name.equals("BSS")) {
			src.addCode(".bss");
		} else {
			src.addCode(".section ." + this.name.toLowerCase());
		}
		
		src.addSpace();
		
		for (SectionPopulant child : this.children) {
			
			// Put in the lines of code if the child supports them.
			if (child instanceof Assemblable) {
				
				Assemblable asm = (Assemblable) child;
				
				if (asm instanceof RapidSubroutine) {
					
					RapidSubroutine sub = (RapidSubroutine) asm;
					
					// FIXME Make this more generic.
					if (sub.name.equals("ENTRY")) {
						src.addCode(".globl _start");
					}
					
				}
				
				// Check for labels, generate them if so.
				if (asm instanceof Labelable) {
					
					List<RapidLabel> labels = ((Labelable) asm).getLabels();
					for (RapidLabel label : labels) {
						src.addLabel(label.getName());
					}
					
				}
				
				asm.addLines(src);
				
			}
			
		}
		
		src.addLabel("section_" + this.name + "_end");
		
	}
	
}
