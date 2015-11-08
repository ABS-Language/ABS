public class GramaticConfigs {
	public final static Symbol[] SPECIAL_SYMBOLS = { 
			new Symbol("ako", Consts.CONDITIONAL_OPERATORS.IF),
			new Symbol("inache", Consts.CONDITIONAL_OPERATORS.ELSE),
			
			new Symbol("za", Consts.LOOPS.FOR),
			
			new Symbol("dokato", Consts.LOOPS.WHILE),
			
			new Symbol("vazmojnost", Consts.CONDITIONAL_OPERATORS.SWITCH),
			new Symbol("sluchai", Consts.CONDITIONAL_OPERATORS.CASE),
			
			new Symbol("cqlo", Consts.DEFINITION_TYPES.INTEGER),
			new Symbol("drobno", Consts.DEFINITION_TYPES.DOUBLE),
			new Symbol("simvol", Consts.DEFINITION_TYPES.CHAR),
			new Symbol("niz", Consts.DEFINITION_TYPES.STRING),
			
			new Symbol("izvedi", Consts.IO.OUTPUT),
			new Symbol("vuvedi", Consts.IO.INPUT),
			
			new Symbol("stava", Consts.OPERATORS.MOV),
			new Symbol("razlichno", Consts.OPERATORS.DIFF),
			
			new Symbol("pogolqmo", Consts.OPERATORS.GREATER),
			new Symbol("pomalko", Consts.OPERATORS.LESS)
	};
	
	public final static Symbol[] SEPARATORS = {
		new Symbol("'", Consts.CHARACTERS.APOSTROPHE),
		new Symbol("\"", Consts.CHARACTERS.QUOTE),
		new Symbol("(", Consts.CHARACTERS.LEFT_BRACKET),
		new Symbol(")", Consts.CHARACTERS.RIGHT_BRACKET),
		new Symbol("[", Consts.CHARACTERS.LEFT_SQUARE_BRACKET),
		new Symbol("]", Consts.CHARACTERS.RIGHT_SQUARE_BRACKET),
		new Symbol("{", Consts.CHARACTERS.LEFT_CURLY_BRACKET),
		new Symbol("}", Consts.CHARACTERS.RIGHT_CURLY_BRACKET),
		new Symbol(";", Consts.EOS),
		new Symbol("+", Consts.OPERATORS.ADD),
		new Symbol("-", Consts.OPERATORS.SUB),
		new Symbol("*", Consts.OPERATORS.MUL),
		new Symbol("/", Consts.OPERATORS.DIV),
		new Symbol("%", Consts.OPERATORS.MOD),
		new Symbol("ravno", Consts.OPERATORS.EQU),
		new Symbol(" ", Consts.SEPARATORS.ASCII_SPACE),
		new Symbol("	", Consts.SEPARATORS.ASCII_SPACE),
		new Symbol(",", Consts.CHARACTERS.COMMA)
	};
}	
