package net.rapidasm.structure;

import net.rapidasm.BinarySource;
import net.rapidasm.antlr.RapidASMParser.NumericImmediateContext;
import net.rapidasm.antlr.RapidASMParser.NumericValueContext;
import net.rapidasm.arch.Architecture;
import net.rapidasm.arch.Instruction;

public abstract class Operand {

	protected final Architecture arch;
	
	protected Operand(Architecture arch) {
		
		this.arch = arch;
		
	}
	
	// The cache here is the register we store it in before doing whatever with it, might not be necessary.
	public void setup(String cache, BinarySource src) {
		
	}
	
	public void cleanup(String cache, BinarySource src) {
		
	}
	
	public abstract String getActualOperand();
	
	public abstract boolean needsRegisterCache();
	public void setCacheRegister(String registre) {
		
	}
	
	/**
	 * 
	 * @return An instance of an Operand ready to be used.
	 */
	public static Operand getOperand(Architecture arch, RapidSubroutine sub, NumericValueContext ctx) { // FIXME TODO Make this work.
		
		NumericImmediateContext nic = ctx.numericImmediate();
		
		if (nic != null) {
			
			if (ctx.plusMinus() != null) {
				
				return null; // FIXME
				
			} else {
				
				// Literals, registers, etc.
				return new ImmediateOperand(arch, nic.getText());
				
			}
			
		}
		
		String text = ctx.getText();
		if (text.startsWith("*")) {
			return new PointerDereferenceOperand(arch, sub, text.substring(1)); // ???
		} else if (text.startsWith("~")) {
			// TODO Sub invocation handling.
		}
		
		// TODO Implementation for the other types of operands.
		
		return null;
		
	}
	
	public static class ImmediateOperand extends Operand {

		public String value;
		
		protected ImmediateOperand(Architecture arch, String reg) {
			
			super(arch);
			
			if (reg.startsWith("$")) {
				
				String val = reg.substring(1);
				
				if (arch.hasRegister(val)) this.value = val;
				throw new IllegalArgumentException("Architecture " + arch.getShortName() + " does not support the " + val + " register!");
				
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
		
		private String cache;
		
		protected PointerDereferenceOperand(Architecture arch, RapidSubroutine sub, String pointer) {
			
			super(arch);
			
			this.subroutine = sub;
			this.pointer = pointer;
			
		}
		
		@Override
		public void setup(String cache, BinarySource src) {
			
			String exp = "";
			
			if (this.subroutine.hasArgument(this.pointer)) {
				
				exp = this.subroutine.getArgumentExpression(this.pointer);
				
			} else {
				
				// It's somewhere in the module, at least.  We can get it like this.
				exp = String.format("[%s]", this.pointer);
				
			}
			
			src.addCode(this.arch.getInstruction(Instruction.MOVE, this.cache, exp));
			
		}
		
		@Override
		public String getActualOperand() {
			return this.cache;
		}

		@Override
		public boolean needsRegisterCache() {
			return true; // ???
		}

		@Override
		public void setCacheRegister(String register) {
			this.cache = register;
		}
		
	}
	
}
