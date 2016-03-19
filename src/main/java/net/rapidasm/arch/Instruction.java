package net.rapidasm.arch;

public enum Instruction {

	MOVE,
	ADD,
	SUBTRACT,
	INCREMENT,
	DECREMENT,
	CALL,
	RETURN,
	JUMP,
	COMPARE,
	JUMP_EQUAL,
	JUMP_INEQUAL,
	JUMP_ZERO,
	JUMP_NONZERO,
	JUMP_GREATER,
	JUMP_GREATER_OR_QQUAL,
	JUMP_LESS,
	JUMP_LESS_OR_EQUAL,
	
	UNDEFINED; // Used for any other instruction that we somehow need to represent.
	
}
