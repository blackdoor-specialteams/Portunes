package ugui;

public class Sanitizer {
	
	public static boolean isCleanInput(String input){
		if(input.contains(";") || input.contains("null") || input.equals("") || input.contains("'"))
			return false;
		else
			return true;
	}
}
