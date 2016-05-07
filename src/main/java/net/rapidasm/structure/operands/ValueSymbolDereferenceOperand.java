package net.rapidasm.structure.operands;

import net.rapidasm.arch.Architecture;
import net.rapidasm.asm.AsmOperand;
import net.rapidasm.asm.DataSource;
import net.rapidasm.structure.DataSize;
import net.rapidasm.structure.RapidSubroutine;
import net.rapidasm.structure.symbols.ValueSymbol;

@Deprecated
public class ValueSymbolDereferenceOperand extends Operand {

	private final ValueSymbol value;
	
	public ValueSymbolDereferenceOperand(Architecture arch, RapidSubroutine sub, ValueSymbol value) {
		
		super(arch, sub);
		
		this.value = value;
		
	}

	@Override
	public String getActualOperand() {
		
		// FIXME XXX
		return String.format("(%s)", this.value.name);
		
	}
	
	@Override
	public DataSize getResultingDataSize() {
		return this.value.type.getSize(this.arch);
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
