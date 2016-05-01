package net.rapidasm.structure.operands;

import net.rapidasm.antlr.ParserUtil;
import net.rapidasm.arch.Architecture;
import net.rapidasm.structure.DataSize;
import net.rapidasm.structure.RapidSubroutine;

public class PointerDereferenceOperand extends Operand {
	
	private String pointer;
	private int offset;
	
	public PointerDereferenceOperand(Architecture arch, RapidSubroutine sub, String pointer, int offset) {
		
		super(arch, sub);
		
		this.pointer = pointer;
		this.offset = offset;
		
	}
	
	public PointerDereferenceOperand(Architecture arch, RapidSubroutine sub, String pointer) {
		this(arch, sub, pointer, 0);
	}
	
	@Override
	public String getActualOperand() {
		
		if (this.subroutine.hasArgument(this.pointer)) {
			
			return this.subroutine.getArgumentExpression(this.pointer);
			
		} else {
			
			// It's somewhere in the module, at least.  We can get it like this.
			if (this.offset == 0) {
				return "(" + ParserUtil.tryParseRegisterName(this.pointer) + ")";
			} else {
				return String.format("%s(%s)", this.offset, ParserUtil.tryParseRegisterName(this.pointer));
			}
			
		}
		
	}
	
	@Override
	public boolean needsRegisterCache() {
		return false;
	}
	
	@Override
	public DataSize getResultingDataSize() {
		return this.arch.getPointerSize();
	}
	
}
