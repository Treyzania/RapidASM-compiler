package net.rapidasm.arch;

public class Register {

	public final String name;
	public final int width;
	
	public Register(String name, int width) {
		
		this.name = name;
		this.width = width;
		
	}
	
	public boolean isCompatible(Register other) {
		return this.width == other.width;
	}
	
	public static boolean isCompatible(Register a, Register b) {
		return a.isCompatible(b);
	}

	@Override
	public int hashCode() {
		
		// Yucky generated code.
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + width;
		return result;
		
	}

	@Override
	public boolean equals(Object obj) {
		
		// Yucky generated code.
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
		if (width != other.width)
			return false;
		return true;
		
	}
	
}
