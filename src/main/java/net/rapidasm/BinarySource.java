package net.rapidasm;

import java.util.ArrayList;
import java.util.List;

import net.rapidasm.arch.Architecture;

public class BinarySource {

	@SuppressWarnings("unused")
	private final Architecture arch;
	
	private Blob lines; // Underlying list wrapper.
	public LineType lastTypeAdded;
	
	private List<String> queuedLabels;
	private int spacesAdded;
	
	public BinarySource(Architecture arch) {
		
		this.arch = arch;
		
		this.lines = new Blob();
		this.resetLabelQueue();
		
	}
	
	private void resetLabelQueue() {
		this.queuedLabels = new ArrayList<>();
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
		this.queuedLabels.add(labelName);
	}
	
	/**
	 * Adds code that is indented by a tab.
	 * 
	 * @param lines
	 */
	public void addCode(String... lines) {
		
		this.spacesAdded = 0; // Reset the spaces.
		
		for (String label : this.queuedLabels) {
			this.add(label + ":");
		}
		
		this.resetLabelQueue();
		
		for (String line : lines) {
			this.add("\t" + line);
		}
		
		this.lastTypeAdded = LineType.CODE;
		
	}
	
	public void addSpaces(int minSpaces) {
		
		int toAdd = minSpaces;
		
		// Quick tests to make sure we don't overshoot or anything bad.
		if (this.spacesAdded <= minSpaces) toAdd -= this.spacesAdded;
		if (toAdd < 0) toAdd = 0;
		
		String indent = this.getIndent();
		
		for (int i = 0; i < toAdd; i++) {
			this.add(indent);
		}
		
		this.spacesAdded += toAdd;
		
	}
	
	public void addSpace() {
		this.addSpaces(1);
	}
	
	public void addComment(String msg) {
		
		this.spacesAdded = 0;
		this.add(this.getIndent() + "# " + msg);
		
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
