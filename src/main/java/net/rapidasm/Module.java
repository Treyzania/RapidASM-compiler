package net.rapidasm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.rapidasm.structure.subroutines.RapidSection;

public class Module {

	public File filename;
	
	public List<RapidSection> sections;
	
	public Module() {
		this.sections = new ArrayList<>();
	}
	
	public HeaderFile getHeader() {
		
		// TODO Header file generation.
		return null;
		
	}
	
}
