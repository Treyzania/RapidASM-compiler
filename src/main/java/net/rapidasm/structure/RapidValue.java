package net.rapidasm.structure;

public class RapidValue {

	public String baseContents; // TODO Change this to allow specific behavior for registers, objects, etc.
	public int derefCount; // -1 is for address-of
	
	public String getAsmContents() {
		
		StringBuilder beginning = new StringBuilder();
		StringBuilder end = new StringBuilder();
		
		for (int i = 0; i < derefCount; i++) {
			
			beginning.append("[");
			end.append("]");
			
		}
		
		return beginning.toString() + baseContents + end.toString();
		
		// TODO Handle address-of stuff.
		
	}
	
}
