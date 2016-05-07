package net.rapidasm.structure;

import net.rapidasm.arch.Architecture;
import net.rapidasm.structure.operands.Operand;

public enum DataSize {

	BYTE(1, 'b'),
	SHORT(2, 's'),
	INTEGER(4, 'l'),
	LONG(8, 'q');
	
	public int size = 0;
	public char suffix;
	
	private DataSize(int size, char suffix) {
		
		this.size = size;
		this.suffix = suffix;
		
	}
	
	// Apparently Java doesn't like it when you have circular enum declarations.
	public DataType getType() {
		
		for (DataType dt : DataType.values()) {
			if (dt.size == this) return dt;
		}
		
		return null;
		
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
		for (DataSize ds : values()) {
			if (ds.size == bytes) return ds;
		}
		
		throw new IllegalArgumentException("Invalid data size: " + key);
		
	}
	
	public static DataSize getDataSize(Operand a, Operand b) {
		
		DataSize dsa = a.getResultingDataSize();
		DataSize dsb = b.getResultingDataSize();
		
		if (dsa == null && dsb == null) throw new IllegalArgumentException(String.format("Operands %s and %s both don't have obvious sizes!", a.getAsmOperand().value, b.getAsmOperand().value));
		if (dsa != null && dsb != null) {
			
			if (dsa == dsb) {
				return dsa; // Could do either.
			} else {
				throw new IllegalArgumentException(String.format("Operands %s and %s are incompatible.", a.getAsmOperand().value, b.getAsmOperand().value));
			}
			
		}
		
		if (dsb == null) return dsa;
		return dsb; // We want the left hand side usually.
		
	}
	
}
