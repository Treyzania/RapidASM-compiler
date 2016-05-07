package net.rapidasm.structure.operands;

import net.rapidasm.arch.Architecture;
import net.rapidasm.asm.AsmOperand;
import net.rapidasm.asm.DataSource;
import net.rapidasm.structure.DataSize;
import net.rapidasm.structure.RapidSubroutine;
import net.rapidasm.structure.symbols.ValueSymbol;

@Deprecated
public class ValueSymbolOperand extends Operand {

	private final ValueSymbol value;
	
	public ValueSymbolOperand(Architecture arch, RapidSubroutine sub, ValueSymbol value) {
		
		super(arch, sub);
		
		this.value = value;
		
	}

	@Override
	public String getActualOperand() {
		
		// FIXME XXX
		return "$" + this.value.name;
		
	}
	
	@Override
	public DataSize getResultingDataSize() {
		return this.arch.getPointerSize();
	}

	@Override
	public AsmOperand getAsmOperand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataSource getSource() {
		return DataSource.MEMORY;
	}
	
}
