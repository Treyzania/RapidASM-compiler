package net.rapidasm.structure.subroutines;

import java.util.List;

import net.rapidasm.structure.symbols.RapidSymbol;

public class RapidSection {

	public String name;

	public List<RapidSubroutine> subroutines;
	public List<RapidSymbol> objects;

	public void addSubroutine(RapidSubroutine sub) {
		this.subroutines.add(sub);
	}
	
	public void addObject(RapidSymbol obj) {
		this.objects.add(obj);
	}
	
}
