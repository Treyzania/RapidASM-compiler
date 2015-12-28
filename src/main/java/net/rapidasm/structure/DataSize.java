package net.rapidasm.structure;

public enum DataSize {

	POINTER(-1),
	BYTE(1),
	SHORT(2),
	INTEGER(4),
	LONG(8);
	
	public int size;
	
	private DataSize(int size) {
		this.size = size;
	}
	
	public static DataSize getSize(String key) {
		
		String val = key.substring(1);
		
		if (val.equals("ptr")) return POINTER;
		
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
