package net.rapidasm.structure;

import net.rapidasm.BinarySource;
import net.rapidasm.arch.CallingConvention;

public class RapidSubroutine extends SectionPopulant implements Assemblable, Headerable, StatementBlockParent {

	public String name;
	public CallingConvention callingConvention;
	public Signature signature;
	
	public RapidStatementBlock statementBlock;
	
	public RapidSubroutine(RapidSection parent, String name, CallingConvention conv) {
		
		super(parent);
		
		this.name = name;
		this.callingConvention = conv;
		this.signature = new Signature();
		
	}
	
	public boolean hasArgument(String name) {
		return this.signature.hasAgument(name);
	}
	
	public String getArgumentExpression(String name) {
		return this.callingConvention.getArgumentExpression(this.signature, name); 
	}
	
	@Override
	public void addLines(BinarySource src) {
		
		String lcName = this.name.toLowerCase();
		
		if (this.name.equals("ENTRY")) {
			src.addLabel("_start");
		} else {
			src.addLabel("sub_" + lcName);
		} 
		
		src.addSpace();
		
		// Now actually generate the code.
		this.callingConvention.doCalleeSetup(this, src);
		if (this.statementBlock != null) this.statementBlock.addLines(src);
		this.callingConvention.doCalleeCleanup(this, src);
		
		src.addSpace();
		src.addLabel("sub_" + lcName + "_end");
		src.addSpace();
		
	}
	
	@Override
	public RapidStatementBlock getBody() {
		return this.statementBlock;
	}
	
}
