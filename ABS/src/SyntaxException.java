@SuppressWarnings("serial")
public class SyntaxException extends Exception{
	SyntaxException(String found, String expected) {
		super(expected.replace("%s", found));
	}
	
	SyntaxException(String error) {
		super(error);
	}
}
