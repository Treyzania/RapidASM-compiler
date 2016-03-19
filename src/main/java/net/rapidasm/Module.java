package net.rapidasm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.rapidasm.structure.Assemblable;
import net.rapidasm.structure.Headerable;
import net.rapidasm.structure.RapidSection;

public class Module implements Assemblable, Headerable {

	public File filename;
	
	public List<RapidSection> sections;
	
	public Module(File file) {
		
		this.filename = file;
		
		this.sections = new ArrayList<>();
		
	}

	@Override
	public void addLines(BinarySource src) {
		
		// Add beginning stuff.
		src.addComment("Generated from " + this.filename.getPath());
		// TODO Add some generic stuff derived from the arch.
		
		// Put in the sections.
		for (RapidSection sec : this.sections) {
			
			sec.addLines(src);
			src.addSpace();
			
		}
		
	}
	
}
