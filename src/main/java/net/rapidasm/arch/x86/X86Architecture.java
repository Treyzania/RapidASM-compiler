package net.rapidasm.arch.x86;

import net.rapidasm.arch.Architecture;

public class X86Architecture extends Architecture {

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

}
