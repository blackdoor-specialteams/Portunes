package agui;

public class Sanitizer {
	
	public static boolean isCleanInput(String input){
		if(input.contains(";") || input.contains("null") || input.equals("") )
			return false;
		else
			return true;
	}
}
