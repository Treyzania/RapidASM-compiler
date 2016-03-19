package net.rapidasm.arch.x86;

import java.util.ArrayList;
import java.util.List;

import net.rapidasm.BinarySource;
import net.rapidasm.arch.Architecture;
import net.rapidasm.arch.CallingConvention;
import net.rapidasm.arch.EmptyConvention;
import net.rapidasm.structure.RapidSubroutine;

public class X86Architecture extends Architecture {

	private List<CallingConvention> conventions;
	
	public X86Architecture() {
		
		// FIXME TODO Create actual calling conventions for these ones.
		this.conventions = new ArrayList<>();
		this.conventions.add(new CdeclConvention(this));
		this.conventions.add(new EmptyConvention(this, "test"));
		this.conventions.add(new EmptyConvention(this, "nocall"));
		
	}
	
	@Override
	public int getPointerSize() {
		return 4;
	}

	@Override
	public long getMaximumAddressableMemory() {
		return 0xffffffff;
	}

	@Override
	public int getWordSize() {
		return 4;
	}

	@Override
	public String getShortName() {
		return "x86";
	}

	@Override
	public List<CallingConvention> getCallingConventions() {
		return this.conventions;
	}
	
	@Override
	public String getMovInstruction(String to, String from) {
		return String.format("mov %s, %s", to, from);
	}

	@Override
	public String getIncInstruction(String target) {
		return String.format("inc %s", target);
	}

	@Override
	public String getDecInstruction(String target) {
		return String.format("dec %s", target);
	}

	@Override
	public String getAddInstruction(String target, String other) {
		return String.format("add %s, %s", target, other);
	}

	@Override
	public String getSubInstruciton(String target, String other) {
		return String.format("add %s, %s", target, other);
	}

	@Override
	public String getCallInstruction(String sub) {
		return String.format("call %s", sub);
	}

	@Override
	public String getReturnInstruction() {
		return "ret";
	}
	
	public static class CdeclConvention extends CallingConvention {

		private CdeclConvention(X86Architecture x86) {
			super(x86, "cdecl");
		}

		@Override
		public void doCallerSetup(RapidSubroutine caller, RapidSubroutine callee, BinarySource src) {
			// TODO Push the arguments onto the stack.
		}

		@Override
		public void doCallerCleanup(RapidSubroutine caller, RapidSubroutine callee, BinarySource src) {
			
			// Clean up the stack.
			src.addCode(String.format("add esp, %s", callee.signature.getTotalSize())); // Remove the arguments.
			
		}

		@Override
		public void doCalleeSetup(RapidSubroutine callee, BinarySource src) {
			// Nothing!
		}

		@Override
		public void doCalleeCleanup(RapidSubroutine callee, BinarySource src) {
			
			// Just return.  This is a caller-cleans convention.
			src.addCode("ret");
			
		}
		
	}
	
}
