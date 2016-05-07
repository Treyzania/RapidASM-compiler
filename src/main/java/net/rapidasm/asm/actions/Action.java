package net.rapidasm.asm.actions;

import java.util.List;

import net.rapidasm.arch.Architecture;
import net.rapidasm.asm.AsmInstruction;

public abstract class Action {
	
	public final Architecture arch;
	
	public Action(Architecture arch) {
		this.arch = arch;
	}
	
	public abstract List<AsmInstruction> getInstructions();
	
}
