package net.rapidasm.structure;

import net.rapidasm.arch.Architecture;

public enum DataSize {

	BYTE(1, 'b', DataType.BYTE),
	SHORT(2, 's', DataType.SHORT),
	INTEGER(4, 'l', DataType.INTEGER),
	LONG(8, 'q', DataType.LONG);
	
	public int size = 0;
	public char suffix;
	
	public DataType dataType;
	
	private DataSize(int size, char suffix, DataType type) {
		
		this.size = size;
		this.suffix = suffix;
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
