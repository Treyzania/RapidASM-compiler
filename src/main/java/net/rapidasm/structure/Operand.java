package net.rapidasm.structure;

import net.rapidasm.BinarySource;
import net.rapidasm.antlr.ParserUtil;
import net.rapidasm.arch.Architecture;

public abstract class Operand {

	protected final Architecture arch;
	protected final RapidSubroutine subroutine;
	
	protected Operand(Architecture arch, RapidSubroutine sub) {
		
		this.arch = arch;
		this.subroutine = sub;
		
	}
	
	public void setup(BinarySource src) {
		
	}
	
	public void cleanup(BinarySource src) {
		
	}
	
	public abstract String getActualOperand();
	
	// The cache here is the register we store it in before doing whatever with it, might not be necessary.
	public abstract boolean needsRegisterCache();
	public void setCacheRegister(String registre) {
		
	}
	
	public static class ImmediateOperand extends Operand {

		public String value;
		
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
		
	}
	
	public static class PointerDereferenceOperand extends Operand {
		
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
		
	}
	
}
