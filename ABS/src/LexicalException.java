@SuppressWarnings("serial")
public class LexicalException extends Exception{
	LexicalException(String error, int line, int character) {
		super(error.replace("%1", line + "").replace("%2", character + ""));
	}
}
