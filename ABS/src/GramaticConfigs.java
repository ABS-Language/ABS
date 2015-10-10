public class GramaticConfigs {
	public final static Symbol[] SPECIAL_SYMBOLS = { 
			new Symbol("ako", Consts.CONDITIONAL_OPERATORS.IF),
			new Symbol("inache", Consts.CONDITIONAL_OPERATORS.ELSE),
			new Symbol("za", Consts.LOOPS.FOR),
			new Symbol("dokato", Consts.LOOPS.WHILE),
			new Symbol("vazmojnost", Consts.CONDITIONAL_OPERATORS.SWITCH),
			new Symbol("sluchai", Consts.CONDITIONAL_OPERATORS.CASE),
			new Symbol("cqlo", Consts.DATA_TYPES.INTEGER),
			new Symbol("drobno", Consts.DATA_TYPES.FLOAT),
			new Symbol("niz", Consts.DATA_TYPES.STRING),
			new Symbol("simvol", Consts.DATA_TYPES.CHAR),
			new Symbol("izvedi", Consts.IO.OUTPUT),
			new Symbol("vuvedi", Consts.IO.INPUT)
	};
	
	public final static Symbol[] SEPARATORS = {
		new Symbol("(", Consts.CHARACTERS.LEFT_BRACKET),
		new Symbol(")", Consts.CHARACTERS.RIGHT_BRACKET),
		new Symbol("[", Consts.CHARACTERS.LEFT_SQUARE_BRACKET),
		new Symbol("]", Consts.CHARACTERS.RIGHT_SQUARE_BRACKET),
		new Symbol("{", Consts.CHARACTERS.LEFT_CURLY_BRACKET),
		new Symbol("}", Consts.CHARACTERS.RIGHT_CURLY_BRACKET),
		new Symbol(";", Consts.EOS.SEMICOLON),
		new Symbol("+", Consts.OPERATORS.ADD),
		new Symbol("-", Consts.OPERATORS.SUB),
		new Symbol("*", Consts.OPERATORS.MUL),
		new Symbol("/", Consts.OPERATORS.DIV),
		new Symbol("%", Consts.OPERATORS.MOD),
		new Symbol("=", Consts.OPERATORS.EQU),
		new Symbol(" ", Consts.SEPARATORS.SPACE),
		new Symbol("	", Consts.SEPARATORS.TAB),
		new Symbol(",", Consts.CHARACTERS.COMMA)
	};
}	
