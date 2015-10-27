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
		
		if(this.currentSymbol.getCode() != Consts.CHARACTERS.RIGHT_CURLY_BRACKET) {
			throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_RIGHT_CURLY_BRACKET);
		}	
	}
	
	private void Operator() throws SyntaxException{
		switch(getNextSymbol()) {
			case Consts.LEXICALS.IDENTIFIER : {
				if(this.currentSymbol.getType() == Consts.TYPES.UNKNOWN_TYPE){
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_VARIABLE);
				}
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
<<<<<<< HEAD
			case Consts.DATA_TYPES.INTEGER : 
			case Consts.DATA_TYPES.FLOAT : 
			case Consts.DATA_TYPES.CHAR : 
			case Consts.DATA_TYPES.STRING :  {
				dataDefinition();
				break;
			}
=======
			case Consts.DEFINITION_TYPES.INTEGER : 
			case Consts.DEFINITION_TYPES.DOUBLE : 
			case Consts.DEFINITION_TYPES.CHAR : 
			case Consts.DEFINITION_TYPES.STRING : {
				dataDefinition();
				break;
			}
			case Consts.EOS : {
				break;
			}
>>>>>>> origin/master
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

	private void dataDefinition() throws SyntaxException {
		switch(this.currentSymbol.getCode()) {
			case Consts.DEFINITION_TYPES.INTEGER: {
				if(this.getNextSymbol() != Consts.LEXICALS.IDENTIFIER){
					throw new SyntaxException(this.currentSymbol.getName(),
							Consts.ERRORS.NOT_FOUND_IDENTIFIER);
				}
				
				currentSymbol.setType(Consts.TYPES.INTEGER);
				
				if(this.getNextSymbol() != Consts.OPERATORS.MOV){
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_SET_OPERATOR);
				}
				
				Expression();
				
				if(getNextSymbol() != Consts.EOS) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_EOS);
				}
				break;
			}
			
			case Consts.DATA_TYPES.FLOAT : {
				if(getNextSymbol() != Consts.LEXICALS.IDENTIFIER) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_IDENTIFIER);
				}
				
				if(getNextSymbol() != Consts.OPERATORS.MOV) {

				break;
			}
			case Consts.DEFINITION_TYPES.FLOAT: {
				if(this.getNextSymbol() != Consts.LEXICALS.IDENTIFIER){
					throw new SyntaxException(this.currentSymbol.getName(),
							Consts.ERRORS.NOT_FOUND_IDENTIFIER);
				}
				
				currentSymbol.setType(Consts.TYPES.FLOAT);
				
				if(this.getNextSymbol() != Consts.OPERATORS.MOV){
				throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_SET_OPERATOR);
				}
				
				Expression();
			
				if(getNextSymbol() != Consts.EOS) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_EOS);
				}
				break;
			}
			
			case Consts.DATA_TYPES.CHAR : {
				if(getNextSymbol() != Consts.LEXICALS.IDENTIFIER) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_IDENTIFIER);
				}
				
				if(getNextSymbol() != Consts.OPERATORS.MOV) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_SET_OPERATOR);
				}
				
				Expression();
								
				if(getNextSymbol() != Consts.EOS) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_EOS);
				}
				break;
			}
			
			case Consts.DATA_TYPES.STRING : {
				if(getNextSymbol() != Consts.LEXICALS.IDENTIFIER) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_IDENTIFIER);
				}
				
				if(getNextSymbol() != Consts.OPERATORS.MOV) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_SET_OPERATOR);
				}
				
				Expression();
				
				if(getNextSymbol() != Consts.EOS) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_EOS);
				}
				break;
			}
			case Consts.DEFINITION_TYPES.CHAR: {
				if(this.getNextSymbol() != Consts.LEXICALS.IDENTIFIER){
					throw new SyntaxException(this.currentSymbol.getName(),
							Consts.ERRORS.NOT_FOUND_IDENTIFIER);
				}
				
				currentSymbol.setType(Consts.TYPES.CHAR);
				
				if(this.getNextSymbol() != Consts.OPERATORS.MOV){
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_SET_OPERATOR);
				}
				
				if(this.getNextSymbol() != Consts.CHARACTERS.APOSTROPHE){
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_APOSTROPHE);
				}
				
				Expression();
				
				if(this.getNextSymbol() != Consts.CHARACTERS.APOSTROPHE){
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_APOSTROPHE);
				}
				
				break;
			}
			case Consts.DEFINITION_TYPES.STRING: {
				if(this.getNextSymbol() != Consts.LEXICALS.IDENTIFIER){
					throw new SyntaxException(this.currentSymbol.getName(),
							Consts.ERRORS.NOT_FOUND_IDENTIFIER);
				}
				
				currentSymbol.setType(Consts.TYPES.STRING);
				
				if(this.getNextSymbol() != Consts.OPERATORS.MOV){
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_SET_OPERATOR);
				}
				
				if(this.getNextSymbol() == Consts.CHARACTERS.QUOTE){
					if(this.getNextSymbol() != Consts.LEXICALS.CONSTANT) {
						throw new SyntaxException(Consts.ERRORS.INVALID_STRING);
					}
					
					if(this.getNextSymbol() != Consts.CHARACTERS.QUOTE){
						throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_QUOTE);
					}
				}
				else {
					Expression();
				}
				
				break;
			}
		}
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