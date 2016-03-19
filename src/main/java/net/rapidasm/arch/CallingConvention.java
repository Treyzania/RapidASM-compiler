package net.rapidasm.arch;

import net.rapidasm.structure.RapidSubroutine;

public abstract class CallingConvention {

	public final String name;
	
	public CallingConvention(String name) {
		
		this.name = name;
		
	}
	
	// TODO Change how these store info, they currently can't do anything.
	public abstract void getSetupInstructions(RapidSubroutine sub);
	public abstract void getCleanupInstructions(RapidSubroutine sub);
	
}
