import java.util.ArrayList;

public class Parser {
	private Tetrada tetrada;
	
	private ArrayList<Position> codeOrder;
	private final ArrayList<Integer> branches = new ArrayList<>();
	
	private Hash symbols; 
	private int currentIndex = 0;
	private Symbol currentSymbol;
	private Symbol receiver;
	
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
		
		tetrada.add(new Row(Consts.PROGRAM_END, null, null, null));
		
		setBranches();
		
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
				
				this.receiver = this.currentSymbol;
				
				if(getNextSymbol() != Consts.OPERATORS.MOV) { //'stava'
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_SET_OPERATOR);
				}
				
				Op1 = this.currentSymbol;
				
				Symbol Op2 = new Symbol();
				Op2 = Expression(Op2);
				
				if(getNextSymbol() != Consts.EOS) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_EOS);
				}
				
				switch(Op1.getType()) {
					case Consts.TYPES.INTEGER : {
						if(Op2.getType() == Consts.TYPES.INTEGER
						|| Op2.getType() == Consts.TYPES.DOUBLE) {
							tetrada.add(new Row(Consts.OPERATORS.MOV, 
									Op1.getPosition(),
									Op2.getPosition(),
									symbols.insert(new Symbol())));
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
						//	if(Op2.)
							tetrada.add(new Row(Consts.OPERATORS.MOV, 
									Op1.getPosition(), 
									Op2.getPosition(), 
									symbols.insert(new Symbol())));
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
				
				Symbol op = new  Symbol();
				op = Expression(op);
				
				int lastIndex = this.tetrada.getLastElementIndex();
				Position result = this.tetrada.get(lastIndex).getResult();
				
				int ifEndIndex = tetrada.getLastElementIndex() + 1;
				this.tetrada.add(new Row(Consts.OPERATORS.JZ, -1, result, null)); 
				
				if(getNextSymbol() != Consts.CHARACTERS.RIGHT_BRACKET) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_RIGHT_BRACKET);
				}
				//====
				codeBlock();

				int elseStartIndex = tetrada.getLastElementIndex() + 1;
				this.tetrada.add(new Row(Consts.OPERATORS.JMP, -1, null, null));
				
				int endElseIndex = tetrada.getLastElementIndex();

				int line = this.tetrada.getLastElementIndex() + 1;
				this.tetrada.addJumpLine(ifEndIndex, line);
				branches.add(line);
				//====
				if(getNextSymbol() == Consts.CONDITIONAL_OPERATORS.ELSE) {
					line = this.tetrada.getLastElementIndex() + 1;
					this.tetrada.addJumpLine(elseStartIndex, line);
					branches.add(line);
					
					codeBlock();
					
					line = this.tetrada.getLastElementIndex() + 1;
					this.tetrada.addJumpLine(endElseIndex, line);
					branches.add(line);
				}
				else {
					//if you have getNextSymbol() + '==' 
					//always return the index if the check fails
					line = this.tetrada.getLastElementIndex() + 1;
					this.tetrada.addJumpLine(endElseIndex, line);
					branches.add(line);
					
					this.currentIndex--;
				}
				//====
				break;
			}			
			case Consts.LOOPS.WHILE : {
				if(getNextSymbol() != Consts.CHARACTERS.LEFT_BRACKET) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_LEFT_BRACKET);
				}

				int upIndex = this.tetrada.getLastElementIndex() + 1;
				
				Symbol op = new Symbol(); 
				op = Expression(op);
			
				int downIndex = tetrada.getLastElementIndex() + 1;
				
				int lastIndex = this.tetrada.getLastElementIndex();
				Position result = this.tetrada.get(lastIndex).getResult();
				
				this.tetrada.add(new Row(Consts.OPERATORS.JZ, -1, result, null)); 
				
				if(getNextSymbol() != Consts.CHARACTERS.RIGHT_BRACKET) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_RIGHT_BRACKET);
				}
				//====
				codeBlock();
				//====
				
				this.tetrada.add(new Row(Consts.OPERATORS.JMP, upIndex, null, null));
				
				int line = this.tetrada.getLastElementIndex() + 1;
				this.tetrada.addJumpLine(downIndex, line);
				branches.add(line);
				
				break;
			}			
			case Consts.LOOPS.FOR : {
				if(getNextSymbol() != Consts.CHARACTERS.LEFT_BRACKET) {
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_LEFT_BRACKET);
				}
				
				Operator();
				
				//===
				Symbol op = new Symbol(); 
				op = Expression(op);
				
				int downIndex = tetrada.getLastElementIndex() + 1;
				
				int lastIndex = this.tetrada.getLastElementIndex();
				Position result = this.tetrada.get(lastIndex).getResult();
				
				this.tetrada.add(new Row(Consts.OPERATORS.JZ, -1, result, null)); 
				
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
				
				this.tetrada.add(new Row(Consts.OPERATORS.JMP, downIndex - 1, null, null));
				
				int line = this.tetrada.getLastElementIndex() + 1;
				this.tetrada.addJumpLine(downIndex, line);
				branches.add(line);
				
				break;
			}
			case Consts.DEFINITION_TYPES.INTEGER : 
			case Consts.DEFINITION_TYPES.DOUBLE : 
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
			switch(Op1.getType()){
			case Consts.TYPES.INTEGER :{
				if(Op2.getType() == Consts.TYPES.INTEGER ||
					Op2.getType() == Consts.TYPES.DOUBLE){
					Symbol result = new Symbol("&" + nextVar++, 
													Consts.LEXICALS.IDENTIFIER, 
													Consts.TYPES.INTEGER);
					
					tetrada.add(new Row(opCode, 
							Op1.getPosition(), 
							Op2.getPosition(), 
							symbols.lookupInsert(result)));
					
					Op1 = result;
				} 
				else {
					throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_OPERATOR_TYPES);
				}

				 break;
			}
			
			case Consts.TYPES.DOUBLE :{
				if(Op2.getType() == Consts.TYPES.INTEGER ||
					Op2.getType() == Consts.TYPES.DOUBLE){
					Symbol result = new Symbol("&" + nextVar++, 
													Consts.LEXICALS.IDENTIFIER, 
													Consts.TYPES.DOUBLE);
					
					tetrada.add(new Row(opCode, 
							Op1.getPosition(), 
							Op2.getPosition(), 
							symbols.lookupInsert(result)));
					
					Op1 = result;
				} 
				else {
					throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_OPERATOR_TYPES);
				}

				 break;
			}
			case Consts.TYPES.STRING : { 
				if(Op2.getType() == Consts.TYPES.STRING){
						Symbol result = new Symbol("&" + nextVar++,
													Consts.LEXICALS.IDENTIFIER,
													Consts.TYPES.STRING);
						
						tetrada.add(new Row(opCode,
								Op1.getPosition(),
								Op2.getPosition(),
								symbols.lookupInsert(result)));
						
						Op1 = result;
					} 
					else {
						throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_OPERATOR_TYPES);
					}

					 break;
			}
			default : {
				throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_OPERATOR_TYPES);
			}
		}
	}
	
	if(currentSymbol.getCode() == Consts.OPERATORS.GREATER 
			|| currentSymbol.getCode() == Consts.OPERATORS.LESS) {
		int opCode = currentSymbol.getCode();
		
		Op2 = Expression(Op2);
		
		Symbol result = new Symbol("&" + nextVar++,
				Consts.LEXICALS.IDENTIFIER,
				Consts.TYPES.INTEGER);
		
		tetrada.add(new Row(opCode,
				Op1.getPosition(),
				Op2.getPosition(),
				symbols.lookupInsert(result)));
		
		Op1 = result;
	}
	else {	
		//if the read 'next symbol' doesnt pass the prev symbol position is returned
		//to avoid reading the next symbol instead"**
		currentIndex--;
	}
		
	return Op1;
}

	private Symbol Term(Symbol Op1) throws SyntaxException{ 
		Symbol Op2 = new Symbol();
		
		Op1 = Factor();
		
		while(getNextSymbol() == Consts.OPERATORS.MUL 
				|| currentSymbol.getCode() == Consts.OPERATORS.DIV) {			
			int opCode = currentSymbol.getCode();
			
			Op2 = Factor();
			
			switch(Op1.getType()){
				case Consts.TYPES.INTEGER :{
					if(Op2.getType() == Consts.TYPES.INTEGER ||
						Op2.getType() == Consts.TYPES.DOUBLE){
						Symbol result = new Symbol("&" + nextVar++,
								Consts.LEXICALS.IDENTIFIER,
								Consts.TYPES.INTEGER);
						
						tetrada.add(new Row(opCode,
								Op1.getPosition(),
								Op2.getPosition(),
								symbols.lookupInsert(result)));
						
						Op1 = result;
					} 
					else {
						throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_OPERATOR_TYPES);
					}

					 break;
				}
				
				case Consts.TYPES.DOUBLE :{
					if(Op2.getType() == Consts.TYPES.INTEGER ||
						Op2.getType() == Consts.TYPES.DOUBLE){
						Symbol result = new Symbol("&" + nextVar++,
								Consts.LEXICALS.IDENTIFIER,
								Consts.TYPES.DOUBLE);
						
						tetrada.add(new Row(opCode,
								Op1.getPosition(),
								Op2.getPosition(),
								symbols.lookupInsert(result)));
						
						Op1 = result;
					} 
					else {
						throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_OPERATOR_TYPES);
					}

					 break;
				}
				
				default : {
					throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_OPERATOR_TYPES);
				}
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
				
				return this.currentSymbol;
			}
			case Consts.LEXICALS.CONSTANT : {
				if(isInt(this.currentSymbol)) {
					this.currentSymbol.setType(Consts.TYPES.INTEGER);
				}
				else if(isDouble(this.currentSymbol)) {
					this.currentSymbol.setType(Consts.TYPES.DOUBLE);
				}
				
				break;
			}
			
			case Consts.CHARACTERS.APOSTROPHE : {
				if(getNextSymbol() != Consts.LEXICALS.CONSTANT 
						|| this.currentSymbol.getName().length() != 1) { 
					throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_CHAR);
				}
				
				this.currentSymbol.setType(Consts.TYPES.CHAR);
				Position pos = this.currentSymbol.getPosition();
				
				if(getNextSymbol() != Consts.CHARACTERS.APOSTROPHE) { 
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_APOSTROPHE);
				}
				
				break;
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
				
				break;
			}
			
			default : {
				throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_EXPRESSION);
			}
		}
		
		return this.currentSymbol;
	}

	private void dataDefinition() throws SyntaxException {
		switch(this.currentSymbol.getCode()) {
			case Consts.DEFINITION_TYPES.INTEGER: {
				if(getNextSymbol() != Consts.LEXICALS.IDENTIFIER){
					throw new SyntaxException(this.currentSymbol.getName(), Consts.ERRORS.SYNTAX.NOT_FOUND_IDENTIFIER);
				}
				
				if(this.currentSymbol.getType() != Consts.TYPES.UNKNOWN_TYPE) {
					throw new SyntaxException(this.currentSymbol.getName(), 
							Consts.ERRORS.SYNTAX.INVALID_VARIABLE);
				}
				
				currentSymbol.setType(Consts.TYPES.INTEGER);
				
				this.receiver = this.currentSymbol;
				
				if(getNextSymbol() == Consts.OPERATORS.MOV) {
					Symbol op = new Symbol();
					op = Expression(op);

					if(op.getType() != Consts.TYPES.INTEGER
						&& op.getType() != Consts.TYPES.DOUBLE) {
						throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_OPERATOR_TYPES);
					}
					
					tetrada.add(new Row(Consts.OPERATORS.MOV, this.receiver.getPosition(), null, op.getPosition()));
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
				
				this.receiver = this.currentSymbol;
				
				if(this.getNextSymbol() == Consts.OPERATORS.MOV){
					Symbol op = new Symbol();
					op = Expression(op);

					if(op.getType() != Consts.TYPES.INTEGER
						&& op.getType() != Consts.TYPES.DOUBLE) {
						throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_OPERATOR_TYPES);
					}
					tetrada.add(new Row(Consts.OPERATORS.MOV, op.getPosition(), null, this.receiver.getPosition()));
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
				
				this.receiver = this.currentSymbol;
				
				if(getNextSymbol() == Consts.OPERATORS.MOV) {
					if(getNextSymbol() == Consts.CHARACTERS.APOSTROPHE){
						if(getNextSymbol() != Consts.LEXICALS.CONSTANT) {
							throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_CHAR);
						}
						
						if(this.currentSymbol.getName().length() != 1) {
							throw new SyntaxException(Consts.ERRORS.SYNTAX.INVALID_CHAR);
						}
						
						this.currentSymbol.setType(Consts.TYPES.CHAR);

						tetrada.add(new Row(Consts.OPERATORS.MOV, this.currentSymbol.getPosition(), null, this.receiver.getPosition()));
						
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
				
				this.receiver = this.currentSymbol;
				
				if(this.getNextSymbol() == Consts.OPERATORS.MOV){
					if(this.getNextSymbol() == Consts.CHARACTERS.QUOTE){
						if(this.getNextSymbol() == Consts.LEXICALS.CONSTANT) {

							tetrada.add(new Row(Consts.OPERATORS.MOV, this.currentSymbol.getPosition(), null, this.receiver.getPosition()));
							
							}
						else {

							tetrada.add(new Row(Consts.OPERATORS.MOV, this.currentSymbol.getPosition(), null, this.receiver.getPosition()));
							
							
							//if you have getNextSymbol() + '==' 
							//always return the index if the check fails
							this.currentIndex--;
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
		//	Print("getNextSymbol() :: #" + currentIndex + " -> " + this.currentSymbol.getName() + " [type :: " + this.currentSymbol.getType() +"]");
			return this.currentSymbol.getCode();			
		}
		catch(IndexOutOfBoundsException e) {
			return Consts.UNKNOWN;
		}
	}
	
	private void Print(String x) {
		System.out.println(x);
	}
	
	private void setBranches() {
		for(Integer i : branches) {
			tetrada.get(i).setBranch();
		}
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
	
	public Tetrada getTetrada() {
		return tetrada;
	}
}