package net.rapidasm.structure;

import net.rapidasm.BinarySource;
import net.rapidasm.arch.CallingConvention;

public class RapidSubroutine extends SectionPopulant implements Assemblable, Headerable, StatementBlockParent {

	public String name;
	public CallingConvention callingConvention;
	public Signature signature;
	
	public RapidStatementBlock statementBlock;
	
	public RapidSubroutine(String name, CallingConvention conv) {
		
		this.name = name;
		this.callingConvention = conv;
		this.signature = new Signature();
		
	}
	
	public boolean hasArgument(String name) {
		return this.signature.hasAgument(name);
	}
	
	public String getArgumentExpression(String name) {
		return String.format("", "");
	}
	
	@Override
	public void addLines(BinarySource src) {
		
		src.addLabel("sub_" + this.name.toLowerCase());
		
		// Now actually generate the code.
		this.callingConvention.doCalleeSetup(this, src);
		if (this.statementBlock != null) this.statementBlock.addLines(src);
		this.callingConvention.doCalleeCleanup(this, src);
		
		src.addLabel("sub_" + this.name.toLowerCase() + "_end");
		
		src.addSpace();
		
	}
	
	@Override
	public RapidStatementBlock getBody() {
		return this.statementBlock;
	}
	
}
