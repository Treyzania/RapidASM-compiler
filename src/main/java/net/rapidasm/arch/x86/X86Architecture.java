package net.rapidasm.arch.x86;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import net.rapidasm.BinarySource;
import net.rapidasm.arch.Architecture;
import net.rapidasm.arch.CallingConvention;
import net.rapidasm.arch.EmptyConvention;
import net.rapidasm.arch.Instruction;
import net.rapidasm.arch.InstructionSet;
import net.rapidasm.arch.Register;
import net.rapidasm.structure.DataSize;
import net.rapidasm.structure.RapidSubroutine;
import net.rapidasm.structure.Signature;
import net.rapidasm.structure.Vararg;

public class X86Architecture extends Architecture {

	private List<CallingConvention> conventions;
	private InstructionSet instructionSet;
	
	public X86Architecture() {
		
		// FIXME TODO Create actual calling conventions for these ones.
		this.conventions = new ArrayList<>();
		this.conventions.add(new CdeclConvention(this));
		this.conventions.add(new EmptyConvention(this, "test"));
		this.conventions.add(new EmptyConvention(this, "nocall"));
		
		this.instructionSet = new InstructionSet();
		
		// General instructions.
		this.instructionSet.set(Instruction.MOVE, "mov %s, %s");
		this.instructionSet.set(Instruction.EXCHANGE, "xchg %s, %s");
		this.instructionSet.set(Instruction.ADD, "add %s, %s");
		this.instructionSet.set(Instruction.SUBTRACT, "sub %s, %s");
		this.instructionSet.set(Instruction.INCREMENT, "inc %s");
		this.instructionSet.set(Instruction.DECREMENT, "dec %s");
		this.instructionSet.set(Instruction.CALL, "call %s");
		this.instructionSet.set(Instruction.RETURN, "ret");
		this.instructionSet.set(Instruction.JUMP, "jmp %s");
		this.instructionSet.set(Instruction.COMPARE, "cmp %s, %s");
		
		// Jumping instructions.
		this.instructionSet.set(Instruction.JUMP_EQUAL, "je %s");
		this.instructionSet.set(Instruction.JUMP_INEQUAL, "jne %s");
		this.instructionSet.set(Instruction.JUMP_ZERO, "jz %s");
		this.instructionSet.set(Instruction.JUMP_NONZERO, "jnz %s");
		this.instructionSet.set(Instruction.JUMP_GREATER, "jg %s");
		this.instructionSet.set(Instruction.JUMP_GREATER_OR_EQUAL, "jge %s");
		this.instructionSet.set(Instruction.JUMP_LESS, "jl %s");
		this.instructionSet.set(Instruction.JUMP_LESS_OR_EQUAL, "jle %s");
		
	}
	
	@Override
	public long getMaximumAddressableMemory() {
		return 0xffffffff;
	}
	
	@Override
	public DataSize getWordSize() {
		return DataSize.INTEGER;
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
	public InstructionSet getInstructionSet() {
		return this.instructionSet;
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

		@Override
		public String getArgumentExpression(Signature sig, String argName) {
			
			ListIterator<Vararg> args = sig.getArguments().listIterator();
			
			int bytesPassed = 0;
			
			while (args.hasNext()) {
				
				Vararg arg = args.next();
				
				bytesPassed += arg.size.size;
				if (arg.name.equals(argName)) break;
				
			}
			
			return String.format("[esp + %s]", bytesPassed);
			
		}
		
	}

	@Override
	public List<Register> getRegisters() {
		
		return Arrays.asList(
				
			// General purpose registers
			new Register("al", 1),
			new Register("ah", 1),
			new Register("ax", 2),
			new Register("eax", 4),
			new Register("bl", 1),
			new Register("bh", 1),
			new Register("bx", 2),
			new Register("ebx", 4),
			new Register("cl", 1),
			new Register("ch", 1),
			new Register("cx", 2),
			new Register("ecx", 4),
			new Register("dl", 1),
			new Register("dh", 1),
			new Register("dx", 2),
			new Register("edx", 4),
			
			// Others
			new Register("si", 2),
			new Register("esi", 4),
			new Register("di", 2),
			new Register("edi", 4),
			new Register("bp", 2),
			new Register("ebp", 4),
			new Register("sp", 2),
			new Register("esp", 4),
			
			// Flags
			new Register("flags", 2),
			new Register("eflags", 4)
			
			// TODO Add the other ones, like cs, ds, es, fs, gs, and ss? 
			
		);
		
	}

	@Override
	public Register getStackRegister() {
		return new Register("esp", 4); // This is identical to the one defined above.
	}

	@Override
	public int getStackDirection() {
		return -1;
	}
	
}
