package net.rapidasm.arch.x86;

import java.util.ArrayList;
import java.util.List;

import net.rapidasm.arch.Architecture;
import net.rapidasm.arch.CallingConvention;
import net.rapidasm.arch.EmptyConvention;

public class X86Architecture extends Architecture {

	private List<CallingConvention> conventions;
	
	public X86Architecture() {
		
		// FIXME TODO Create actual calling conventions for these ones.
		this.conventions = new ArrayList<>();
		this.conventions.add(new EmptyConvention("test"));
		this.conventions.add(new EmptyConvention("cdecl"));
		this.conventions.add(new EmptyConvention("nocall"));
		
	}
	
	@Override
	public int getPointerSize() {
		return 4;
	}

	@Override
	public long getMaximumAddressableMemory() {
		return 0xffffffff;
	}

	@Override
	public int getWordSize() {
		return 4;
	}

	@Override
	public String getShortName() {
		return "x86";
	}

	@Override
	public List<CallingConvention> getCallingConventions() {
		return this.conventions;
	}
	
}
