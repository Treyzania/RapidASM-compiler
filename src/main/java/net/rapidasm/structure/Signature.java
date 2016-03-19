package net.rapidasm.structure;

import java.util.ArrayList;
import java.util.List;

public class Signature {

	private List<Vararg> arguments;
	
	public Signature() {
		
		this.arguments = new ArrayList<>();
		
	}
	
	public void addVararg(Vararg arg) {
		this.arguments.add(arg);
	}
	
	public int getTotalSize() {
		
		// Just sum up everything and return the sum.
		int sum = 0;
		for (Vararg arg : this.arguments) sum += arg.size.size;
		return sum;
		
	}
	
}
