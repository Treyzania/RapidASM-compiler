package net.rapidasm.structure.conditionals;

public enum Likelihood {

	LIKELY("likely"),
	UNLIKELY("unlikely");
	
	public String key;
	
	private Likelihood(String key) {
		this.key = key;
	}
	
}
