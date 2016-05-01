package net.rapidasm.structure.operands;

import net.rapidasm.arch.Architecture;
import net.rapidasm.structure.DataSize;
import net.rapidasm.structure.RapidSubroutine;
import net.rapidasm.structure.symbols.ValueSymbol;

public class ValueSymbolOperand extends Operand {

	private final ValueSymbol value;
	
	public ValueSymbolOperand(Architecture arch, RapidSubroutine sub, ValueSymbol value) {
		
		super(arch, sub);
		
		this.value = value;
		
	}

	@Override
	public String getActualOperand() {
		return "$" + this.value.name;
	}

	@Override
	public boolean needsRegisterCache() {
		return false;
	}

	@Override
	public DataSize getResultingDataSize() {
		return this.arch.getPointerSize();
	}
	
}
