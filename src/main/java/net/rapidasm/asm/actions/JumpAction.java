package net.rapidasm.asm.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.rapidasm.arch.Architecture;
import net.rapidasm.arch.Instruction;
import net.rapidasm.asm.AsmInstruction;
import net.rapidasm.asm.AsmLabel;
import net.rapidasm.structure.operands.JumpInstructionTargetOperand;

public class JumpAction extends Action {
	
	private AsmInstruction target;
	
	public JumpAction(Architecture arch, AsmInstruction target) {
		
		super(arch);
		
		this.target = target;
		
	}

	@Override
	public List<AsmInstruction> getInstructions() {
		
		AsmLabel targetLabel = null;
		if (this.target.labels.size() == 0) {
			
			targetLabel = new AsmLabel("adhoc_" + this.hashCode());
			this.target.addLabel(targetLabel);
			
		} else {
			targetLabel = this.target.getPrimaryLabel();
		}
		
		return Arrays.asList(this.arch.getInstructionSet().fill(this.getJumpInstruction(), new JumpInstructionTargetOperand(this.arch, targetLabel)));
		
	}
	
	protected Instruction getJumpInstruction() {
		return Instruction.JUMP;
	}
	
}
