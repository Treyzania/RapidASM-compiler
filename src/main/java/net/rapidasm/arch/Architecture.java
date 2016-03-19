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
