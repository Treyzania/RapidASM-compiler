package net.rapidasm.structure.conditionals;


public class BranchProfile {
	
	public final BranchGenerationType type;
	public final Likelihood likelihood;
	public final Conditional conditional;
	
	public BranchProfile(BranchGenerationType type, Likelihood like, Conditional cond) {
		
		this.type = type;
		this.likelihood = like;
		this.conditional = cond;
		
	}
	
}
