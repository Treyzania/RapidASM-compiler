package net.rapidasm.structure;

import net.rapidasm.arch.Architecture;

public enum DataSize {

	BYTE(1, DataType.BYTE),
	SHORT(2, DataType.SHORT),
	INTEGER(4, DataType.INTEGER),
	LONG(8, DataType.LONG);
	
	public int size = 0;
	
	public DataType dataType;
	
	private DataSize(int size, DataType type) {
		
		this.size = size;
		this.dataType = type;
		
	}
	
	public static DataSize getSize(Architecture arch, String key) {
		
		String val = key.substring(1);
		
		if (val.equals("ptr")) return arch.getPointerSize();
		
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
