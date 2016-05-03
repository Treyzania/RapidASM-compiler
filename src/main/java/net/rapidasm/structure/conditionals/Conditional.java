package net.rapidasm.structure.conditionals;

import net.rapidasm.arch.Instruction;

public enum Conditional {

	EQUAL(Instruction.JUMP_EQUAL, Instruction.JUMP_INEQUAL, "=="),
	INEQUAL(Instruction.JUMP_INEQUAL, Instruction.JUMP_EQUAL, "!=", "<>"),
	GRATER_THAN(Instruction.JUMP_GREATER, Instruction.JUMP_LESS_OR_EQUAL, ">"),
	GREATER_THAN_EQUAL(Instruction.JUMP_GREATER_OR_EQUAL, Instruction.JUMP_LESS, ">=", "=>"),
	LESS_THAN(Instruction.JUMP_LESS, Instruction.JUMP_GREATER_OR_EQUAL, "<"),
	LESS_THAN_EQUAL(Instruction.JUMP_LESS_OR_EQUAL, Instruction.JUMP_GREATER, "<=", "=<");
	
	public String[] operators;
	public Instruction jmpInstruction;
	public Instruction jmpInstructionInverse; // For likelyhood handling.
	
	private Conditional(Instruction instr, Instruction inverse, String... ops) {
		
		this.operators = ops;
		
		this.jmpInstruction = instr;
		this.jmpInstructionInverse = inverse;
		
	}
	
	public static Conditional getConditonal(String operator) {
		
		for (Conditional cond : values()) {
			
			for (String op : cond.operators) {
				
				// Just one conditional when it comes down to it.
				if (op.equals(operator)) return cond;
				
			}
			
		}
		
		return null;
		
	}
	
}
