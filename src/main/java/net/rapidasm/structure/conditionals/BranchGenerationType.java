package net.rapidasm.structure.conditionals;

public enum BranchGenerationType {

	TRUE,
	FALSE,
	CONDITIONAL;
	
	public static BranchGenerationType getType(String parenExpression) {
		
		if (parenExpression.toLowerCase().equals("true")) return TRUE;
		if (parenExpression.toLowerCase().equals("false")) return FALSE;
		
		return CONDITIONAL;
		
	}
	
}
