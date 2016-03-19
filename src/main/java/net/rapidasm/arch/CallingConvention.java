package net.rapidasm.arch;

import net.rapidasm.BinarySource;
import net.rapidasm.structure.RapidSubroutine;
import net.rapidasm.structure.Signature;

public abstract class CallingConvention {

	public final Architecture arch;
	public final String name;
	
	public CallingConvention(Architecture arch, String name) {
		
		this.arch = arch;
		this.name = name;
		
	}
	
	public abstract void doCallerSetup(RapidSubroutine caller, RapidSubroutine callee, BinarySource src);
	public abstract void doCallerCleanup(RapidSubroutine caller, RapidSubroutine callee, BinarySource src);
	public abstract void doCalleeSetup(RapidSubroutine callee, BinarySource src);
	public abstract void doCalleeCleanup(RapidSubroutine callee, BinarySource src);
	
	public abstract String getArgumentExpression(Signature sig, String argName);
	
}
