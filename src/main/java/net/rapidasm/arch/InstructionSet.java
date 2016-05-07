package net.rapidasm.arch;

import java.util.ArrayList;
import java.util.List;
import net.rapidasm.asm.DataSource;
import net.rapidasm.structure.operands.Operand;

/**
 * Stores the patterns for code to generically represent specific instructions.
 * 
 * @author Treyzania
 *
 */
public class InstructionSet {

	private List<InstructionSignature> instructionSignatures;
	
	public InstructionSet() {
		this.instructionSignatures = new ArrayList<>();
	}
	
	/**
	 * Defines an instruction in the instruction set to have the specified
	 * characteristics.  These values are later passed into String.format (so
	 * arguments should be replaced by <code>%s</code>) and another replace(),
	 * so there should be <code>{}</code> in the place of the GAS syntax
	 * instruction size specification.
	 * 
	 * @param instr
	 * @param pattern
	 * @param changes
	 * @param sources
	 */
	public void set(Instruction instr, String pattern, int changes, DataSource... sources) {
		this.instructionSignatures.add(new InstructionSignature(instr, pattern, changes, sources));
	}
	
	public void set(Instruction instr, String pattern, DataSource... sources) {
		this.set(instr, pattern, 0, sources);
	}
	
	public void set(Instruction instr, String pattern) {
		this.set(instr, pattern, new DataSource[0]);
	}
	
	/**
	 * Checks to see if this instruction set can support the specified values.
	 * 
	 * @param instr
	 * @param sources
	 * @return
	 */
	public boolean isValid(Instruction instr, DataSource... sources) {
		
		for (InstructionSignature is : this.instructionSignatures) {
			if (instr == is.instruction && is.matches(sources)) return true;
		}
		
		return false;
		
	}
	
	public String fill(Instruction instr, Operand... operands) {
		
		// First check to see if we can support it, and get the actual text while we're at it.
		DataSource[] sources = new DataSource[operands.length];
		String[] ops = new String[operands.length];
		for (int i = 0; i < operands.length; i++) {
			
			sources[i] = operands[i].getSource();
			ops[i] = operands[i].getAsmOperand().value;
			
		}
		
		InstructionSignature is = null;
		for (InstructionSignature test : this.instructionSignatures) {
			if (instr == is.instruction && is.matches(sources)) is = test;
		}
		
		if (is == null) throw new IllegalArgumentException("Instruction " + instr.name() + " is not supported by this instruction set!");
		return String.format(is.pattern, (Object[]) ops); // Yucky Object[] casting. :P
		
	}
	
	public static class InstructionSignature {
		
		protected final Instruction instruction;
		protected final String pattern;
		protected final int changeMap;
		protected final DataSource[] sourceMap;
		
		protected InstructionSignature(Instruction instr, String pattern, int changeMap, DataSource... sources) {
			
			this.instruction = instr;
			this.pattern = pattern;
			this.changeMap = changeMap;
			this.sourceMap = sources;
			
		}
		
		/**
		 * Checks to see if the supplied pattern of <code>DataSource</code>s is compatible with this signature.
		 * 
		 * @param sources
		 * @return
		 */
		public boolean matches(DataSource... sources) {
			
			if (this.sourceMap.length != sources.length) return false;
			
			for (int i = 0; i < this.sourceMap.length; i++) {
				if (this.sourceMap[i] != sources[i]) return false;
			}
			
			return true;
			
		}
		
	}
	
}
