package net.rapidasm.asm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.rapidasm.arch.Architecture;

public class AsmSequencer {
	
	public final Architecture arch;
	
	private List<AsmInstruction> instructions;
	
	public AsmSequencer(Architecture arch) {
		
		this.arch = arch;
		
		this.instructions = new ArrayList<>();
		
	}
	
	public void addInstruction(AsmInstruction asm) {
		this.instructions.add(asm);
	}
	
	public void addInstructions(Collection<AsmInstruction> instrs) {
		this.instructions.addAll(instrs);
	}
	
	public List<String> tabulate() {
		
		List<String> lines = new ArrayList<>();
		
		for (AsmInstruction asm : this.instructions) {
			
			for (AsmLabel label : asm.labels) {
				lines.add(label.name + ":");
			}
			
			lines.add("\t" + asm.getCompiledLine(this.arch));
			
		}
		
		return lines;
		
	}
	
	public String getText() {
		
		StringBuilder sb = new StringBuilder();
		
		for (String line : this.tabulate()) {
			sb.append(line + "\n");
		}
		
		return sb.toString();
		
	}
	
}
