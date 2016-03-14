package net.rapidasm.structure.subroutines;

import java.util.Map;

import net.rapidasm.BinarySource;
import net.rapidasm.arch.CallingConvention;
import net.rapidasm.structure.Assemblable;
import net.rapidasm.structure.DataSize;
import net.rapidasm.structure.Headerable;
import net.rapidasm.structure.RapidStatementBlock;

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
		this.statementBlock.addLines(src);
		src.addLabel("sub_" + this.name.toLowerCase() + "_end");
		
		src.addSpace();
		
	}
	
}
