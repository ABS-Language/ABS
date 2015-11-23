import java.util.ArrayList;

public class Assemblifier {
	private Tetrada tetrada;
	private Hash hash; //test reasons
	
	private final ArrayList<AsemblyRow> asm = new ArrayList<>();
	
	public Assemblifier(Tetrada tetrada) {
		this.tetrada = tetrada;
	}
	
	public Assemblifier(Tetrada tetrada, Hash hash) {//test reasons
		this.tetrada = tetrada;
		this.hash = hash;
	}
	
	public String toString() {
		int line = 0;
		for (AsemblyRow row : asm) {
			System.out.println((line++ + " | ") + row);
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
					asm.add(new AsemblyRow("MOV", "EAX", (Position)row.getOp1()));
					asm.add(new AsemblyRow("CMP", "EAX", row.getOp2()));
					
					asm.add(new AsemblyRow("CMOVNE", "AL", "1")); //Move if not equal (ZF=0)
					asm.add(new AsemblyRow("CMOVE", "AL", "0")); //Move if equal (ZF=1)
					
					asm.add(new AsemblyRow("MOV", (Position)row.getResult(), "AL"));
									
					break;
				}
				
				case Consts.OPERATORS.DIV : {
					
					
					break;
				}
				
				case Consts.OPERATORS.EQU : {
					asm.add(new AsemblyRow("MOV", "EAX", (Position)row.getOp1()));
					asm.add(new AsemblyRow("CMP", "EAX", row.getOp2()));
					
					asm.add(new AsemblyRow("CMOVE", "AL", "1")); //Move if equal (ZF=1)
					asm.add(new AsemblyRow("CMOVNE", "AL", "0")); //Move if not equal (ZF=0)
					
					asm.add(new AsemblyRow("MOV", (Position)row.getResult(), "AL"));
					
					break;
				}
				
				case Consts.OPERATORS.GREATER : {
					asm.add(new AsemblyRow("MOV", "EAX", (Position)row.getOp1()));
					asm.add(new AsemblyRow("CMP", "EAX", row.getOp2()));
					
					asm.add(new AsemblyRow("CMOVG ", "AL", "1")); //Move if greater (ZF=0 and SF=OF)
					asm.add(new AsemblyRow("CMOVNG", "AL", "0")); //Move if not greater (ZF=1 or SF<>OF)
					asm.add(new AsemblyRow("MOV", (Position)row.getResult(), "AL"));
					
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
					asm.add(new AsemblyRow("MOV", "EAX", (Position)row.getOp1()));
					asm.add(new AsemblyRow("CMP", "EAX", row.getOp2()));
					
					asm.add(new AsemblyRow("CMOVL", "AL", "1")); //Move if less (SF<>OF)
					asm.add(new AsemblyRow("CMOVNL ", "AL", "0"));  //Move if not less (SF=OF)
					asm.add(new AsemblyRow("MOV", (Position)row.getResult(), "AL"));
					/*
					asm.add(new AsemblyRow("JL", "cmp1", null));
					
					asm.add(new AsemblyRow("MOV", "0", (Position)row.getResult())); //not less
					asm.add(new AsemblyRow("JMP", "cmp2", null));
					
					asm.add(new AsemblyRow("cmp1:"));
					asm.add(new AsemblyRow("MOV", "1", (Position)row.getResult())); //less
					
					asm.add(new AsemblyRow("cmp2:"));
					*/
					
					break;
				}
				
				case Consts.OPERATORS.MOD : {
					//
					
					break;
				}
				
				case Consts.OPERATORS.MOV : {
					asm.add(new AsemblyRow("MOV", "AL", row.getResult()));
					asm.add(new AsemblyRow("MOV", (Position)row.getOp1(), "AL"));
					
					break;
				}
				
				case Consts.OPERATORS.MUL : {
					
					
					break;
				}
				
				case Consts.OPERATORS.SUB : {
					
					
					break;
				}
				
				case Consts.PROGRAM_END : {
					asm.add(new AsemblyRow("INT", "21h", ""));
					
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
	private Object op2;
	
	public AsemblyRow(String instruction, String op1, Position op2) {
		this.instruction = instruction;
		this.op1 = (String)op1;
		this.op2 = op2;
	}
	
	public AsemblyRow(String instruction, String op1, String op2) {
		this.instruction = instruction;
		this.op1 = (String)op1;
		this.op2 = (String)op2;
	}
	
	public AsemblyRow(String instruction, Position op1, Position op2) {
		this.instruction = instruction;
		this.op1 = (Position)op1;
		this.op2 = op2;
	}
	
	public AsemblyRow(String instruction, Position op1, String op2) {
		this.instruction = instruction;
		this.op1 = (Position)op1;
		this.op2 = (String)op2;
	}
	
	public AsemblyRow(String instruction, int op1, Position op2) {
		this.instruction = instruction;
		this.op1 = (Integer)op1;
		this.op2 = op2;
	}
	
	
	
	public AsemblyRow(String instruction) {
		this.instruction = instruction;
	}
	
	public String toString() {
		String output = "";
		
		output += this.instruction + " | ";
		
		if(op1 == null) {
			output += "null | null";
			
			return output;
		}
		else {
			if(op1 instanceof Integer || op1 instanceof String) {
				output += op1;
				
				if(op1 instanceof Integer) {
					output += ".";
				}
			}
			else {
				output += Tetrada.getHash().get((Position)op1);
			}
			
			output += " | ";
			
			if(op2 == null) {
				output += "null";
			}
			else if(op2 instanceof String) {
				output += op2;
			}
			else {
				output += Tetrada.getHash().get((Position)op2);
			}
		}
		
		return output;
		/*
		return this.instruction + " | " 
		+ ((op1 instanceof Integer || op1 instanceof String) ? op1 + 
				(op1 == null) ? ((op1 instanceof Integer) ? "." : "") : Tetrada.getHash().get((Position)op1)) + " | "
		+ ((op2 == null) ? "null" : Tetrada.getHash().get(op2));*/
	}
}
