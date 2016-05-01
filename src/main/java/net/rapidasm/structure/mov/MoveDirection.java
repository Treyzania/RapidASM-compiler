package net.rapidasm.structure.mov;


public enum MoveDirection {
	
	EXCHANGE(MoveType.EXHANGE, "<->"),
	RTL(MoveType.MOVE, "<-", "<="),
	LTR(MoveType.MOVE, "->", "=>");
	
	public MoveType moveType;
	public String[] tokens;
	
	private MoveDirection(MoveType type, String... tokens) {
		
		this.moveType = type;
		this.tokens = tokens;
		
	}
	
	public static MoveDirection getMoveDirection(String token) {
		
		for (MoveDirection dir : values()) {
			
			for (String t : dir.tokens) {
				
				if (t.equals(token)) return dir;
				
			}
			
		}
		
		return null;
		
	}
	
}
