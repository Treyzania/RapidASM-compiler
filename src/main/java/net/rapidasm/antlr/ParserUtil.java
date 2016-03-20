package net.rapidasm.antlr;

import org.antlr.v4.runtime.tree.TerminalNode;

import net.rapidasm.antlr.RapidASMParser.NumericImmediateContext;
import net.rapidasm.antlr.RapidASMParser.RegisterContext;
import net.rapidasm.arch.Architecture;

public class ParserUtil {

	public static String getNumericImmediateContent(Architecture arch, NumericImmediateContext nic) {
		
		TerminalNode numberLiteral = nic.NUMBER();
		RegisterContext register = nic.register();
		TerminalNode argument = nic.ALPHANUM();
		TerminalNode exc = nic.EXCLAMATION();
		
		if (numberLiteral != null) {
			return numberLiteral.getText();
		} else if (register != null) {
			
			String regName = register.ALPHANUM().getText();
			
			if (arch.getRegisters().contains(regName)) {
				return regName;
			} else {
				throw new IllegalArgumentException("This arch does not have the " + regName + " register!");
			}
			
		} else if (argument != null) {
			
			return "!!!TODO!!!";
			
		} else if (exc != null) {
			
			return "$";
			
		}
		
		return "!!!ERROR!!!";
		
	}

	public static String getConvertedRegisterName(String string) {
		
		if (!string.startsWith("$")) throw new IllegalArgumentException("That doesn't look like a register name!  It should start with a '$'! (" + string + ")");
		return string.substring(1);
		
	}
	
	public static String tryParseRegisterName(String name) {
		
		try {				
			return getConvertedRegisterName(name);
		} catch (IllegalArgumentException iae) {
			return name;
		}
		
	}
	
}
