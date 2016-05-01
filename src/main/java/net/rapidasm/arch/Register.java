package net.rapidasm.arch;

import net.rapidasm.structure.DataSize;

public class Register {

	public final String name;
	public final DataSize size;
	
	public Register(String name, DataSize size) {
		
		this.name = name;
		this.size = size;
		
	}
	
	public boolean isCompatible(Register other) {
		return this.size == other.size;
	}
	
	public static boolean isCompatible(Register a, Register b) {
		return a.isCompatible(b);
	}

	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		return result;
		
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Register other = (Register) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (size != other.size)
			return false;
		return true;
		
	}
	
}
