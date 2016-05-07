package net.rapidasm.asm;

/**
 * Represents how a instruction operand gets there.  Sometimes also refers to destinations.
 * 
 * @author Treyzania
 */
public enum DataSource {
	
	REGISTER,
	LITERAL, // Both bare numbers and, when used in jumps, labels.
	MEMORY;
	
}
