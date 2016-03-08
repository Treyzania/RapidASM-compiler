package net.rapidasm.structure.subroutines;

import java.util.List;
import java.util.Map;

import net.rapidasm.AsmLine;
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

	@Override
	public List<AsmLine> getAssembly() {
		
		// TODO
		
		return null;
		
	}
	
}
