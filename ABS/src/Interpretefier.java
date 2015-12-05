
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
							
							int tempResult = (int)(op1.getIntValue() + op2.getDoubleValue());
							System.out.println(tempResult);
							result.setIntValue(tempResult);

							System.out.println(result.getIntValue());
							break;
						}
						
						case Consts.TYPES.DOUBLE : {
							op1 = hash.get((Position)currentRow.getOp1());
							op2 = hash.get((Position)currentRow.getOp2());
							
							double tempResult = (double)(op1.getDoubleValue() + op2.getDoubleValue());
							System.out.println(tempResult);
							
							result.setDoubleValue((double)(op1.getIntValue() + op2.getIntValue()));
							System.out.println(result.getDoubleValue());
							break;
						}
					}
				}
				
				case Consts.OPERATORS.DIFF : {
					break;
				}
				
				case Consts.OPERATORS.DIV : {
					break;
				}
				
				case Consts.OPERATORS.EQU : {
					break;
				}
				
				case Consts.OPERATORS.GREATER : {
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
							op1.setIntValue((int)result.getIntValue());
							break;
						}
						
						case Consts.TYPES.DOUBLE : {
							op1.setDoubleValue((double)result.getDoubleValue());
							break;
						}
						
						case Consts.TYPES.STRING : {
							op1.setStringValue(result.getStringValue());
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
					break;
				}
				
				case Consts.OPERATORS.SUB : {
					break;
				}
			}
			
		}
		System.out.println("End");
	}
}
