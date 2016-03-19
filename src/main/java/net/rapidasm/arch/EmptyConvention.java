package net.rapidasm.arch;

import net.rapidasm.BinarySource;
import net.rapidasm.structure.RapidSubroutine;
import net.rapidasm.structure.Signature;

public class EmptyConvention extends CallingConvention {

	public EmptyConvention(Architecture arch, String name) {
		super(arch, name);
	}
	
	@Override
	public void doCallerSetup(RapidSubroutine caller, RapidSubroutine callee, BinarySource src) {
		
		src.addComment("CALLING: " + callee.name + " (" + this.name + ")");
		src.addCode(this.arch.getInstruction(Instruction.CALL, callee.name));
		
	}
	
	@Override
	public void doCallerCleanup(RapidSubroutine caller, RapidSubroutine callee, BinarySource src) {
		
		src.addComment("CALLED: " + caller.name + " (" + this.name + ")");
		
	}
	
	@Override
	public void doCalleeSetup(RapidSubroutine callee, BinarySource src) {
		
	}
	
	@Override
	public void doCalleeCleanup(RapidSubroutine callee, BinarySource src) {
		
		// Just the return instruction here.
		src.addCode(this.arch.getInstruction(Instruction.RETURN));
		
	}

	@Override
	public String getArgumentExpression(Signature sig, String argName) {
		return "!!!TEST!!!";
	}
	
}
