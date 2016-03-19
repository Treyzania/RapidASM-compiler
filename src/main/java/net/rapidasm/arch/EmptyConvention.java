package net.rapidasm.arch;

import net.rapidasm.BinarySource;
import net.rapidasm.structure.RapidSubroutine;

public class EmptyConvention extends CallingConvention {

	public EmptyConvention(String name) {
		super(name);
	}

	@Override
	public void doCallerSetup(RapidSubroutine caller, RapidSubroutine callee, BinarySource src) {
		
	}

	@Override
	public void doCallerCleanup(RapidSubroutine caller, RapidSubroutine callee, BinarySource src) {
		
	}

	@Override
	public void doCalleeSetup(RapidSubroutine callee, BinarySource src) {
		
	}

	@Override
	public void doCalleeCleanup(RapidSubroutine callee, BinarySource src) {
		
	}
	
}
