package net.rapidasm.structure;

import java.util.Map;

import net.rapidasm.BinarySource;
import net.rapidasm.arch.CallingConvention;

public class RapidSubroutine extends SectionPopulant implements Assemblable, Headerable, StatementBlockParent {

	public String name;
	public CallingConvention callingConvention;
	public Map<String, DataSize> signature;
	
	public RapidStatementBlock statementBlock;
	
	public RapidSubroutine() {
		
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
