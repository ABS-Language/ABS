
public final class Consts {
	public final static int UNKNOWN 		= 666;
	
	class FORBIDDEN {
		public final static int ASCII_AT	= 64;
	}
	
	class PERMITTED {
		public final static int ASCII_LOW	= 32;
		public final static int ASCII_HIGH	= 125;
		
		public final static int ASCII_CYRILLIC_LOW	= 192;
		public final static int ASCII_CYRILLIC_HIGH	= 255;
	}
	
	class LEXICALS{
		public final static int IDENTIFIER 				= 1;
		public final static int CONSTANT				= 2;
	}
	
	class CONSTANTS {
		public final static int INTEGER		= 12;
		public final static int FLOAT		= 13;
		public final static int DOUBLE		= 14;
		public final static int CHAR		= 15;
		public final static int STRING		= 16;
	}
	
	class DATA_TYPES {
		public final static int INTEGER		= 21;
		public final static int FLOAT		= 22;
		public final static int DOUBLE		= 23;
		public final static int CHAR		= 24;
		public final static int STRING		= 25;
	}
	
	class SEPARATORS {
		public final static int ASCII_SPACE		= 32;
		public final static int ASCII_TAB 		= 9;
	}
	
	class OPERATORS {
		public final static int ADD			= 100;
		public final static int SUB			= 101;
		public final static int MUL			= 102;
		public final static int DIV			= 103;
		public final static int POW			= 104;
		public final static int MOD			= 105;
		public final static int EQU			= 106;
	}
	
	class CONDITIONAL_OPERATORS {
		public final static int IF			= 200;
		public final static int ELSE		= 201;
		public final static int SWITCH		= 202;
		public final static int CASE		= 203;
	}
	
	class EOS {
		public final static int SEMICOLON 	= 300;
	}
	
	class LOOPS {
		public final static int WHILE 		= 400;
		public final static int FOR			= 401;
	}
	
	class CHARACTERS {
		public final static int LEFT_BRACKET 			= 500;
		public final static int RIGHT_BRACKET			= 501;
		public final static int LEFT_CURLY_BRACKET		= 502;
		public final static int RIGHT_CURLY_BRACKET		= 503;
		public final static int LEFT_SQUARE_BRACKET 	= 504;
		public final static int RIGHT_SQUARE_BRACKET 	= 505;
		
		public final static int QUOTE					= 506;
		public final static int APOSTROPHE				= 507;
		public final static int COMMA					= 508;
	}
	
	class IO {
		public final static int INPUT					= 600;
		public final static int OUTPUT					= 601;
	}
	
	class ERRORS { // TODO: '' EXPECTED BUT '' FOUND
		public final static String NOT_FOUND_EQU 								= "'=' expected.";
		public final static String NOT_FOUND_EOS 								= "';' expected.";
		public final static String NOT_FOUND_IDENTIFIER							= "Identifier expected.";
		
		
		public final static String INVALID_INTEGER 								= "Invalid int constant.";
		public final static String INVALID_FLOAT								= "Invalid float constant.";
		public final static String INVALID_CHAR									= "Invalid char constant.";
		public final static String INVALID_STRING								= "Invalid string constant.";
	}
}
