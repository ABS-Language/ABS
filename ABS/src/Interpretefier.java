
public class Interpretefier {
	private Tetrada tetrada;
	private Hash hash;
	
	private int currentIndex = -1;
	
	public Interpretefier(Tetrada tetrada, Hash hash){
		this.tetrada = tetrada;
		this.hash = hash;
	}
	
	public void start(){
		Symbol result, op1, op2;
		
		while(this.tetrada.get(++this.currentIndex).getOperator() != Consts.PROGRAM_END){
			Row currentRow = this.tetrada.get(this.currentIndex);
			
			switch(currentRow.getOperator()){
				case Consts.OPERATORS.ADD : {
					result = hash.get(currentRow.getResult());
										
					switch(result.getType()) {
						case Consts.TYPES.INTEGER : {
							op1 = hash.get((Position)currentRow.getOp1());
							op2 = hash.get((Position)currentRow.getOp2());
							
							result.setIntValue(op1.getIntValue() + op2.getIntValue());
							
							break;
						}
						
						case Consts.TYPES.DOUBLE : {
							op1 = hash.get((Position)currentRow.getOp1());
							op2 = hash.get((Position)currentRow.getOp2());
							
							result.setDoubleValue(op1.getDoubleValue() + op2.getDoubleValue());

							break;
						}
						
						case Consts.TYPES.STRING : {
							op1 = hash.get((Position)currentRow.getOp1());
							op2 = hash.get((Position)currentRow.getOp2());
							
							result.setStringValue(op1.getStringValue() + op2.getStringValue());

							break;
						}
					}
					
					break;
				}
				
				case Consts.OPERATORS.DIFF : { //TODO : Same as EQU
					
					break;
				}
				
				case Consts.OPERATORS.DIV : {
					result = hash.get(currentRow.getResult());
					
					switch(result.getType()) {
						case Consts.TYPES.INTEGER : {
							op1 = hash.get((Position)currentRow.getOp1());
							op2 = hash.get((Position)currentRow.getOp2());
							
							result.setIntValue(op1.getIntValue() / op2.getIntValue());
							
							break;
						}
						
						case Consts.TYPES.DOUBLE : {
							op1 = hash.get((Position)currentRow.getOp1());
							op2 = hash.get((Position)currentRow.getOp2());
							
							result.setDoubleValue(op1.getDoubleValue() / op2.getDoubleValue());

							break;
						}
					}
					
					break;
				}
				
				case Consts.OPERATORS.EQU : {
result = hash.get(currentRow.getResult());
					
					op1 = hash.get((Position)currentRow.getOp1());
					op2 = hash.get((Position)currentRow.getOp2());
					
					int temp = 0;
					
					switch(op1.getType()) {
						case Consts.TYPES.INTEGER : {
							int type = op2.getType();
							
							if(type == Consts.TYPES.INTEGER) {
								if(op1.getIntValue() == op2.getIntValue()){
									temp = 1;
								}
							}
							else if(type == Consts.TYPES.DOUBLE) {
								if(op1.getDoubleValue() == op2.getDoubleValue()){
									temp = 1;
								}
							}
							else if(type == Consts.TYPES.CHAR) {
								if(op1.getIntValue() == op2.getCharValue()){
									temp = 1;
								}						
							}
							else if(type == Consts.TYPES.STRING) {
								String str = op2.getStringValue();
								
								int ascii = 0;
							
								for(int i = 0; i < str.length(); i++) {
									ascii += str.charAt(i);
								}
								
								if(op1.getIntValue() == ascii){
									temp = 1;
								}
							}
							
							break;
						} //case integer
						
						case Consts.TYPES.DOUBLE : {
							int type = op2.getType();
							
							if(type == Consts.TYPES.INTEGER
								||	type == Consts.TYPES.DOUBLE) {
								if(op1.getDoubleValue() == op2.getDoubleValue()){
									temp = 1;
								}
							}
							else if(type == Consts.TYPES.CHAR) {
								if(op1.getDoubleValue() == op2.getCharValue()){
									temp = 1;
								}						
							}
							else if(type == Consts.TYPES.STRING) {
								String str = op2.getStringValue();
								
								int ascii = 0;
							
								for(int i = 0; i < str.length(); i++) {
									ascii += str.charAt(i);
								}
								
								if(op1.getDoubleValue() == ascii){
									temp = 1;
								}
							}
							
							break;
						} //case double
						
						case Consts.TYPES.CHAR : {
							int type = op2.getType();
							
							if(type == Consts.TYPES.INTEGER) {
								if(op1.getCharValue() == op2.getIntValue()){
									temp = 1;
								}
							}
							else if(type == Consts.TYPES.DOUBLE) {
								if(op1.getCharValue() == op2.getDoubleValue()){
									temp = 1;
								}
							}
							else if(type == Consts.TYPES.CHAR) {
								if(op1.getCharValue() == op2.getCharValue()){
									temp = 1;
								}						
							}
							else if(type == Consts.TYPES.STRING) {
								String str = op2.getStringValue();
								
								int ascii = 0;
							
								for(int i = 0; i < str.length(); i++) {
									ascii += str.charAt(i);
								}
								
								if(op1.getCharValue() == ascii){
									temp = 1;
								}
							}
							
							break;
						} //case char
						
						case Consts.TYPES.STRING : {
							int type = op2.getType();
							
							if(type == Consts.TYPES.INTEGER) {
								if(op1.getIntValue() == op2.getIntValue()){
									temp = 1;
								}
							}
							else if(type == Consts.TYPES.DOUBLE) {
								if(op1.getDoubleValue() == op2.getDoubleValue()){
									temp = 1;
								}
							}
							else if(type == Consts.TYPES.CHAR) {
								if(op1.getIntValue() == op2.getCharValue()){
									temp = 1;
								}						
							}
							else if(type == Consts.TYPES.STRING) {
								String str1 = op1.getStringValue();
								
								int ascii1 = 0;
							
								for(int i = 0; i < str1.length(); i++) {
									ascii1 += str1.charAt(i);
								}
								
								String str2 = op2.getStringValue();
								
								int ascii2 = 0;
							
								for(int i = 0; i < str2.length(); i++) {
									ascii2 += str2.charAt(i);
								}
								
								if(ascii1 == ascii2){
									temp = 1;
								}
							}
							
							break;
						} //case string
					} //switch op1.getType()

					result.setIntValue(temp);
					System.out.println(temp);
					
					break;
				}
				
				case Consts.OPERATORS.GREATER : {
					result = hash.get(currentRow.getResult());
					
					op1 = hash.get((Position)currentRow.getOp1());
					op2 = hash.get((Position)currentRow.getOp2());
					
					int temp = 0;
					
					switch(op1.getType()) {
						case Consts.TYPES.INTEGER : {
							int type = op2.getType();
							
							if(type == Consts.TYPES.INTEGER) {
								if(op1.getIntValue() > op2.getIntValue()){
									temp = 1;
								}
							}
							else if(type == Consts.TYPES.DOUBLE) {
								if(op1.getDoubleValue() > op2.getDoubleValue()){
									temp = 1;
								}
							}
							else if(type == Consts.TYPES.CHAR) {
								if(op1.getIntValue() > op2.getCharValue()){
									temp = 1;
								}						
							}
							else if(type == Consts.TYPES.STRING) {
								String str = op2.getStringValue();
								
								int ascii = 0;
							
								for(int i = 0; i < str.length(); i++) {
									ascii += str.charAt(i);
								}
								
								if(op1.getIntValue() > ascii){
									temp = 1;
								}
							}
							
							break;
						} //case integer
						
						case Consts.TYPES.DOUBLE : {
							int type = op2.getType();
							
							if(type == Consts.TYPES.INTEGER
								||	type == Consts.TYPES.DOUBLE) {
								if(op1.getDoubleValue() > op2.getDoubleValue()){
									temp = 1;
								}
							}
							else if(type == Consts.TYPES.CHAR) {
								if(op1.getDoubleValue() > op2.getCharValue()){
									temp = 1;
								}						
							}
							else if(type == Consts.TYPES.STRING) {
								String str = op2.getStringValue();
								
								int ascii = 0;
							
								for(int i = 0; i < str.length(); i++) {
									ascii += str.charAt(i);
								}
								
								if(op1.getDoubleValue() > ascii){
									temp = 1;
								}
							}
							
							break;
						} //case double
						
						case Consts.TYPES.CHAR : {
							int type = op2.getType();
							
							if(type == Consts.TYPES.INTEGER) {
								if(op1.getCharValue() > op2.getIntValue()){
									temp = 1;
								}
							}
							else if(type == Consts.TYPES.DOUBLE) {
								if(op1.getCharValue() > op2.getDoubleValue()){
									temp = 1;
								}
							}
							else if(type == Consts.TYPES.CHAR) {
								if(op1.getCharValue() > op2.getCharValue()){
									temp = 1;
								}						
							}
							else if(type == Consts.TYPES.STRING) {
								String str = op2.getStringValue();
								
								int ascii = 0;
							
								for(int i = 0; i < str.length(); i++) {
									ascii += str.charAt(i);
								}
								
								if(op1.getCharValue() > ascii){
									temp = 1;
								}
							}
							
							break;
						} //case char
						
						case Consts.TYPES.STRING : {
							int type = op2.getType();
							
							if(type == Consts.TYPES.INTEGER) {
								if(op1.getIntValue() > op2.getIntValue()){
									temp = 1;
								}
							}
							else if(type == Consts.TYPES.DOUBLE) {
								if(op1.getDoubleValue() > op2.getDoubleValue()){
									temp = 1;
								}
							}
							else if(type == Consts.TYPES.CHAR) {
								if(op1.getIntValue() > op2.getCharValue()){
									temp = 1;
								}						
							}
							else if(type == Consts.TYPES.STRING) {
								String str1 = op1.getStringValue();
								
								int ascii1 = 0;
							
								for(int i = 0; i < str1.length(); i++) {
									ascii1 += str1.charAt(i);
								}
								
								String str2 = op2.getStringValue();
								
								int ascii2 = 0;
							
								for(int i = 0; i < str2.length(); i++) {
									ascii2 += str2.charAt(i);
								}
								
								if(ascii1 > ascii2){
									temp = 1;
								}
							}
							
							break;
						} //case string
					} //switch op1.getType()

					result.setIntValue(temp);
					System.out.println(temp);
					
					break;
				}
				
				case Consts.OPERATORS.JG : {
					break;
				}
				
				case Consts.OPERATORS.JL : {
					break;
				}
				
				case Consts.OPERATORS.JMP : {
					break;
				}
				
				case Consts.OPERATORS.JZ : {
					break;
				}
				
				case Consts.OPERATORS.LESS : {
					result = hash.get(currentRow.getResult());
					
					op1 = hash.get((Position)currentRow.getOp1());
					op2 = hash.get((Position)currentRow.getOp2());
					
					int temp = 0;
					
					switch(op1.getType()) {
						case Consts.TYPES.INTEGER : {
							int type = op2.getType();
							
							if(type == Consts.TYPES.INTEGER) {
								if(op1.getIntValue() < op2.getIntValue()){
									temp = 1;
								}
							}
							else if(type == Consts.TYPES.DOUBLE) {
								if(op1.getDoubleValue() < op2.getDoubleValue()){
									temp = 1;
								}
							}
							else if(type == Consts.TYPES.CHAR) {
								if(op1.getIntValue() < op2.getCharValue()){
									temp = 1;
								}						
							}
							else if(type == Consts.TYPES.STRING) {
								String str = op2.getStringValue();
								
								int ascii = 0;
							
								for(int i = 0; i < str.length(); i++) {
									ascii += str.charAt(i);
								}
								
								if(op1.getIntValue() < ascii){
									temp = 1;
								}
							}
							
							break;
						} //case integer
						
						case Consts.TYPES.DOUBLE : {
							int type = op2.getType();
							
							if(type == Consts.TYPES.INTEGER
								||	type == Consts.TYPES.DOUBLE) {
								if(op1.getDoubleValue() < op2.getDoubleValue()){
									temp = 1;
								}
							}
							else if(type == Consts.TYPES.CHAR) {
								if(op1.getDoubleValue() < op2.getCharValue()){
									temp = 1;
								}						
							}
							else if(type == Consts.TYPES.STRING) {
								String str = op2.getStringValue();
								
								int ascii = 0;
							
								for(int i = 0; i < str.length(); i++) {
									ascii += str.charAt(i);
								}
								
								if(op1.getDoubleValue() < ascii){
									temp = 1;
								}
							}
							
							break;
						} //case double
						
						case Consts.TYPES.CHAR : {
							int type = op2.getType();
							
							if(type == Consts.TYPES.INTEGER) {
								if(op1.getCharValue() < op2.getIntValue()){
									temp = 1;
								}
							}
							else if(type == Consts.TYPES.DOUBLE) {
								if(op1.getCharValue() < op2.getDoubleValue()){
									temp = 1;
								}
							}
							else if(type == Consts.TYPES.CHAR) {
								if(op1.getCharValue() < op2.getCharValue()){
									temp = 1;
								}						
							}
							else if(type == Consts.TYPES.STRING) {
								String str = op2.getStringValue();
								
								int ascii = 0;
							
								for(int i = 0; i < str.length(); i++) {
									ascii += str.charAt(i);
								}
								
								if(op1.getCharValue() < ascii){
									temp = 1;
								}
							}
							
							break;
						} //case char
						
						case Consts.TYPES.STRING : {
							int type = op2.getType();
							
							if(type == Consts.TYPES.INTEGER) {
								if(op1.getIntValue() < op2.getIntValue()){
									temp = 1;
								}
							}
							else if(type == Consts.TYPES.DOUBLE) {
								if(op1.getDoubleValue() < op2.getDoubleValue()){
									temp = 1;
								}
							}
							else if(type == Consts.TYPES.CHAR) {
								if(op1.getIntValue() < op2.getCharValue()){
									temp = 1;
								}						
							}
							else if(type == Consts.TYPES.STRING) {
								String str1 = op1.getStringValue();
								
								int ascii1 = 0;
							
								for(int i = 0; i < str1.length(); i++) {
									ascii1 += str1.charAt(i);
								}
								
								String str2 = op2.getStringValue();
								
								int ascii2 = 0;
							
								for(int i = 0; i < str2.length(); i++) {
									ascii2 += str2.charAt(i);
								}
								
								if(ascii1 < ascii2){
									temp = 1;
								}
							}
							
							break;
						} //case string
					} //switch op1.getType()

					result.setIntValue(temp);
					System.out.println(temp);
					
					break;
				}
				
				case Consts.OPERATORS.MOD : {
					break;
				}
				
				case Consts.OPERATORS.MOV : {
					op1 = hash.get((Position)currentRow.getOp1());
					result = hash.get((Position)currentRow.getResult());
					
					switch(op1.getType()){
						case Consts.TYPES.INTEGER :{
							op1.setIntValue(result.getIntValue());
							System.out.println(op1.getIntValue());
							break;
						}
						
						case Consts.TYPES.DOUBLE : {
							op1.setDoubleValue(result.getDoubleValue());
							System.out.println(op1.getDoubleValue());
							break;
						}
						
						case Consts.TYPES.STRING : {
							op1.setStringValue(result.getStringValue());
							System.out.println(op1.getStringValue());
							break;
						}
						
						case Consts.TYPES.CHAR : {
							op1.setCharValue(result.getCharValue());
							System.out.println(op1.getCharValue());
							break;
						}
					}
					
					break;
				}
				
				case Consts.OPERATORS.MUL : {
					result = hash.get(currentRow.getResult());
					
					switch(result.getType()) {
						case Consts.TYPES.INTEGER : {
							op1 = hash.get((Position)currentRow.getOp1());
							op2 = hash.get((Position)currentRow.getOp2());
							
							result.setIntValue(op1.getIntValue() * op2.getIntValue());
							
							break;
						}
						
						case Consts.TYPES.DOUBLE : {
							op1 = hash.get((Position)currentRow.getOp1());
							op2 = hash.get((Position)currentRow.getOp2());
							
							result.setDoubleValue(op1.getDoubleValue() * op2.getDoubleValue());

							break;
						}
					}
					
					break;
				}
				
				case Consts.OPERATORS.SUB : {
					result = hash.get(currentRow.getResult());
					
					switch(result.getType()) {
						case Consts.TYPES.INTEGER : {
							op1 = hash.get((Position)currentRow.getOp1());
							op2 = hash.get((Position)currentRow.getOp2());
							
							result.setIntValue(op1.getIntValue() - op2.getIntValue());
							
							break;
						}
						
						case Consts.TYPES.DOUBLE : {
							op1 = hash.get((Position)currentRow.getOp1());
							op2 = hash.get((Position)currentRow.getOp2());
							
							result.setDoubleValue(op1.getDoubleValue() - op2.getDoubleValue());

							break;
						}
					}
					
					break;
				}
				default : {
					System.out.println("UNSUPPORTED OPERATOR");
					break;
				}
			} //switch
		} //while
	} //start
} //class
