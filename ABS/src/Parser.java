import java.util.ArrayList;

public class Parser {
	
	private ArrayList<Tetrada> tetradi;
	private ArrayList<Position> codeOrder;
	private Hash symbols;
	private int currentIndex = 0;
	private Symbol currentSymbol;
	
	public Parser(ArrayList<Position> codeOrder, Hash symbols ) {
		this.tetradi = new ArrayList<>();
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
//
//		while(true) {
//			try {
//				Operator();
//			}
//			catch(SyntaxException e) {
//				this.currentIndex--;
//				break;
//			}
//		}
		
		while(getNextSymbol() != Consts.CHARACTERS.RIGHT_CURLY_BRACKET) {
			this.currentIndex--;
			Operator();
		}
		this.currentIndex--;
		
		if(getNextSymbol() != Consts.CHARACTERS.RIGHT_CURLY_BRACKET) {
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
				
				if(getNextSymbol() != Consts.EOS) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_EOS);
				}

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
				if(getNextSymbol() == Consts.CONDITIONAL_OPERATORS.ELSE) {
					codeBlock();
				}
				else {
					//if you have getNextSymbol() + '==' 
					//always return the index if the check fails
					this.currentIndex--;
				}
				//====
				break;
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
			case Consts.DEFINITION_TYPES.INTEGER : 
			case Consts.DEFINITION_TYPES.FLOAT : 
			case Consts.DEFINITION_TYPES.CHAR : 
			case Consts.DEFINITION_TYPES.STRING :  {
				dataDefinition();
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
		
		//if the read 'next symbol' doesnt pass the prev symbol position is returned
				//to avoid reading the next symbol instead"**
				currentIndex--;
	}
	
	private void Term() throws SyntaxException{
		Factor();
		
		while(getNextSymbol() == Consts.OPERATORS.MUL 
				|| currentSymbol.getCode() == Consts.OPERATORS.DIV 
				|| currentSymbol.getCode() == Consts.OPERATORS.GREATER 
				|| currentSymbol.getCode() == Consts.OPERATORS.LESS){ //TODO: nameri mqsto na greater i less
			
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
			case Consts.LEXICALS.IDENTIFIER : {
				if(this.currentSymbol.getType() == Consts.TYPES.UNKNOWN_TYPE) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_VARIABLE);
				}
				break;
			}
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
				if(getNextSymbol() != Consts.LEXICALS.IDENTIFIER){
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_IDENTIFIER);
				}
				
				currentSymbol.setType(Consts.TYPES.INTEGER);
				
				if(this.getNextSymbol() == Consts.OPERATORS.MOV){
					Expression();
				}
				else {
					//if you have getNextSymbol() + '==' 
					//always return the index if the check fails
					this.currentIndex--;
				}
				
				if(getNextSymbol() != Consts.EOS) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_EOS);
				}
				break;
			}
			case Consts.DEFINITION_TYPES.FLOAT: {
				if(this.getNextSymbol() != Consts.LEXICALS.IDENTIFIER){
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_IDENTIFIER);
				}
				
				currentSymbol.setType(Consts.TYPES.FLOAT);
				
				if(this.getNextSymbol() == Consts.OPERATORS.MOV){
					Expression();
				}
				else {
					//if you have getNextSymbol() + '==' 
					//always return the index if the check fails
					this.currentIndex--;
				}
				
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
				
				if(getNextSymbol() == Consts.OPERATORS.MOV) {
					if(getNextSymbol() == Consts.CHARACTERS.APOSTROPHE){
						if(this.getNextSymbol() != Consts.LEXICALS.CONSTANT) {
							throw new SyntaxException(Consts.ERRORS.INVALID_STRING);
						}
						
						if(getNextSymbol() != Consts.CHARACTERS.APOSTROPHE){
							throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_APOSTROPHE);
						}						
					}
					else {
						//if you have getNextSymbol() + '==' 
						//always return the index if the check fails
						this.currentIndex--;
						
						Expression();
					}					
				}
				else {
					//if you have getNextSymbol() + '==' 
					//always return the index if the check fails
					this.currentIndex--;
				}
				
				if(getNextSymbol() != Consts.EOS) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_EOS);
				}
				
				break;
			}
			case Consts.DEFINITION_TYPES.STRING: {
				if(this.getNextSymbol() != Consts.LEXICALS.IDENTIFIER){
					throw new SyntaxException(this.currentSymbol.getName(),
							Consts.ERRORS.NOT_FOUND_IDENTIFIER);
				}
				
				currentSymbol.setType(Consts.TYPES.STRING);
				
				if(this.getNextSymbol() == Consts.OPERATORS.MOV){
					if(this.getNextSymbol() == Consts.CHARACTERS.QUOTE){
						if(this.getNextSymbol() != Consts.LEXICALS.CONSTANT) {
							throw new SyntaxException(Consts.ERRORS.INVALID_STRING);
						}
						
						if(this.getNextSymbol() != Consts.CHARACTERS.QUOTE){
							throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_QUOTE);
						}
					}
					else {
						//if you have getNextSymbol() + '==' 
						//always return the index if the check fails
						this.currentIndex--;
						
						Expression();
					}
				}
				else {
					//if you have getNextSymbol() + '==' 
					//always return the index if the check fails
					this.currentIndex--;
				}
				
				if(getNextSymbol() != Consts.EOS) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.NOT_FOUND_EOS);
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