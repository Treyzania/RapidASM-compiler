package net.rapidasm;

import java.io.File;
import java.util.List;

import net.rapidasm.structure.context.Context;
import net.rapidasm.structure.context.ContextProvider;
import net.rapidasm.structure.subroutines.RapidSection;

public class Module implements ContextProvider {

	public File filename;
	
	public List<RapidSection> sections;
	
	public AsmFile getAsm() {
		
		// TODO Asm file generation.
		return null;
		
	}
	
	public HeaderFile getHeader() {
		
		// TODO Header file generation.
		return null;
		
	}

	@Override
	public boolean isBacktrackable() {
		return true; // Not sure if this makes sense here.
	}

	@Override
	public Context getContext() {
		
		Context c = new Context();
		for (RapidSection sec : this.sections) c = c.mergeContexts(sec.getContext());
		return c;
		
	}
	
}
