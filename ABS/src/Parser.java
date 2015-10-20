import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;

public class Parser {
	
	private ArrayList<Position> codeOrder;
	private Hash symbols;
	private int currentIndex = 0;
	private Symbol currentSymbol;
	
	public Parser(ArrayList<Position> codeOrder, Hash symbols ) {
		this.codeOrder = codeOrder;
		this.symbols = symbols;
	}
	
	public boolean read() {
		System.out.println(dataDefinition());
		
		return true;
	}
	
	private boolean codeBlock() {
		return false;
	}
	
	private boolean operator() {
		return false;
	}
	
//	private boolean conditionIf(){
//		if(this.getNextSymbol() == Consts.CONDITIONAL_OPERATORS.IF){
//			if(this.getNextSymbol() == Consts.CHARACTERS.LEFT_BRACKET){
//				if(this.getNextSymbol() == Consts.LEXICALS.IDENTIFIER
//						|| currentSymbol.getCode() == Consts.LEXICALS.CONSTANT){
//					
//				}
//			}
//		}
//	}
	
	private void Operator() throws SyntaxException{
		switch(getNextSymbol()){
			case Consts.LEXICALS.IDENTIFIER : {
				if(getNextSymbol() != Consts.OPERATORS.MOV){
					throw new SyntaxException("Expression expected!");
				}
				Expression();
				break;
			}
			
			case Consts.OPERATORS.MOV : {
				break;
			}
			
			case Consts.CONDITIONAL_OPERATORS.IF : {
				break;
			}
			
			case Consts.LOOPS.WHILE : {
				break;
			}
			
			case Consts.LOOPS.FOR : {
				break;
			}
		}
		
		
		
	}
	
	private void Expression() throws SyntaxException{
		Term();
		while(getNextSymbol() == Consts.OPERATORS.ADD ||
				currentSymbol.getCode() == Consts.OPERATORS.SUB){
			Term();
		}
	}
	
	private void Term() throws SyntaxException{
		Factor();
		while(getNextSymbol() == Consts.OPERATORS.MUL ||
				currentSymbol.getCode() == Consts.OPERATORS.DIV){
			Factor();
		}
	}
	
	private void Factor() throws SyntaxException{
		if(getNextSymbol() == Consts.CHARACTERS.LEFT_BRACKET){
			Expression();
		}
		
		if(getNextSymbol() != Consts.CHARACTERS.RIGHT_BRACKET){
			throw new SyntaxException("Right braket expected"); // TODO fix error constants
		}
		
		if(getNextSymbol() != Consts.LEXICALS.IDENTIFIER 
				&& currentSymbol.getCode() != Consts.LEXICALS.CONSTANT){
			throw new SyntaxException("Expected Identifier or constant zemi fuste");
		}
	}
	
	private boolean dataDefinition() {
		switch(getNextSymbol()) {
			case Consts.DATA_TYPES.INTEGER: {
				if(getNextSymbol() == Consts.LEXICALS.IDENTIFIER) {
					if(getNextSymbol() == Consts.OPERATORS.EQU) {
						if(getNextSymbol() == Consts.LEXICALS.CONSTANT) {
							if(this.isInt(this.currentSymbol)) {
								if(getNextSymbol() == Consts.EOS.SEMICOLON) {
									return true;
								}
								else {
									Print(Consts.ERRORS.NOT_FOUND_EOS);
									return false;
								}
							}
							else {
								Print(Consts.ERRORS.INVALID_INTEGER);
								return false;
							}
						}
						else {
							Print(Consts.ERRORS.INVALID_INTEGER);
							return false;
						}
					}
					else {
						Print(Consts.ERRORS.NOT_FOUND_EQU);
						return false;
					}
				}
				else {
					Print(Consts.ERRORS.NOT_FOUND_IDENTIFIER);
					return false;
				}
			}
			case Consts.DATA_TYPES.FLOAT: {
				if(getNextSymbol() == Consts.LEXICALS.IDENTIFIER) {
					if(getNextSymbol() == Consts.OPERATORS.EQU) {
						if(getNextSymbol() == Consts.LEXICALS.CONSTANT) {
							if(this.isFloat(this.currentSymbol)) { //TODO: pass only float values and not integers
								if(getNextSymbol() == Consts.EOS.SEMICOLON) {
									return true;
								}
								else {
									Print(Consts.ERRORS.NOT_FOUND_EOS);
									return false;
								}
							}
							else {
								Print(Consts.ERRORS.INVALID_FLOAT);
								return false;
							}
						}
						else {
							Print(Consts.ERRORS.INVALID_FLOAT);
							return false;
						}
					}
					else {
						Print(Consts.ERRORS.NOT_FOUND_EQU);
						return false;
					}
				}
				else {
					Print(Consts.ERRORS.NOT_FOUND_IDENTIFIER);
					return false;
				}
			}
			case Consts.DATA_TYPES.CHAR: {
				if(getNextSymbol() == Consts.LEXICALS.IDENTIFIER) {
					if(getNextSymbol() == Consts.OPERATORS.EQU) {
						if(getNextSymbol() == Consts.CHARACTERS.APOSTROPHE) {
								if(getNextSymbol() == Consts.LEXICALS.CONSTANT) {
									if(this.currentSymbol.getName().length() == 1) {
										if(getNextSymbol() == Consts.CHARACTERS.APOSTROPHE) {
											if(getNextSymbol() == Consts.EOS.SEMICOLON) {
												return true;
											}
											else {
												Print(Consts.ERRORS.NOT_FOUND_EOS);
												return false;
											}
										}
										else {
												Print(Consts.ERRORS.INVALID_CHAR);
												return false;
										}
							}
							else {
									Print(Consts.ERRORS.INVALID_CHAR);
									return false;
							}
						}
						else {
								Print(Consts.ERRORS.INVALID_CHAR);
								return false;
						}
					}
					else {
							Print(Consts.ERRORS.INVALID_CHAR);
							return false;
					}
				}
				else {
						Print(Consts.ERRORS.NOT_FOUND_EQU);
						return false;
					}
				}
				else {
					Print(Consts.ERRORS.NOT_FOUND_IDENTIFIER);
					return false;
				}
			}
			case Consts.DATA_TYPES.STRING: {
				if(getNextSymbol() == Consts.LEXICALS.IDENTIFIER) {
					if(getNextSymbol() == Consts.OPERATORS.EQU) {
						if(getNextSymbol() == Consts.CHARACTERS.QUOTE) {
							if(getNextSymbol() == Consts.LEXICALS.CONSTANT) {
								if(getNextSymbol() == Consts.CHARACTERS.QUOTE) {
									if(getNextSymbol() == Consts.EOS.SEMICOLON) {
											return true;
									}
									else {
										Print(Consts.ERRORS.NOT_FOUND_EOS);
										return false;
									}
								}
								else {
									Print(Consts.ERRORS.INVALID_STRING);
									return false;
								}
							}
						}
						else {
							Print(Consts.ERRORS.INVALID_STRING);
							return false;
						}
					}
					else {
						Print(Consts.ERRORS.NOT_FOUND_EQU);
						return false;
					}
				}
				else {
					Print(Consts.ERRORS.NOT_FOUND_IDENTIFIER);
					return false;
				}
			}
		}
		return false;
	}
	
	private int getNextSymbol() {
		try {
			return (this.currentSymbol = symbols.get(codeOrder.get(currentIndex++))).getCode();			
		}
		catch(IndexOutOfBoundsException e) {
			return Consts.UNKNOWN;}
	}
	
	private void Print(String str) {
		System.out.println(str);
	}
	
	private  boolean isInt(Symbol symbol) {
		try
	     {
	         Integer.parseInt(symbol.getName());
	         return true;
	     }
	     catch(NumberFormatException e)
	     {
	         return false;
	     }
	}
	
	private  boolean isFloat(Symbol symbol) {
		try
	     {
	         Float.parseFloat(symbol.getName());
	         return true;
	     }
	     catch(NumberFormatException e)
	     {
	         return false;
	     }
	}	
}