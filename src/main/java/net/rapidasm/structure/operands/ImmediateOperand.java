package net.rapidasm.structure.operands;

import net.rapidasm.antlr.ParserUtil;
import net.rapidasm.arch.Architecture;
import net.rapidasm.structure.DataSize;
import net.rapidasm.structure.RapidSubroutine;

public class ImmediateOperand extends Operand {

	public final String value;
	public final String originalValue;
	
	public ImmediateOperand(Architecture arch, RapidSubroutine sub, String reg) {
		
		super(arch, sub);
		
		// This branching doesn't work perfectly.  Needs some fine tuning for stuff like referencing arguments 
		if (reg.startsWith("$")) {
			
			String val = ParserUtil.getConvertedRegisterName(reg);
			
			if (!arch.hasRegister(val)) throw new IllegalArgumentException("Architecture " + arch.getShortName() + " does not support the " + val + " register!");
			this.value = "%" + val;
			
		} else if (reg.equals("!")) {
			this.value = "$";
		} else if (reg.equals("!!")) {
			this.value = "$$";
		} else {
			
			// Probably a number at this point, so we'll use the dolla sign.
			this.value = "$" + reg;
			
		}
		
		this.originalValue = reg;
		
	}
	
	@Override
	public String getActualOperand() {
		
		String out = this.value;
		
		if (this.subroutine != null && this.subroutine.hasArgument(this.value)) {
			out = this.subroutine.getArgumentExpression(this.value);
		}
		
		return out;
		
	}
	
	@Override
	public boolean needsRegisterCache() {
		return false;
	}
	
	@Override
	public DataSize getResultingDataSize() {
		
		if (this.originalValue.startsWith("$")) {
			return this.arch.getRegister(this.originalValue.substring(1)).size;
		} else if (this.originalValue.equals("!") || this.originalValue.equals("!!")) {
			return this.arch.getPointerSize();
		} else {
			return null; 
		}
		
	}
	
}
