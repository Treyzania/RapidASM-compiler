package net.rapidasm.structure.subroutines;

import java.util.List;

import net.rapidasm.structure.objects.RapidObject;

public class RapidSection {

	public String name;

	public List<RapidSubroutine> subroutines;
	public List<RapidObject> objects;

	public void addSubroutine(RapidSubroutine sub) {
		this.subroutines.add(sub);
	}
	
	public void addObject(RapidObject obj) {
		this.objects.add(obj);
	}
	
}
