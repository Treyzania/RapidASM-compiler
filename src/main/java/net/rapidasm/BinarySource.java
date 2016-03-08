package net.rapidasm;

import java.util.ArrayList;
import java.util.List;

public class BinarySource {

	private Blob lines; // Underlying list wrapper.
	public LineType lastTypeAdded;
	
	public BinarySource() {
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
		
		this.lines.addLine(labelName + ":");
		this.lastTypeAdded = LineType.LABEL;
		
	}
	
	/**
	 * Adds code that is intented by a tab.
	 * 
	 * @param lines
	 */
	public void addCode(String... lines) {
		
		for (String line : lines) {
			this.lines.addLine("\t" + line);
		}
		
		this.lastTypeAdded = LineType.CODE;
		
	}
	
	public void addSpaces(int num) {
		
		String indent = this.getIndent();
		
		for (int i = 0; i < num; i++) {
			this.lines.addLine(indent);
		}
		
	}
	
	public void addSpace() {
		this.addSpaces(1);
	}
	
	public void addComment(String msg) {
		this.lines.addLine(this.getIndent() + "; " + msg);
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
