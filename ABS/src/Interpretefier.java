
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
													
							result.setIntValue(
									op1.getIntValue() == 0 ? 
											Integer.parseInt(op1.getName()) 
												: op1.getIntValue() 
							+
									op2.getIntValue() == 0 ? 
											Integer.parseInt(op2.getName()) 
												: op1.getIntValue() 
							);
							
							break;
						}
						
						case Consts.TYPES.DOUBLE : {
							
							
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
