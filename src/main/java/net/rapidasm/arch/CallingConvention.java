package net.rapidasm.arch;

import net.rapidasm.BinarySource;
import net.rapidasm.structure.RapidSubroutine;

public abstract class CallingConvention {

	public final String name;
	
	public CallingConvention(String name) {
		
		this.name = name;
		
	}
	
	public abstract void doCallerSetup(RapidSubroutine caller, RapidSubroutine callee, BinarySource src);
	public abstract void doCallerCleanup(RapidSubroutine caller, RapidSubroutine callee, BinarySource src);
	public abstract void doCalleeSetup(RapidSubroutine callee, BinarySource src);
	public abstract void doCalleeCleanup(RapidSubroutine callee, BinarySource src);
	
}
