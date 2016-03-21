package net.rapidasm;

import java.util.ArrayList;
import java.util.List;

import net.rapidasm.arch.Architecture;
import net.rapidasm.arch.Instruction;

public class BinarySource {

	private final Architecture arch;
	
	private Blob lines; // Underlying list wrapper.
	public LineType lastTypeAdded;
	
	public BinarySource(Architecture arch) {
		
		this.arch = arch;
		
		this.lines = new Blob();
		
	}
	
	private String getIndent() {
		
		if (this.lastTypeAdded == LineType.CODE) {
			return "\t";
		}
		
		return "";
		
	}
	
	/**
	 * Adds code that will appear all the way on the left, followed by a colon.
	 * 
	 * @param labelName
	 */
	public void addLabel(String labelName) {
		
		this.add(labelName + ":");
		this.lastTypeAdded = LineType.CODE;
		
	}
	
	/**
	 * Adds code that is indented by a tab.
	 * 
	 * @param lines
	 */
	public void addCode(String... lines) {
		
		for (String line : lines) {
			this.add("\t" + line);
		}
		
		this.lastTypeAdded = LineType.CODE;
		
	}
	
	/**
	 * Adds the instruction specified with the operands specified, if any.
	 * @param instr
	 * @param operands
	 */
	public void addInstruction(Instruction instr, String... operands) {
		this.addCode(this.arch.getInstruction(instr, operands));
	}
	
	public void addSpaces(int num) {
		
		String indent = this.getIndent();
		
		for (int i = 0; i < num; i++) {
			this.add(indent);
		}
		
	}
	
	public void addSpace() {
		this.addSpaces(1);
	}
	
	public void addComment(String msg) {
		this.add(this.getIndent() + "; " + msg);
	}
	
	public void add(String str) {
		this.lines.addLine(str);
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		for (String line : this.lines.lines) {
			sb.append(line + "\n");
		}
		
		return sb.toString();
		
	}
	
	private static enum LineType {
		CODE, LABEL;
	}
	
	private static class Blob {
		
		private List<String> lines;
		
		public Blob() {
			this.lines = new ArrayList<String>();
		}
		
		public void addLine(String line) {
			this.lines.add(line);
		}
		
	}
	
}
