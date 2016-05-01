package net.rapidasm.arch;

public enum Instruction {

	MOVE(2, 1),
	EXCHANGE(2, 0, 1),
	ADD(2, 1),
	SUBTRACT(1, 1),
	INCREMENT(1, 1),
	DECREMENT(1, 1),
	CALL(1),
	RETURN(0),
	JUMP(1),
	COMPARE(2),
	JUMP_EQUAL(1),
	JUMP_INEQUAL(1),
	JUMP_ZERO(1),
	JUMP_NONZERO(1),
	JUMP_GREATER(1),
	JUMP_GREATER_OR_EQUAL(1),
	JUMP_LESS(1),
	JUMP_LESS_OR_EQUAL(1),
	PUSH(1),
	POP(1, 0),
	
	UNDEFINED(-1); // Used for any other instruction that we somehow need to represent.
	
	public int arguments;
	
	// 16-value bitmask to figure out which ones change registers.
	private short changedRegisterMask = 0;
	
	private Instruction(int args) {
		this.arguments = args;
	}
	
	private Instruction(int args, int... changedRegisters) {
		
		this(args);
		
		for (int arg : changedRegisters) {
			this.changedRegisterMask |= (1 << arg);
		}
		
	}
	
	/**
	 * 
	 * @param argNum The number of the argument we're testing
	 * @return If this instruction changes the value of the register in that argument, if any.
	 */
	public boolean doesChangeRegister(int argNum) {
		return (this.changedRegisterMask & (1 << argNum)) != 0;
	}
	
	public boolean hasModifiedRegisters() {
		return this.changedRegisterMask != 0;
	}
	
}
