package net.rapidasm;

import java.util.List;

public class AsmFile {

	public List<AsmLine> lines;
	
	public String getContents() {
		
		StringBuilder sb = new StringBuilder(lines.get(0).getLine());
		
		for (AsmLine line : this.lines) {
			
			sb.append("\n" + line.getLine());
			
		}
		
		return sb.toString();
		
	}
	
}
