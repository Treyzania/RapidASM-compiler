package net.rapidasm.structure;

import net.rapidasm.arch.Architecture;

public enum DataType {

	BYTE("byte"),
	SHORT("hword"),
	INTEGER("int"), // Probably not going to work right on everything.
	LONG("quad"),
	STRING("ascii"),
	STRING_ZERO_TERM("asciz");
	
	public String definer;
	
	private DataType(String def) {
		
		this.definer = def;
		
	}
	
	public static DataType getType(Architecture arch, String key) {
		
		try {
			return DataSize.getSize(arch, key).dataType;
		} catch (Exception e) {
			
			String val = key.substring(1);
			
			if (val.equals("str")) return STRING;
			if (val.equals("string")) return STRING_ZERO_TERM;
			
			// Ruled out everything else at this point.
			return null;
			
		}
		
	}
	
}
