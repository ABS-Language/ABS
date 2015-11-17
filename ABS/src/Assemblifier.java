import java.util.ArrayList;

public class Assemblifier {
	private Tetrada tetrada;
	
	ArrayList<Roww> asm;
	
	public Assemblifier(Tetrada tetrada) {
		this.tetrada = tetrada;
	}
	
	public void toAsm() {
		Row row;
		
		for(int i = 0; i < tetrada.size(); i++) {
			row = tetrada.get(i);
			
			switch(row.getOperator()) {
				case Consts.OPERATORS.ADD : {
					asm.add(new Roww("ADD", 
							(Position)row.getOp1(), 
							row.getOp2(), 
							row.getResult()));
					
					break;
				}
				case Consts.OPERATORS.DIFF : {
					asm.add(new Roww("CMP",
							(Position)row.getOp1(), 
							row.getOp2(), 
							row.getResult()));		
					
					//TODO: JNZ
									
					break;
				}
				case Consts.OPERATORS.DIV : {
					asm.add(new Roww("DIV", 
							(Position)row.getOp1(), 
							row.getOp2(), 
							row.getResult()));
					
					break;
				}
				case Consts.OPERATORS.EQU : {
					asm.add(new Roww("CMP", 
							(Position)row.getOp1(), 
							row.getOp2(), 
							row.getResult()));
					
					//TODO: JZ
					
					break;
				}
				case Consts.OPERATORS.GREATER : {
					asm.add(new Roww("CMP", 
							(Position)row.getOp1(), 
							row.getOp2(), 
							row.getResult()));
					
					//TODO: JNG
					
					break;
				}
				case Consts.OPERATORS.JG : {
					//
					
					break;
				}
				case Consts.OPERATORS.JL : {
					//
					
					break;
				}
				case Consts.OPERATORS.JMP : {
					//
					
					break;
				}
				case Consts.OPERATORS.JZ : {
					//
					
					break;
				}
				case Consts.OPERATORS.LESS : {
					asm.add(new Roww("CMP", 
							(Position)row.getOp1(), 
							row.getOp2(), 
							row.getResult()));
					
					//TODO: JG
					
					break;
				}
				case Consts.OPERATORS.MOD : {
					//
					
					break;
				}
				case Consts.OPERATORS.MOV : {
					asm.add(new Roww("MOV", 
							(Position)row.getOp1(), 
							row.getOp2(), 
							row.getResult()));
					
					break;
				}
				case Consts.OPERATORS.MUL : {
					asm.add(new Roww("MUL", 
							(Position)row.getOp1(), 
							row.getOp2(), 
							row.getResult()));
					
					break;
				}
				case Consts.OPERATORS.SUB : {
					asm.add(new Roww("SUB", 
							(Position)row.getOp1(), 
							row.getOp2(), 
							row.getResult()));
					
					break;
				}
			}
		}
	}
}

class Roww {
	public Roww(String instruction, Position op1, Position op2, Position result) {
		
	}
	
	public Roww(String instruction, int op1, Position op2, Position result) {
		
	}
}
