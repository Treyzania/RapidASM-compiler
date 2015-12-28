package net.rapidasm.structure.subroutines;

import java.util.List;
import java.util.Map;

import net.rapidasm.arch.CallingConvention;
import net.rapidasm.structure.DataSize;

public class RapidSubroutine {

	public String name;
	public CallingConvention callingConvention;
	public Map<String, DataSize> signature;
	
	public List<RapidStatement> statements;
	
}
