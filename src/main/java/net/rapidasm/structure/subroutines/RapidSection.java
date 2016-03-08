package net.rapidasm.structure.subroutines;

import java.util.ArrayList;
import java.util.List;

import net.rapidasm.Module;
import net.rapidasm.structure.Child;

public class RapidSection implements Child<Module> {

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
	
}
