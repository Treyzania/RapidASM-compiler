package net.rapidasm.structure.mov;

import net.rapidasm.arch.Instruction;

public enum MoveType {

	MOVE(Instruction.MOVE),
	EXHANGE(Instruction.EXCHANGE);
	
	public Instruction instruction;
	
	private MoveType(Instruction instr) {
		this.instruction = instr;
	}
	
}
