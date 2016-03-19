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
	
	@Override
	public void addLines(BinarySource src) {
		
		src.addLabel("sub_" + this.name.toLowerCase());
		if (this.statementBlock != null) this.statementBlock.addLines(src);
		src.addLabel("sub_" + this.name.toLowerCase() + "_end");
		
		src.addSpace();
		
	}
	
	@Override
	public RapidStatementBlock getBody() {
		return this.statementBlock;
	}
	
}
