package net.rapidasm.arch;

import net.rapidasm.structure.RapidSubroutine;

public class EmptyConvention extends CallingConvention {

	public EmptyConvention(String name) {
		super(name);
	}

	@Override
	public void getSetupInstructions(RapidSubroutine sub) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getCleanupInstructions(RapidSubroutine sub) {
		// TODO Auto-generated method stub

	}

}
