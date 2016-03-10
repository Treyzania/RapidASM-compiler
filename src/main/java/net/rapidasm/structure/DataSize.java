package net.rapidasm.structure;

public enum DataSize {

	POINTER(-1, ""), // FIXME
	STRING(-1, "db"), // Still works the same as for byte, this is just to provide more context
	BYTE(1, "db"),
	SHORT(2, "dw"),
	INTEGER(4, "dd");
	//LONG(8);
	
	public int size;
	public String keyword;
	
	private DataSize(int size, String keyword) {
		
		this.size = size;
		this.keyword = keyword;
		
	}
	
	public static DataSize getSize(String key) {
		
		String val = key.substring(1);
		
		if (val.equals("ptr")) return POINTER;
		if (val.equals("str")) return STRING;
		
		int bytes = Integer.MIN_VALUE;
		
		try {
			bytes = Integer.parseInt(val);
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("Invalid data size: " + key);
		}
		
		// Find it or fail.
		for (DataSize ds : values()) if (ds.size == bytes) return ds;
		throw new IllegalArgumentException("Invalid data size: " + key);
		
	}
	
}
