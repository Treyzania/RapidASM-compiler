package net.rapidasm.structure.objects;

public class RapidObject {

	public RapidObjectType type;
	public String[] operands;
	
	public RapidObject(RapidObjectType type, String[] opers) {
		
		this.type= type;
		this.operands = opers;
		
	}
	
}
