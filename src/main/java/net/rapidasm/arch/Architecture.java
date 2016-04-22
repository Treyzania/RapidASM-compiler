package net.rapidasm.arch;

import java.util.ArrayList;
import java.util.List;

import net.rapidasm.structure.DataSize;

public abstract class Architecture {

	// TODO Add list of instructions.
	
	/**
	 * 
	 * @return The size of a pointers in bytes.
	 */
	public DataSize getPointerSize() {
		return this.getWordSize();
	}
	
	/**
	 * 
	 * @return The maximum bytes of memory that can be addressed. 
	 */
	public abstract long getMaximumAddressableMemory();
	
	/**
	 * 
	 * @return The size of a word in bytes.
	 */
	public abstract DataSize getWordSize(); 
	
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
	 * 
	 * @return A list of all of the general-purpose registers that can be accessed by most instructions.
	 */
	public abstract List<Register> getRegisters();
	
	/**
	 * 
	 * @return The register representing the base of the stack.
	 */
	public abstract Register getStackRegister();
	
	public boolean hasRegister(String name) {
		
		for (Register r : this.getRegisters()) {
			if (r.name.equals(name)) return true;
		}
		
		return false;
		
	}
	
	public List<Register> getOptimalRegisters() {
		
		List<Register> regs = new ArrayList<>();
		
		this.getRegisters().forEach(r -> {
			if (r.width == this.getWordSize().size) regs.add(r);
		});
		
		return regs;
		
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
	
	public abstract CallingConvention getDefaultCallingConvention();
	
}
