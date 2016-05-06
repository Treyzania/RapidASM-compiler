package net.rapidasm.arch.x86;

import java.util.ListIterator;

import net.rapidasm.BinarySource;
import net.rapidasm.arch.CallingConvention;
import net.rapidasm.structure.RapidSubroutine;
import net.rapidasm.structure.Signature;
import net.rapidasm.structure.Vararg;

public class CdeclConvention extends CallingConvention {
	
	protected CdeclConvention(X86Architecture x86) {
		super(x86, "cdecl");
	}
	
	@Override
	public void doCallerSetup(RapidSubroutine caller, RapidSubroutine callee, BinarySource src) {
		// TODO Push the arguments onto the stack.
	}
	
	@Override
	public void doCallerCleanup(RapidSubroutine caller, RapidSubroutine callee, BinarySource src) {
		
		// Clean up the stack.
		src.addCode(String.format("addl %s, esp", callee.signature.getTotalSize())); // Remove the arguments.
		
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
	
	@Override
	public String getArgumentExpression(Signature sig, String argName) {
		
		ListIterator<Vararg> args = sig.getArguments().listIterator();
		
		// Initial value to overcome the ebp thing.
		int bytesPassed = this.arch.getPointerSize().size;
		
		while (args.hasNext()) {
			
			Vararg arg = args.next();
			
			bytesPassed += arg.size.size;
			if (arg.name.equals(argName)) break;
			
		}
		
		return String.format("%s(%esp)", bytesPassed);
		
	}
	
}