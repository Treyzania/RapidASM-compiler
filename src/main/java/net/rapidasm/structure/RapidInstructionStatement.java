package net.rapidasm.structure;

import java.util.ArrayList;
import java.util.List;

import net.rapidasm.BinarySource;
import net.rapidasm.arch.Register;

public class RapidInstructionStatement extends RapidStatement implements Child<RapidStatementBlock>  {

	public final String name;
	
	private List<Operand> operands;
	
	public RapidInstructionStatement(RapidStatementBlock parent, String name) {
		
		super(parent);
		
		this.name = name;
		
		this.operands = new ArrayList<>();
		
	}
	
	public void setOperands(List<Operand> os) {
		this.operands = os;
	}
	
	@Override
	public void addLines(BinarySource src) {
		
		String argExp = "";
		
		for (Operand operand : this.operands) {
			operand.setup(src);
		}
		
		if (this.operands.size() > 0) {
			
			StringBuilder args = new StringBuilder(this.operands.get(0).getActualOperand());
			for (int i = 1; i < this.operands.size(); i++) {
				args.append(", " + this.operands.get(i).getActualOperand());
			}
			
			argExp = args.toString();
			
		}
		
		for (Operand operand : this.operands) {
			operand.cleanup(src);
		}
		
		src.addCode(this.name + " " + argExp);
		
	}
	
	@Override
	public RapidStatementBlock getStructuralParent() {
		return this.parent;
	}
	
	public List<Register> getChangedRegisters() {
		
		List<Register> regs = new ArrayList<>();
		
		// TODO Add code here!
		
		return regs;
		
	}
	
}
