package net.rapidasm.structure.conditionals;

import net.rapidasm.BinarySource;
import net.rapidasm.structure.RapidStatementBlock;
import net.rapidasm.structure.operands.Operand;

public abstract class RapidBranchingStatement extends RapidStatementBlock {

	private static final String LABEL_INFIX_BEGIN = "_begin_";
	private static final String LABEL_INFIX_CODE = "_code_";
	private static final String LABEL_INFIX_END = "_end_";
	
	protected BranchGenerationType type;
	protected Likelihood likelyhood;
	protected Conditional conditional;
	
	protected Operand leftSide, rightSide;
	
	public RapidBranchingStatement(RapidStatementBlock parent, BranchGenerationType type, Likelihood like, Conditional cond) {
		
		super(parent);
		
		this.type = type;
		this.likelyhood = like;
		this.conditional = cond;
		
	}
	
	public void setOperands(Operand left, Operand right) {
		
		this.leftSide = left;
		this.rightSide = right;
		
	}
	
	public abstract String getBlockLabelPrefix();
	public abstract int getLine();
	
	public void addCodeLines(BinarySource src) {
		super.addLines(src); // This is what normally gets generated.
	}
	
	@Deprecated
	public void addConditionalLines(BinarySource src) {
		
		// FIXME XXX XXX XXX FIXME TODO XXX Like really.
		
		//DataSize cmpSuffix = DataSize.getDataSize(this.leftSide, this.rightSide);
		
		if (this.type == BranchGenerationType.CONDITIONAL) {
			
			this.leftSide.setup(src);
			this.rightSide.setup(src);
			
			//src.addInstruction(Instruction.COMPARE, cmpSuffix, this.leftSide.getActualOperand(), this.rightSide.getActualOperand());
			
			if (this.likelyhood == Likelihood.LIKELY) {
				//src.addInstruction(this.conditional.jmpInstruction, this.getEndLabel());
			} else if (this.likelyhood == Likelihood.UNLIKELY) {
				
				//src.addInstruction(this.conditional.jmpInstructionInverse, this.getCodeLabel());
				//src.addInstruction(Instruction.JUMP, this.getEndLabel());
				
			} else {
				src.addComment("ERROR FOR CONDITIONAL ON LINE " + this.getLine());
			}
			
		} else if (this.type == BranchGenerationType.FALSE) {
			
			src.addComment("Branch here is always false.");
			//src.addInstruction(Instruction.JUMP, this.getEndLabel());
			
		} else if (this.type == BranchGenerationType.TRUE) {
			src.addComment("Branch here is always true.");
		} else {
			src.addComment("Branch here has no type specification, assuming true.");
		}
		
	}
	
	@Override
	public void addLines(BinarySource src) {
		
		src.addSpace();
		
		// The conditional itself.
		src.addLabel(this.getBeginLabel());
		this.addConditionalLines(src);
		
		src.addLabel(this.getCodeLabel());
		this.addCodeLines(src);
		
		src.addLabel(this.getEndLabel());
		src.addSpace();
		
	}
	
	protected String getBeginLabel() {
		return this.getBlockLabelPrefix() + LABEL_INFIX_BEGIN + this.getLine();
	}
	
	protected String getCodeLabel() {
		return this.getBlockLabelPrefix() + LABEL_INFIX_CODE + this.getLine();
	}
	
	protected String getEndLabel() {
		return this.getBlockLabelPrefix() + LABEL_INFIX_END + this.getLine();
	}
	
}
