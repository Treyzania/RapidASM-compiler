package net.rapidasm;

public class RapidASM {
	
	public static void main(String[] args) {
		
		String fileName = args[0];
		
		ModuleBuilder mb = new ModuleBuilder();
		Module mod = mb.createModule(fileName);
		
		BinarySource bs = new BinarySource(mod.architecture);
		bs.addComment("Generated from " + fileName);
		mod.addLines(bs);
		
		System.out.println(bs.toString());
		
	}
	
}
