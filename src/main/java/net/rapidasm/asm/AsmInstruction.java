package net.rapidasm.asm;

import java.util.ArrayList;
import java.util.List;

import net.rapidasm.arch.Architecture;

public class AsmInstruction {
	
	public final String instruction;
	public final AsmOperand[] operands;
	
	public final List<AsmLabel> labels;
	
	public AsmInstruction(String instr) {
		this(instr, new AsmOperand[0]);
	}
	
	public AsmInstruction(String instr, AsmOperand[] ops) {
		
		this.instruction = instr;
		this.operands = ops;
		
		this.labels = new ArrayList<>();
		
	}
	
	public void addLabel(AsmLabel label) {
		
		if (label.reference != null) throw new IllegalArgumentException("An instruction already own the label object " + label.name + "!");
		
		this.labels.add(label);
		label.reference = this;
		
	}
	
	public void addLabel(String label) {
		this.addLabel(new AsmLabel(label));
	}
	
	public AsmLabel getPrimaryLabel() {
		return this.labels.get(0);
	}
	
	public String getCompiledLine(Architecture arch) {
		
		StringBuilder sb = new StringBuilder(this.instruction);
		
		if (this.operands.length > 0) {
			
			sb.append(" ");
			sb.append(this.operands[0].value);
			
			for (int i = 1; i < this.operands.length; i++) {
				sb.append(", " + this.operands[i].value);
			}
			
		}
		
		return sb.toString();
		
	}
	
}
