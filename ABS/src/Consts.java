
public final class Consts {
	public final static int UNKNOWN 			= 666;
	public final static String DEFAULT_STRING 	= "";
	
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
	
	class TYPES {
		public final static int UNKNOWN_TYPE 	= 11;
		public final static int INTEGER			= 12;
		public final static int DOUBLE			= 13;
		public final static int CHAR			= 14;
		public final static int STRING			= 15;
	}
	
	class DEFINITION_TYPES {
		public final static int INTEGER		= 21;
		public final static int DOUBLE		= 22;
		public final static int CHAR		= 23;
		public final static int STRING		= 24;
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
		public final static int MOV			= 107;
		public final static int DIFF		= 108;
		public final static int GREATER		= 109;
		public final static int	LESS		= 110;
		public final static int JMP			= 111;
		public final static int JZ			= 112;
		public final static int JG			= 113;
		public final static int JL			= 114;
	}
	
	class CONDITIONAL_OPERATORS {
		public final static int IF			= 200;
		public final static int ELSE		= 201;
		public final static int SWITCH		= 202;
		public final static int CASE		= 203;
	}
	
	public final static int EOS 			= 300;
	
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
		public final static int DOT						= 509;
		
	}
	
	class IO {
		public final static int INPUT					= 600;
		public final static int OUTPUT					= 601;
	}
	
	class ERRORS {
		class SYNTAX {
			public final static String NOT_FOUND_EOF								= "'%s' found out of the Primary Block.";
			
			public final static String NOT_FOUND_OPERATOR							= "Expected Operator : '%s' found.";
			
			public final static String NOT_FOUND_EQU 								= "Expected '=' : '%s' found.";
			public final static String NOT_FOUND_EOS 								= "Expected ';' : %s' found.";
			public final static String NOT_FOUND_IDENTIFIER							= "Expected Variable : '%s' found.";
			public final static String NOT_FOUND_EXPRESSION							= "Expected Expression : '%s' found.";
			public final static String NOT_FOUND_SET_OPERATOR						= "Expected 'SET' Operator : '%s' found.";
		
			public final static String NOT_FOUND_VARIABLE							= "Variable '%s' not declared.";
			
			public final static String INVALID_INTEGER 								= "Invalid int constant.";
			public final static String INVALID_FLOAT								= "Invalid float constant.";
			public final static String INVALID_CHAR									= "Invalid char constant.";
			public final static String INVALID_STRING								= "Invalid string constant.";
			
			public final static String NOT_FOUND_LEFT_BRACKET						= "Expected '(' : '%s' found.";
			public final static String NOT_FOUND_RIGHT_BRACKET						= "Expected ')' : '%s' found.";
			public final static String NOT_FOUND_LEFT_CURLY_BRACKET					= "Expected '{' : '%s' found.";
			public final static String NOT_FOUND_RIGHT_CURLY_BRACKET				= "Expected '}' : '%s' found.";
			public final static String NOT_FOUND_APOSTROPHE							= "Expected '\'' : '%s' found.";
			public final static String NOT_FOUND_QUOTE								= "Expected '\"' : '%s' found.";
			
			public final static String INVALID_OPERATOR_TYPES						= "Mismatched operator types";
		}
		class LEXICAL {
			public final static String INVALID_CHARACTER							= "Invalid character found! '%1' : '%2'";
		}
	}
}
