package net.rapidasm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.rapidasm.arch.Architecture;
import net.rapidasm.structure.Assemblable;
import net.rapidasm.structure.Headerable;
import net.rapidasm.structure.RapidSection;
import net.rapidasm.structure.RapidStatementBlock;
import net.rapidasm.structure.RapidWhileBlock;
import net.rapidasm.structure.conditionals.Likelihood;
import net.rapidasm.structure.conditionals.RapidIfStatement;

public class Module implements Assemblable, Headerable {

	public final Architecture architecture;
	
	public List<RapidSection> sections;
	
	private Map<Class<? extends RapidStatementBlock>, Likelihood> defaultLikelyhoods;
	
	public Module(Architecture arch) {
		
		this.architecture = arch;
		
		this.sections = new ArrayList<>();
		
		this.defaultLikelyhoods = new HashMap<>();
		this.defaultLikelyhoods.put(RapidIfStatement.class, Likelihood.UNLIKELY);
		this.defaultLikelyhoods.put(RapidWhileBlock.class, Likelihood.LIKELY);
		
	}
	
	/**
	 * Sets the likelihood used by default by a given branching class, which is used if not otherwise specified.
	 * 
	 * @param clazz The class of the branching generator
	 * @param like The default likelihood
	 */
	public void setDefaultLikelihood(Class<? extends RapidStatementBlock> clazz, Likelihood like) {
		this.defaultLikelyhoods.put(clazz, like);
	}
	
	/**
	 * Gets the likelihood used by default for the given class, which is to be used if not otherwise specified.
	 * 
	 * @param clazz The class of the branching generator
	 * @return The default likelihood./
	 */
	public Likelihood getDefaulyLikelihood(Class<? extends RapidStatementBlock> clazz) {
		return this.defaultLikelyhoods.get(clazz);
	}
	
	@Override
	public void addLines(BinarySource src) {
		
		// Add beginning stuff.
		// TODO Add some generic stuff derived from the arch.
		
		// Put in the sections.
		for (RapidSection sec : this.sections) {
			
			src.addSpace();
			sec.addLines(src);
			
		}
		
		src.addCode(".ident \"RapidASM-compiler\"");
		
	}
	
}
