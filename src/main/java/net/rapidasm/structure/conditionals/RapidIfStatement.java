package net.rapidasm.structure.conditionals;

import net.rapidasm.BinarySource;
import net.rapidasm.antlr.RapidASMParser.ConditionalBlockContext;
import net.rapidasm.arch.Architecture;
import net.rapidasm.arch.Instruction;
import net.rapidasm.structure.Operand;
import net.rapidasm.structure.RapidStatementBlock;

public class RapidIfStatement extends RapidStatementBlock {

	private static String LABEL_PREFIX_BEGIN = "branch_begin_";
	private static String LABEL_PREFIX_CODE = "branch_code_";
	private static String LABEL_PREFIX_AFTER = "branch_after_";
	
	private ConditionalBlockContext context;
	
	private BranchGenerationType type;
	private Likelihood likelyhood;
	private Conditional conditional;
	
	private Operand leftSide;
	private Operand rightSide;
	
	public RapidIfStatement(RapidStatementBlock parent, BranchGenerationType type, Likelihood like, Conditional cond, ConditionalBlockContext ctx) {
		
		super(parent);
		
		this.type = type;
		this.likelyhood = like;
		this.conditional = cond;
		
		this.context = ctx;
		
	}
	
	public void setOperands(Operand left, Operand right) {
		
		this.leftSide = left;
		this.rightSide = right;
		
	}
	
	@Override
	public void addLines(BinarySource src) {
		
		Architecture arch = this.getRootModule().architecture;
		
		src.addComment("IF AT : " + this.context.start.getLine());
		
		src.addLabel(LABEL_PREFIX_BEGIN + this.getLine());
		
		if (this.type == BranchGenerationType.CONDITIONAL) {
			
			this.leftSide.setup(src);
			this.rightSide.setup(src);
			
			src.addCode(arch.getInstruction(Instruction.COMPARE, this.leftSide.getActualOperand(), this.rightSide.getActualOperand()));
			
			if (this.likelyhood == Likelihood.LIKELY) {
				src.addCode(arch.getInstruction(this.conditional.jmpInstructionInverse, LABEL_PREFIX_AFTER + this.getLine()));
			} else if (this.likelyhood == Likelihood.UNLIKELY) {
				
				src.addCode(arch.getInstruction(this.conditional.jmpInstruction, LABEL_PREFIX_CODE + this.getLine()));
				src.addCode(arch.getInstruction(Instruction.JUMP, LABEL_PREFIX_AFTER + this.getLine()));
				
			} else {
				src.addComment("ERROR FOR CONDITIONAL ON LINE " + this.getLine());
			}
			
		} else if (this.type == BranchGenerationType.FALSE) {
			
			src.addComment("Branch here is alwats false.");
			src.addCode(arch.getInstruction(Instruction.JUMP, LABEL_PREFIX_AFTER + this.getLine()));
			
		} else if (this.type == BranchGenerationType.TRUE) {
			src.addComment("Branch here is always true.");
		} else {
			src.addComment("Branch here has no type specification, assuming true.");
		}
		
		src.addLabel(LABEL_PREFIX_CODE + this.getLine());
		super.addLines(src); // The code for the block itself.
		src.addLabel(LABEL_PREFIX_AFTER + this.getLine());
		
	}
	
	private int getLine() {
		return this.context.start.getLine();
	}
	
}
