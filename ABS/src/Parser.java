import java.util.ArrayList;

public class Parser {
	private Tetrada tetrada;
	private ArrayList<Position> codeOrder;
	private Hash symbols; 
	private int currentIndex = 0;
	private Symbol currentSymbol;
	private Position receiver;
	
	private int nextVar = 1;
	
	public Parser(ArrayList<Position> codeOrder, Hash symbols ) {
//		this.tetrada = new Tetrada();
		this.tetrada = new Tetrada(symbols); //TODO: constructor for testing purpose
		
		this.codeOrder = codeOrder;
		this.symbols = symbols;
	}
	
	public boolean read() throws SyntaxException {
		codeBlock();
		
		if(getNextSymbol() != Consts.UNKNOWN) {
			throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_EOF);
		}
		
		Print(tetrada.toString());
		
		return true;
	}
	
	private void codeBlock() throws SyntaxException {
		if(getNextSymbol() != Consts.CHARACTERS.LEFT_CURLY_BRACKET) {
			throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_LEFT_CURLY_BRACKET);
		}

		Operator();
		
		while(getNextSymbol() != Consts.CHARACTERS.RIGHT_CURLY_BRACKET) {
			this.currentIndex--;
			Operator();
		}
		this.currentIndex--;
		
		if(getNextSymbol() != Consts.CHARACTERS.RIGHT_CURLY_BRACKET) {
			throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_RIGHT_CURLY_BRACKET);
		}
	}
	
	
	private void Operator() throws SyntaxException{
		Symbol Op1;
		
		switch(getNextSymbol()) {
			case Consts.LEXICALS.IDENTIFIER : {
				if(this.currentSymbol.getType() == Consts.TYPES.UNKNOWN_TYPE){
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_VARIABLE);
				}
				
				this.receiver = this.currentSymbol.getPosition();
				
				if(getNextSymbol() != Consts.OPERATORS.MOV) { //'stava'
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_SET_OPERATOR);
				}
				
				Op1 = this.currentSymbol;
				
				Symbol Op2 = new Symbol();
				Op2 = Expression(Op2);
				Print("OP2 :: " );
				
				if(getNextSymbol() != Consts.EOS) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_EOS);
				}
				
				switch(Op1.getType()) {
					case Consts.TYPES.INTEGER : {
						if(Op2.getType() == Consts.TYPES.INTEGER
						|| Op2.getType() == Consts.TYPES.DOUBLE) {
							tetrada.add(new Row(Consts.OPERATORS.MOV, Op1.getPosition(), Op2.getPosition(), symbols.insert(new Symbol())));
						}
						else {
							throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_OPERATOR_TYPES);
						}
						break;
					}
					case Consts.TYPES.DOUBLE : {
						if(Op2.getType() == Consts.TYPES.DOUBLE
						|| Op2.getType() == Consts.TYPES.INTEGER) {
							tetrada.add(new Row(Consts.OPERATORS.MOV, Op1.getPosition(), Op2.getPosition(), symbols.insert(new Symbol())));
						}
						else {
							throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_OPERATOR_TYPES);
						}
						break;
					}
					case Consts.TYPES.CHAR : {
						if(Op2.getType() == Consts.TYPES.CHAR) {
							tetrada.add(new Row(Consts.OPERATORS.MOV, Op1.getPosition(), Op2.getPosition(), symbols.insert(new Symbol())));
						}
						else {
							throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_OPERATOR_TYPES);
						}
						break;
					}
					case Consts.TYPES.STRING : {
						if(Op2.getType() == Consts.TYPES.STRING
						|| Op2.getType() == Consts.TYPES.CHAR) {
							tetrada.add(new Row(Consts.OPERATORS.MOV, Op1.getPosition(), Op2.getPosition(), symbols.insert(new Symbol())));
						}
						else {
							throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_OPERATOR_TYPES);
						}
						break;
					}
				}

				break;
			}			
			case Consts.CONDITIONAL_OPERATORS.IF : {
				if(getNextSymbol() != Consts.CHARACTERS.LEFT_BRACKET) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_LEFT_BRACKET);
				}
				
			//	Expression(); TODO: 
				
				if(getNextSymbol() != Consts.CHARACTERS.RIGHT_BRACKET) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_RIGHT_BRACKET);
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
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_LEFT_BRACKET);
				}
				
			//	Expression(); TODO: 
				
				if(getNextSymbol() != Consts.CHARACTERS.RIGHT_BRACKET) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_RIGHT_BRACKET);
				}
				//====
				codeBlock();
				//====
				break;
			}			
			case Consts.LOOPS.FOR : {
				if(getNextSymbol() != Consts.CHARACTERS.LEFT_BRACKET) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_LEFT_BRACKET);
				}
				
				Operator();
				
				if(getNextSymbol() != Consts.EOS) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_EOS);
				}
				//===
			//	Expression(); TODO: 
				
				if(getNextSymbol() != Consts.EOS) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_EOS);
				}
				//===	
				
				Operator();
				
				if(getNextSymbol() != Consts.CHARACTERS.RIGHT_BRACKET) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_RIGHT_BRACKET);
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
				throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_OPERATOR);
			}
		}
	}
	
	private Symbol Expression(Symbol Op1) throws SyntaxException{
		Symbol Op2 = new Symbol();
		
		Op1 = Term(Op1);

		while(currentSymbol.getCode() == Consts.OPERATORS.ADD ||
				currentSymbol.getCode() == Consts.OPERATORS.SUB){
			int opCode = currentSymbol.getCode();
			Op2 = Term(Op2);
			
			if(Op1.getType() == Op2.getType()) {
				Symbol result = new Symbol("&" + nextVar++, Consts.LEXICALS.IDENTIFIER, Consts.TYPES.INTEGER);
				
				tetrada.add(new Row(opCode, Op1.getPosition(), Op2.getPosition(), symbols.insert(result)));
				
				Op1 = result;
			}
			else {
				throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_OPERATOR_TYPES);
			}
		}
		
		//if the read 'next symbol' doesnt pass the prev symbol position is returned
		//to avoid reading the next symbol instead"**
		currentIndex--;
		
		return Op1;
	}
	
	private Symbol Term(Symbol Op1) throws SyntaxException{
		Symbol Op2 = new Symbol();
		
		Op1 = Factor();
		
		while(getNextSymbol() == Consts.OPERATORS.MUL 
				|| currentSymbol.getCode() == Consts.OPERATORS.DIV 
				|| currentSymbol.getCode() == Consts.OPERATORS.GREATER 
				|| currentSymbol.getCode() == Consts.OPERATORS.LESS){ //TODO: nameri mqsto na greater i less
			
			Op2 = Factor();
			int opCode = currentSymbol.getCode();
			
			if(Op1.getType() == Op2.getType()) {
				Symbol result = new Symbol("&" + nextVar++, Consts.LEXICALS.IDENTIFIER, Consts.TYPES.INTEGER);
				
				tetrada.add(new Row(opCode, Op1.getPosition(), Op2.getPosition(), symbols.lookupInsert(result)));
				
				Op1 = result;
			}
			else {
				throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_OPERATOR_TYPES);
			}
		}
		
		return Op1;
	}
	
	private Symbol Factor() throws SyntaxException{
		switch(getNextSymbol()) {
			case Consts.CHARACTERS.LEFT_BRACKET : {
				Symbol op = Expression(this.currentSymbol);
				
				if(getNextSymbol() != Consts.CHARACTERS.RIGHT_BRACKET) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_RIGHT_BRACKET);
				}

				return op;
			}
			case Consts.LEXICALS.IDENTIFIER : {
				if(this.currentSymbol.getType() == Consts.TYPES.UNKNOWN_TYPE) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_VARIABLE);
				}
				
				tetrada.add(new Row(Consts.OPERATORS.MOV, this.currentSymbol.getPosition(), null, this.receiver ));
				
				return this.currentSymbol;
			}
			case Consts.LEXICALS.CONSTANT : {
				if(isInt(this.currentSymbol)) {
					this.currentSymbol.setType(Consts.TYPES.INTEGER);
				}
				else if(isDouble(this.currentSymbol)) {
					this.currentSymbol.setType(Consts.TYPES.DOUBLE);
				}
				
				tetrada.add(new Row(Consts.OPERATORS.MOV, this.currentSymbol.getPosition(), null, this.receiver ));
				
				return this.currentSymbol;
			}
			
			case Consts.CHARACTERS.APOSTROPHE : {
				if(getNextSymbol() != Consts.LEXICALS.CONSTANT) { 
					throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_CHAR);
				}
				
				this.currentSymbol.setType(Consts.TYPES.CHAR);
				Position pos = this.currentSymbol.getPosition();
				
				if(getNextSymbol() != Consts.CHARACTERS.APOSTROPHE) { 
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_APOSTROPHE);
				}
				
				tetrada.add(new Row(Consts.OPERATORS.MOV, pos, null, this.receiver ));
				
				return this.currentSymbol;
			}
			
			case Consts.CHARACTERS.QUOTE : {
				if(getNextSymbol() != Consts.LEXICALS.CONSTANT) { 
					throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_STRING);
				}
				
				this.currentSymbol.setType(Consts.TYPES.STRING);
				Position pos = this.currentSymbol.getPosition();
				
				if(getNextSymbol() != Consts.CHARACTERS.QUOTE) { 
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_QUOTE);
				}
				
				tetrada.add(new Row(Consts.OPERATORS.MOV, pos, null, this.receiver ));
				
				return this.currentSymbol;
			}
			
			default : {
				throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_EXPRESSION);
			}
		}
	}

	private void dataDefinition() throws SyntaxException {
		switch(this.currentSymbol.getCode()) {
			case Consts.DEFINITION_TYPES.INTEGER: {
				if(getNextSymbol() != Consts.LEXICALS.IDENTIFIER){
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_IDENTIFIER);
				}
				
				currentSymbol.setType(Consts.TYPES.INTEGER);
				
				if(this.getNextSymbol() == Consts.OPERATORS.MOV) {
					Symbol op = new Symbol();
					op = Expression(op);

					if(op.getType() != Consts.TYPES.INTEGER
						&& op.getType() != Consts.TYPES.DOUBLE) {
						throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_OPERATOR_TYPES);
					}
				}
				else {
					//if you have getNextSymbol() + '==' 
					//always return the index if the check fails
					this.currentIndex--;
				}
				
				if(getNextSymbol() != Consts.EOS) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_EOS);
				}
				break;
			}
			case Consts.DEFINITION_TYPES.DOUBLE: {
				if(this.getNextSymbol() != Consts.LEXICALS.IDENTIFIER){
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_IDENTIFIER);
				}
				
				currentSymbol.setType(Consts.TYPES.DOUBLE);
				
				if(this.getNextSymbol() == Consts.OPERATORS.MOV){
					Symbol op = new Symbol();
					op = Expression(op);

					if(op.getType() != Consts.TYPES.INTEGER
						&& op.getType() != Consts.TYPES.DOUBLE) {
						throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_OPERATOR_TYPES);
					}
				}
				else {
					//if you have getNextSymbol() + '==' 
					//always return the index if the check fails
					this.currentIndex--;
				}
				
				if(getNextSymbol() != Consts.EOS) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_EOS);
				}
				break;
			}
			case Consts.DEFINITION_TYPES.CHAR: {
				if(this.getNextSymbol() != Consts.LEXICALS.IDENTIFIER){
					throw new SyntaxException(this.currentSymbol.getName(),
							Consts.ERRORS.SYNTAX.NOT_FOUND_IDENTIFIER);
				}
				
				currentSymbol.setType(Consts.TYPES.CHAR);
				
				if(getNextSymbol() == Consts.OPERATORS.MOV) {
					if(getNextSymbol() == Consts.CHARACTERS.APOSTROPHE){
						if(getNextSymbol() != Consts.LEXICALS.CONSTANT) {
							throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_CHAR);
						}
						
						if(this.currentSymbol.getName().length() != 1) {
							throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_CHAR);
						}
						
						this.currentSymbol.setType(Consts.TYPES.CHAR);
						
						if(getNextSymbol() != Consts.CHARACTERS.APOSTROPHE){
							throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_APOSTROPHE);
						}						
					}
					else if(this.currentSymbol.getCode() == Consts.LEXICALS.IDENTIFIER) {
						if(this.currentSymbol.getType() == Consts.TYPES.UNKNOWN_TYPE) {
							throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_VARIABLE);
						}
						if(this.currentSymbol.getType() != Consts.TYPES.CHAR) {
							throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_OPERATOR_TYPES);
						}
					}
					else {
						throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_CHAR);
					}					
				}
				else {
					//if you have getNextSymbol() + '==' 
					//always return the index if the check fails
					this.currentIndex--;
				}
				
				if(getNextSymbol() != Consts.EOS) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_EOS);
				}
				
				break;
			}
			case Consts.DEFINITION_TYPES.STRING: {
				if(this.getNextSymbol() != Consts.LEXICALS.IDENTIFIER){
					throw new SyntaxException(this.currentSymbol.getName(),
							Consts.ERRORS.SYNTAX.NOT_FOUND_IDENTIFIER);
				}
				
				currentSymbol.setType(Consts.TYPES.STRING);
				
				if(this.getNextSymbol() == Consts.OPERATORS.MOV){
					if(this.getNextSymbol() == Consts.CHARACTERS.QUOTE){
						if(this.getNextSymbol() != Consts.LEXICALS.CONSTANT) {
							throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_STRING);
						}
						
						this.currentSymbol.setType(Consts.TYPES.STRING);
						
						if(this.getNextSymbol() != Consts.CHARACTERS.QUOTE){
							throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_QUOTE);
						}
					}
					else if(this.currentSymbol.getCode() == Consts.LEXICALS.IDENTIFIER) {
						if(this.currentSymbol.getType() == Consts.TYPES.UNKNOWN_TYPE) {
							throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_VARIABLE);
						}
						if(this.currentSymbol.getType() != Consts.TYPES.STRING) {
							throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_OPERATOR_TYPES);
						}
					}
					else {
						throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_STRING);
					}
				}
				else {
					//if you have getNextSymbol() + '==' 
					//always return the index if the check fails
					this.currentIndex--;
				}
				
				if(getNextSymbol() != Consts.EOS) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_EOS);
				}
				
				break;
			}
		}
	}
	
	private int getNextSymbol() {
		try {
			(this.currentSymbol = symbols.get(codeOrder.get(currentIndex++))).getCode();
			Print("getNextSymbol() :: #" + currentIndex + " -> " + this.currentSymbol.getName() + " [type :: " + this.currentSymbol.getType() +"]");
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
	
	//! constants only
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
	
	//! constants only
	private  boolean isDouble(Symbol symbol) {
		try
	     {
	         Double.parseDouble(symbol.getName());
	         return true;
	     }
	     catch(NumberFormatException e)
	     {
	         return false;
	     }
	}
}