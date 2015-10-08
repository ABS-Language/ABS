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
			new Symbol("izvedi", Consts.IO.OUTPUT),
			new Symbol("vuvedi", Consts.IO.INPUT)
	};
}	
