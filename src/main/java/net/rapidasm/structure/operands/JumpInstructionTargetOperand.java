package net.rapidasm.structure.operands;

import net.rapidasm.arch.Architecture;
import net.rapidasm.asm.AsmLabel;
import net.rapidasm.asm.AsmOperand;
import net.rapidasm.asm.DataSource;
import net.rapidasm.structure.DataSize;
import net.rapidasm.structure.RapidSubroutine;


public class JumpInstructionTargetOperand extends Operand {
	
	private final AsmLabel target;
	
	public JumpInstructionTargetOperand(Architecture arch, AsmLabel label) {
		
		super(arch, null);
		
		this.target = label;
		
	}
	
	@Override
	public DataSize getResultingDataSize() {
		return null;
	}
	
	@Override
	public AsmOperand getAsmOperand() {
		return new AsmOperand(this.target.name, null);
	}
	
	@Override
	public DataSource getSource() {
		return DataSource.LITERAL;
	}
	
}
