package net.rapidasm.structure;

import net.rapidasm.arch.Architecture;

public enum DataSize {

	BYTE(1, "db"),
	SHORT(2, "dw"),
	INTEGER(4, "dd"),
	LONG(8, "dq");
	
	public int size = 0;
	
	public String keyword;
	
	private DataSize(int size, String keyword) {
		
		this.size = size;
		this.keyword = keyword;
		
	}
	
	public static DataSize getSize(Architecture arch, String key) {
		
		String val = key.substring(1);
		
		if (val.equals("ptr")) return arch.getPointerSize();
		if (val.equals("str")) return BYTE; // Doesn't really matter at this point.
		
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
