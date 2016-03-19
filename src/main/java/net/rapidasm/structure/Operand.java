package net.rapidasm.structure;

import net.rapidasm.BinarySource;
import net.rapidasm.arch.Architecture;

public abstract class Operand {

	protected final Architecture arch;
	
	protected Operand(Architecture arch) {
		
		this.arch = arch;
		
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
		
		public ImmediateOperand(Architecture arch, String reg) {
			
			super(arch);
			
			if (reg.startsWith("$")) {
				
				String val = reg.substring(1);
				
				if (!arch.hasRegister(val)) throw new IllegalArgumentException("Architecture " + arch.getShortName() + " does not support the " + val + " register!");
				this.value = val;
				
			} else {
				this.value = reg;
			}
			
		}
		
		@Override
		public String getActualOperand() {
			return this.value;
		}

		@Override
		public boolean needsRegisterCache() {
			return false;
		}
		
	}
	
	public static class PointerDereferenceOperand extends Operand {
		
		private RapidSubroutine subroutine;
		private String pointer;
		private int offset;
		
		public PointerDereferenceOperand(Architecture arch, RapidSubroutine sub, String pointer, int offset) {
			
			super(arch);
			
			this.subroutine = sub;
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
					return String.format("[%s]", this.pointer);
				} else {
					return String.format("[%s %s %s]", this.pointer, this.offset > 0 ? '+' : '-', this.offset);
				}
				
			}
			
		}

		@Override
		public boolean needsRegisterCache() {
			return false;
		}
		
	}
	
}
