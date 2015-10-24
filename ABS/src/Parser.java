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
	
	public boolean read() throws SyntaxException {
		codeBlock();
		
		if(getNextSymbol() != Consts.UNKNOWN) {
			throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_EOF);
		}
		
		return true;
	}
	
	private void codeBlock() throws SyntaxException {
		if(getNextSymbol() != Consts.CHARACTERS.LEFT_CURLY_BRACKET) {
			throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_LEFT_CURLY_BRACKET);
		}

		Operator();

//		while(getNextSymbol() == Consts.EOS) {
//			Operator();
//		}
		
		if(this.getNextSymbol() != Consts.CHARACTERS.RIGHT_CURLY_BRACKET) {
			throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_RIGHT_CURLY_BRACKET);
		}	
	}
	
	private void Operator() throws SyntaxException{
		switch(getNextSymbol()) {
			case Consts.LEXICALS.IDENTIFIER : {
				if(getNextSymbol() != Consts.OPERATORS.MOV) { //'stava'
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_SET_OPERATOR);
				}
				
				Expression();

				break;
			}
			
			case Consts.CONDITIONAL_OPERATORS.IF : {
				if(getNextSymbol() != Consts.CHARACTERS.LEFT_BRACKET) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_LEFT_BRACKET);
				}
				
				Expression();
				
				if(getNextSymbol() != Consts.CHARACTERS.RIGHT_BRACKET) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_RIGHT_BRACKET);
				}
				//====
				codeBlock();
				//====
				break;
//				if(getNextSymbol() == Consts.CONDITIONAL_OPERATORS.ELSE) {
//					codeBlock();
//				}
//				//====
//				break;
			}
			
			case Consts.LOOPS.WHILE : {
				if(getNextSymbol() != Consts.CHARACTERS.LEFT_BRACKET) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_LEFT_BRACKET);
				}
				
				Expression();
				
				if(getNextSymbol() != Consts.CHARACTERS.RIGHT_BRACKET) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_RIGHT_BRACKET);
				}
				//====
				codeBlock();
				//====
				break;
			}
			
			case Consts.LOOPS.FOR : {
				if(getNextSymbol() != Consts.CHARACTERS.LEFT_BRACKET) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_LEFT_BRACKET);
				}
				
				Operator();
				
				if(getNextSymbol() != Consts.EOS) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_EOS);
				}
				//===
				Expression();
				
				if(getNextSymbol() != Consts.EOS) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_EOS);
				}
				//===	
				
				Operator();
				
				if(getNextSymbol() != Consts.CHARACTERS.RIGHT_BRACKET) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_RIGHT_BRACKET);
				}
				//====
				codeBlock();
				break;
			}
			default : {
				throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_OPERATOR);
			}
		}
	}
	
	private void Expression() throws SyntaxException{
		Term();

		while(currentSymbol.getCode() == Consts.OPERATORS.ADD ||
				currentSymbol.getCode() == Consts.OPERATORS.SUB){
			Term();
		}
		//reads next symbol from Term()
		//if it doesnt pass returns to Expression() 
		//if the read 'next symbol' doesnt pass the prev symbol position is returned
		//to avoid reading the next symbol instead"**
		currentIndex--;
	}
	
	private void Term() throws SyntaxException{
		Factor();
		
		while(getNextSymbol() == Consts.OPERATORS.MUL 
				|| currentSymbol.getCode() == Consts.OPERATORS.DIV 
				|| currentSymbol.getCode() == Consts.OPERATORS.GREATER 
				|| currentSymbol.getCode() == Consts.OPERATORS.LESS){
			
			Factor();
		}
	}
	
	private void Factor() throws SyntaxException{
		switch(getNextSymbol()) {
			case Consts.CHARACTERS.LEFT_BRACKET : {
				Expression();
				
				if(getNextSymbol() != Consts.CHARACTERS.RIGHT_BRACKET) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_RIGHT_BRACKET);
				}
				break;
			}
			case Consts.LEXICALS.IDENTIFIER :
			case Consts.LEXICALS.CONSTANT : {
				break;
			}
			default : {
				throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_EXPRESSION);
			}
		}
	}
	
	private boolean dataDefinition() {
		switch(getNextSymbol()) {
			case Consts.DATA_TYPES.INTEGER: {
				if(getNextSymbol() == Consts.LEXICALS.IDENTIFIER) {
					if(getNextSymbol() == Consts.OPERATORS.EQU) {
						if(getNextSymbol() == Consts.LEXICALS.CONSTANT) {
							if(this.isInt(this.currentSymbol)) {
								if(getNextSymbol() == Consts.EOS) {
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
								if(getNextSymbol() == Consts.EOS) {
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
											if(getNextSymbol() == Consts.EOS) {
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
									if(getNextSymbol() == Consts.EOS) {
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
			(this.currentSymbol = symbols.get(codeOrder.get(currentIndex++))).getCode();
			Print("getNextSymbol() :: #" + currentIndex + " -> " + this.currentSymbol.getName());
			return this.currentSymbol.getCode();			
		}
		catch(IndexOutOfBoundsException e) {
			return Consts.UNKNOWN;
		}
	}
	
	private void Print(String x) {
		System.out.println(x);
	}
	
	private void Print(int x) {
		System.out.println(x);
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