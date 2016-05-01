package net.rapidasm.structure;

import net.rapidasm.arch.Architecture;

public enum DataType {

	BYTE("byte", DataSize.BYTE),
	SHORT("hword", DataSize.SHORT),
	INTEGER("int", DataSize.INTEGER), // Probably not going to work right on everything.
	LONG("quad", DataSize.LONG),
	STRING("ascii", DataSize.BYTE),
	STRING_ZERO_TERM("asciz", DataSize.BYTE);
	
	public String definer;
	public DataSize size;
	
	private DataType(String def, DataSize size) {
		
		this.definer = def;
		this.size = size;
		
	}
	
	public DataSize getSize(Architecture arch) {
		
		//if (this.size != null) return this.size;
		//return null; // FIXME
		return this.size;
		
	}
	
	public static DataType getType(Architecture arch, String key) {
		
		try {
			
			DataSize ds = DataSize.getSize(arch, key);
			
			return ds.getType();
			
		} catch (Exception e) {
			
			String val = key.substring(1);
			
			if (val.equals("str")) return STRING;
			if (val.equals("string")) return STRING_ZERO_TERM;
			
			// Ruled out everything else at this point.
			throw new IllegalArgumentException("Invalid data type: " + key);
			
		}
		
	}
	
}
