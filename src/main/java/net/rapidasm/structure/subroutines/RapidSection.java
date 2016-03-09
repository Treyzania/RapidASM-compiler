package net.rapidasm.structure.subroutines;

import java.util.ArrayList;
import java.util.List;

import net.rapidasm.BinarySource;
import net.rapidasm.Module;
import net.rapidasm.structure.Assemblable;
import net.rapidasm.structure.Child;
import net.rapidasm.structure.symbols.LabelSymbol;
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
		
		String actualName = this.name.toLowerCase();
		
		if (this.name.equals("TEXT")) {
			actualName = "text";
		} else if (this.name.equals("DATA")) {
			actualName = "data";
		} else if (this.name.equals("BSS")) {
			actualName = "bss";
		}
		
		src.addLabel("rsm_section_" + this.name);
		src.addCode("section." + actualName);
		
		for (SectionPopulant child : this.children) {
			
			// Put in the lines of code if the child supports them.
			if (child instanceof Assemblable) {
				
				Assemblable asm = (Assemblable) child;
				
				// Check for labels, generate them if so.
				if (asm instanceof Labelable) {
					
					List<LabelSymbol> labels = ((Labelable) asm).getLabels();
					for (LabelSymbol label : labels) {
						src.addLabel(label.getName());
					}
					
				}
				
				asm.addLines(src);
				src.addSpace();
				
			}
			
		}
		
		src.addLabel("rsm_section_" + this.name + "_end");
		
	}
	
}
