
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
						
						case Consts.TYPES.STRING : {//TODO : parser does not support add on string
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
				
				case Consts.OPERATORS.EQU : { //TODO parser doesnt support ravno!
					result = hash.get(currentRow.getResult());
					
					switch(result.getType()) {
						case Consts.TYPES.INTEGER : {
							op1 = hash.get((Position)currentRow.getOp1());
							op2 = hash.get((Position)currentRow.getOp2());
							
							int temp = 0;
							
							if(op1.getIntValue() == op2.getIntValue()){
								temp = 1;
							}
							
							result.setIntValue(temp);
							System.out.println(temp);
							
							break;
						}
						
						case Consts.TYPES.DOUBLE : {
							op1 = hash.get((Position)currentRow.getOp1());
							op2 = hash.get((Position)currentRow.getOp2());
							
							int temp = 0;
							
							if(op1.getDoubleValue() == op2.getDoubleValue()){
								temp = 1;
							}
							
							result.setIntValue(temp);
							System.out.println(temp);
							
							break;
						}
						
						case Consts.TYPES.STRING : {
							op1 = hash.get((Position)currentRow.getOp1());
							op2 = hash.get((Position)currentRow.getOp2());
							
							int temp = 0;
							
							if(op1.getStringValue().equals(op2.getStringValue())){
								temp = 1;
							}
							
							result.setIntValue(temp);
							System.out.println(temp);
							
							break;
						}
						
						case Consts.TYPES.CHAR : {
							op1 = hash.get((Position)currentRow.getOp1());
							op2 = hash.get((Position)currentRow.getOp2());
							
							int temp = 0;
							
							if(op1.getCharValue() == op2.getCharValue()){
								temp = 1;
							}
							
							result.setIntValue(temp);
							System.out.println(temp);
							
							break;
						}
					}
				}
				
				case Consts.OPERATORS.GREATER : { //TODO : think of string
					result = hash.get(currentRow.getResult());
					
					switch(result.getType()) {
						case Consts.TYPES.INTEGER : {
							op1 = hash.get((Position)currentRow.getOp1());
							op2 = hash.get((Position)currentRow.getOp2());
							
							int temp = 0;
							
							if(op1.getIntValue() > op2.getIntValue()){
								temp = 1;
							}
							
							result.setIntValue(temp);
							System.out.println(temp);
							
							break;
						}
						
						case Consts.TYPES.DOUBLE : {
							op1 = hash.get((Position)currentRow.getOp1());
							op2 = hash.get((Position)currentRow.getOp2());
							
							int temp = 0;
							
							if(op1.getDoubleValue() > op2.getDoubleValue()){
								temp = 1;
							}
							
							result.setIntValue(temp);
							System.out.println(temp);
							
							break;
						}
					}
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
				
				case Consts.OPERATORS.LESS : {//TODO : think of string
					result = hash.get(currentRow.getResult());
					
					switch(result.getType()) {
						case Consts.TYPES.INTEGER : {
							op1 = hash.get((Position)currentRow.getOp1());
							op2 = hash.get((Position)currentRow.getOp2());
							
							int temp = 0;
							
							if(op1.getIntValue() < op2.getIntValue()){
								temp = 1;
							}
							
							result.setIntValue(temp);
							System.out.println(temp);
							
							break;
						}
						
						case Consts.TYPES.DOUBLE : {
							op1 = hash.get((Position)currentRow.getOp1());
							op2 = hash.get((Position)currentRow.getOp2());
							
							int temp = 0;
							
							if(op1.getDoubleValue() < op2.getDoubleValue()){
								temp = 1;
							}
							
							result.setIntValue(temp);
							System.out.println(temp);
							
							break;
						}
					}
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
			}
			
		}
		System.out.println("End");
	}
}
