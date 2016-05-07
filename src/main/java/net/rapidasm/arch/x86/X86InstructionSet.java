package net.rapidasm.arch.x86;

import net.rapidasm.arch.Instruction;
import net.rapidasm.arch.InstructionSet;
import net.rapidasm.asm.DataSource;


public class X86InstructionSet extends InstructionSet {
	
	public X86InstructionSet() {
		
		// Keep things happy, explicitly.
		super();
		
		// mov
		this.set(Instruction.MOVE, "mov{} %s, %s", 1 << 1, DataSource.REGISTER, DataSource.REGISTER);
		this.set(Instruction.MOVE, "mov{} %s, %s", 1 << 1, DataSource.LITERAL, DataSource.REGISTER);
		this.set(Instruction.MOVE, "mov{} %s, %s", 1 << 1, DataSource.MEMORY, DataSource.REGISTER);
		this.set(Instruction.MOVE, "mov{} %s, %s", 1 << 1, DataSource.REGISTER, DataSource.MEMORY);
		
		// xchg
		this.set(Instruction.EXCHANGE, "xchg{} %s, %s", (1 << 1) | 1, DataSource.REGISTER, DataSource.REGISTER);
		this.set(Instruction.EXCHANGE, "xchg{} %s, %s", (1 << 1) | 1, DataSource.REGISTER, DataSource.MEMORY);
		this.set(Instruction.EXCHANGE, "xchg{} %s, %s", (1 << 1) | 1, DataSource.MEMORY, DataSource.REGISTER);
		
		// add
		this.set(Instruction.ADD, "add{} %s, %s", 1 << 1, DataSource.REGISTER, DataSource.REGISTER);
		this.set(Instruction.ADD, "add{} %s, %s", 1 << 1, DataSource.LITERAL, DataSource.REGISTER);
		this.set(Instruction.ADD, "add{} %s, %s", 1 << 1, DataSource.MEMORY, DataSource.REGISTER);
		
		// sub
		this.set(Instruction.SUBTRACT, "sub{} %s, %s", 1 << 1, DataSource.REGISTER, DataSource.REGISTER);
		this.set(Instruction.SUBTRACT, "sub{} %s, %s", 1 << 1, DataSource.LITERAL, DataSource.REGISTER);
		this.set(Instruction.SUBTRACT, "sub{} %s, %s", 1 << 1, DataSource.MEMORY, DataSource.REGISTER);
		
		// inc
		this.set(Instruction.INCREMENT, "inc{} %s", 1, DataSource.REGISTER);
		this.set(Instruction.INCREMENT, "inc{} %s", 1, DataSource.MEMORY);
		
		// dec
		this.set(Instruction.DECREMENT, "dec{} %s", 1, DataSource.REGISTER);
		this.set(Instruction.DECREMENT, "dec{} %s", 1, DataSource.MEMORY);
		
		// call (We're not dealing with explict relative calls in RapidASM at this point, if that's even possible.)
		this.set(Instruction.CALL, "call %s", DataSource.LITERAL);
		
		// ret
		this.set(Instruction.RETURN, "ret");
		
		// cmp
		this.set(Instruction.COMPARE, "cmp{} %s, %s", DataSource.REGISTER, DataSource.REGISTER);
		this.set(Instruction.COMPARE, "cmp{} %s, %s", DataSource.LITERAL, DataSource.REGISTER);
		this.set(Instruction.COMPARE, "cmp{} %s, %s", DataSource.MEMORY, DataSource.REGISTER);
		
		// jmp, etc.
		this.set(Instruction.JUMP, "jmp %s", DataSource.LITERAL);
		this.set(Instruction.JUMP_EQUAL, "je %s", DataSource.LITERAL);
		this.set(Instruction.JUMP_INEQUAL, "jne %s", DataSource.LITERAL);
		this.set(Instruction.JUMP_ZERO, "jz %s", DataSource.LITERAL);
		this.set(Instruction.JUMP_NONZERO, "jnz %s", DataSource.LITERAL);
		this.set(Instruction.JUMP_GREATER, "jg %s", DataSource.LITERAL);
		this.set(Instruction.JUMP_GREATER_OR_EQUAL, "jge %s", DataSource.LITERAL);
		this.set(Instruction.JUMP_LESS, "jl %s", DataSource.LITERAL);
		this.set(Instruction.JUMP_LESS_OR_EQUAL, "jle %s", DataSource.LITERAL);
		
		// push, pop
		this.set(Instruction.PUSH, "push{} %s", DataSource.REGISTER);
		this.set(Instruction.PUSH, "push{} %s", DataSource.LITERAL);
		this.set(Instruction.PUSH, "push{} %s", DataSource.MEMORY);
		this.set(Instruction.POP, "pop{} %s", DataSource.REGISTER);
		this.set(Instruction.POP, "pop{} %s", DataSource.LITERAL);
		this.set(Instruction.POP, "pop{} %s", DataSource.MEMORY);
		
	}
	
}
