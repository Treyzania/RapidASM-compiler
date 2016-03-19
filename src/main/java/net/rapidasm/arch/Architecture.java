package net.rapidasm.arch;

import java.util.List;

public abstract class Architecture {

	// TODO Add list of instructions.
	
	/**
	 * 
	 * @return The size of a pointers in bytes.
	 */
	public abstract int getPointerSize();
	
	/**
	 * 
	 * @return The maximum bytes of memory that can be addressed. 
	 */
	public abstract long getMaximumAddressableMemory();
	
	/**
	 * 
	 * @return The size of a word in bytes.
	 */
	public abstract int getWordSize(); 
	
	/**
	 * 
	 * @return The short, common name of this architecture.
	 */
	public abstract String getShortName();
	
	/**
	 * 
	 * @return A list of calling convention representations that this architecture supports.
	 */
	public abstract List<CallingConvention> getCallingConventions();
	
	/**
	 * A list of all of the general-purpose registers that can be accessed by most instructions.
	 * 
	 * @return
	 */
	public abstract List<Register> getRegisters();
	public abstract Register getStackRegister();
	
	public boolean hasRegister(String name) {
		
		for (Register r : this.getRegisters()) {
			if (r.name.equals(name)) return true;
		}
		
		return false;
		
	}
	
	/**
	 * 
	 * @return '+' or '-', depending on which direction the stack grows.
	 */
	public abstract int getStackDirection();
	public char getStackDirectionSign() {
		
		if (this.getStackDirection() == 1) return '+';
		if (this.getStackDirection() == -1) return '-';
		
		throw new IllegalArgumentException("Architecture " + this.getShortName() + " has invalid stack direction!");
		
	}
	
	public CallingConvention getCallingConvention(String name) {
		
		for (CallingConvention cc : this.getCallingConventions()) {
			if (cc.name.equals(name)) return cc;
		}
		
		throw new UnsupportedConventionException("Calling convention " + name + " is not supported by " + this.getShortName() + ".");
		
	}
	
	public abstract InstructionSet getInstructionSet();
	
	public String getInstruction(Instruction instr, String... operands) {
		return this.getInstructionSet().fill(instr, operands);
	}
	
}
