package net.rapidasm.asm.actions;

import net.rapidasm.arch.Architecture;
import net.rapidasm.asm.AsmInstruction;
import net.rapidasm.asm.AsmOperand;
import net.rapidasm.asm.AsmSequencer;
import net.rapidasm.structure.operands.Operand;

public class GenericAction extends Action {
	
	private String instructionString;
	private Operand left, right;
	
	public GenericAction(Architecture arch, String instrStr, Operand left, Operand right) {
		
		super(arch);
		
		this.instructionString = instrStr;
		
		this.left = left;
		this.right = right;
		
	}
	
	public GenericAction(Architecture arch, String instrStr, Operand oper) {
		this(arch, instrStr, oper, null);
	}
	
	public GenericAction(Architecture arch, String instrStr) {
		this(arch, instrStr, null);
	}
	
	@Override
	public void addInstructions(AsmSequencer seq) {
		
		// XXX
		seq.addInstruction(new AsmInstruction(this.instructionString, new AsmOperand[] {this.left.getAsmOperand(), this.right.getAsmOperand()}));
		
	}
	
}
