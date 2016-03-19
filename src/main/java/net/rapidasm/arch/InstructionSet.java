package net.rapidasm.arch;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores the patterns for code to generically represent specific instructions.
 * 
 * @author Treyzania
 *
 */
public class InstructionSet {

	private Map<Instruction, String> pattern;
	
	public InstructionSet() {
		this.pattern = new HashMap<>();
	}
	
	public void set(Instruction instr, String pattern) {
		this.pattern.put(instr, pattern);
	}
	
	public String fill(Instruction instr, String... strings) {
		
		if (!this.pattern.containsKey(instr)) throw new UnsupportedConventionException("This architecture doesn't report a pattern for the " + instr + " instruction.");
		return String.format(this.pattern.get(instr), (Object[]) strings);
		
	}
	
}
