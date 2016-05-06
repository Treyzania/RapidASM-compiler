package net.rapidasm.arch.x86;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.rapidasm.arch.Architecture;
import net.rapidasm.arch.CallingConvention;
import net.rapidasm.arch.EmptyConvention;
import net.rapidasm.arch.Instruction;
import net.rapidasm.arch.InstructionSet;
import net.rapidasm.arch.Register;
import net.rapidasm.structure.DataSize;

public class X86Architecture extends Architecture {
	
	private List<CallingConvention> conventions;
	private InstructionSet instructionSet;
	
	private CallingConvention cdeclConvention;
	
	public X86Architecture() {
		
		// FIXME TODO Create actual calling conventions for these ones.
		this.conventions = new ArrayList<>();
		this.cdeclConvention = new CdeclConvention(this);
		this.conventions.add(this.cdeclConvention);
		this.conventions.add(new EmptyConvention(this, "nocall"));
		
		this.instructionSet = new InstructionSet();
		
		// General instructions.
		this.instructionSet.set(Instruction.MOVE, "mov{} %s, %s");
		this.instructionSet.set(Instruction.EXCHANGE, "xchg{} %s, %s");
		this.instructionSet.set(Instruction.ADD, "add{} %s, %s");
		this.instructionSet.set(Instruction.SUBTRACT, "sub{} %s, %s");
		this.instructionSet.set(Instruction.INCREMENT, "inc{} %s");
		this.instructionSet.set(Instruction.DECREMENT, "dec{} %s");
		this.instructionSet.set(Instruction.CALL, "call %s");
		this.instructionSet.set(Instruction.RETURN, "ret");
		this.instructionSet.set(Instruction.JUMP, "jmp %s");
		this.instructionSet.set(Instruction.COMPARE, "cmp %s, %s");
		
		// Jumping instructions.
		this.instructionSet.set(Instruction.COMPARE, "cmp{} %s, %s");
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
	
	@Override
	public List<Register> getRegisters() {
		
		return Arrays.asList(
				
			// General purpose registers
			new Register("al", DataSize.BYTE),
			new Register("ah", DataSize.BYTE),
			new Register("ax", DataSize.SHORT),
			new Register("eax", DataSize.INTEGER),
			new Register("bl", DataSize.BYTE),
			new Register("bh", DataSize.BYTE),
			new Register("bx", DataSize.SHORT),
			new Register("ebx", DataSize.INTEGER),
			new Register("cl", DataSize.BYTE),
			new Register("ch", DataSize.BYTE),
			new Register("cx", DataSize.SHORT),
			new Register("ecx", DataSize.INTEGER),
			new Register("dl", DataSize.BYTE),
			new Register("dh", DataSize.BYTE),
			new Register("dx", DataSize.SHORT),
			new Register("edx", DataSize.INTEGER),
			
			// Others
			new Register("si", DataSize.SHORT),
			new Register("esi", DataSize.INTEGER),
			new Register("di", DataSize.SHORT),
			new Register("edi", DataSize.INTEGER),
			new Register("bp", DataSize.SHORT),
			new Register("ebp", DataSize.INTEGER),
			new Register("sp", DataSize.SHORT),
			new Register("esp", DataSize.INTEGER),
			
			// Flags
			new Register("flags", DataSize.SHORT),
			new Register("eflags", DataSize.INTEGER)
			
			// TODO Add the other ones, like cs, ds, es, fs, gs, and ss? 
			
		);
		
	}
	
	@Override
	public Register getStackRegister() {
		return new Register("esp", DataSize.INTEGER); // This is identical to the one defined above.
	}
	
	@Override
	public int getStackDirection() {
		return -1;
	}
	
	@Override
	public CallingConvention getDefaultCallingConvention() {
		return this.cdeclConvention;
	}
	
}
