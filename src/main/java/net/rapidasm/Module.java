package net.rapidasm;

import java.io.File;
import java.util.List;

import net.rapidasm.structure.subroutines.RapidSection;

public class Module {

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
	
}
