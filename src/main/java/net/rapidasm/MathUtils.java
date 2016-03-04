package net.rapidasm;

public class MathUtils {

	public static long parseNumber(String number) {
		
		char typeChar = number.charAt(1);
		String postTypeChars = number.substring(2);
		
		// Change to switch?
		if (typeChar == 'x') { // Hex
			return Long.parseLong(postTypeChars, 16);
		} else if (typeChar == 'o') { // Octal
			return Long.parseLong(postTypeChars, 8);
		} else if (typeChar == 'b') { // Binary
			return Long.parseLong(postTypeChars, 2);
		}
		
		return Long.parseLong(number);
		
	}
	
}
