package net.rapidasm.asm.actions;

import net.rapidasm.arch.Architecture;
import net.rapidasm.asm.AsmSequencer;

public abstract class Action {
	
	public final Architecture arch;
	
	public Action(Architecture arch) {
		this.arch = arch;
	}
	
	public abstract void addInstructions(AsmSequencer seq);
	
}
