package net.rapidasm.arch.x86;

import java.util.ArrayList;
import java.util.List;

import net.rapidasm.arch.Architecture;
import net.rapidasm.structure.context.Context;
import net.rapidasm.structure.context.ContextItem;

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

	@Override
	public Context getNewUniversalContext() {
		
		List<ContextItem> items = new ArrayList<ContextItem>();
		
		// TODO Set up registers.
		
		return new Context(items);
	}
	
}
