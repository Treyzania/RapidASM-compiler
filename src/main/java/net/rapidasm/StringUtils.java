package net.rapidasm;

public class StringUtils {

	public static String replaceVars(String input, String varName, String fill) {
		return input.replace(getVarFiller(varName), fill);
	}
	
	public static String getVarFiller(String var) {
		return "${" + var + "}";
	}
	
}
