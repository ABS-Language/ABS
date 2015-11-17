import java.util.ArrayList;

public class Assemblifier {
	private Tetrada tetrada;
	
	private final ArrayList<AsemblyRow> asm = new ArrayList<>();
	
	public Assemblifier(Tetrada tetrada) {
		this.tetrada = tetrada;
	}
	
	public String toString() {
		for (AsemblyRow row : asm) {
			System.out.println(row);
		}
		
		return new String();
	}
	
	public void toAsm() {
		Row row;
		
		for(int i = 0; i < tetrada.size(); i++) {
			row = tetrada.get(i);
			
			switch(row.getOperator()) {
				case Consts.OPERATORS.ADD : {
					asm.add(new AsemblyRow("MOV", 
							"AX", 
							(Position)row.getOp1()));
					asm.add(new AsemblyRow("ADD", "AX", row.getOp2()));
					
					break;
				}
				case Consts.OPERATORS.DIFF : {
					asm.add(new AsemblyRow("CMP", (Position)row.getOp1(), row.getOp2()));		
					
					//TODO: JZ
					//asm.add(new AsemblyRow("JZ", "RED", op2))
									
					break;
				}
				case Consts.OPERATORS.DIV : {
					
					
					break;
				}
				case Consts.OPERATORS.EQU : {
					
					
					//TODO: JZ
					
					break;
				}
				case Consts.OPERATORS.GREATER : {
					
					
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
					
					
					//TODO: JG
					
					break;
				}
				case Consts.OPERATORS.MOD : {
					//
					
					break;
				}
				case Consts.OPERATORS.MOV : {
					asm.add(new AsemblyRow("MOV", row.getResult(), (Position)row.getOp1()));
					
					break;
				}
				case Consts.OPERATORS.MUL : {
					
					
					break;
				}
				case Consts.OPERATORS.SUB : {
					
					
					break;
				}
				
				case Consts.PROGRAM_END : {
					asm.add(new AsemblyRow("INT", "21h", null));
					
					break;
				}
			}
		}
	}
}

class AsemblyRow {
//	public AsemblyRow(String instruction, int op1, Position op2, Position result) {
//		
//	}
	private String instruction;
	private Object op1;
	private Position op2;
	
	public AsemblyRow(String instruction, String op1, Position op2) {
		this.instruction = instruction;
		this.op1 = (String)op1;
		this.op2 = op2;
	}
	
	public AsemblyRow(String instruction, Position op1, Position op2) {
		this.instruction = instruction;
		this.op1 = (Position)op1;
		this.op2 = op2;
	}
	
	public String toString() {
		return this.instruction + " | " 
					+ op1 + " | "
					+ op2;
		}
}
