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
		
		return "";
	}
	
	public void toAsm() {
		Row row;
		AsemblyRow aRow;
		
		for(int i = 0; i < tetrada.size(); i++) {
			row = tetrada.get(i);
			
			switch(row.getOperator()) {
				case Consts.OPERATORS.ADD : {
					aRow = new AsemblyRow("MOV", "EAX", (Position)row.getOp1());
					
					if(row.getBranch()) {
						aRow.setLabel("LABEL" + i + ":");
					}
					
					asm.add(aRow);
					asm.add(new AsemblyRow("ADD", "EAX", row.getOp2()));
					asm.add(new AsemblyRow("MOV", (Position)row.getResult(), "EAX"));
					
					break;
				}
				
				case Consts.OPERATORS.DIFF : {
					aRow = new AsemblyRow("MOV", "EAX", (Position)row.getOp1());
					
					if(row.getBranch()) {
						aRow.setLabel("LABEL" + i + ":");
					}
					
					asm.add(aRow);
					asm.add(new AsemblyRow("CMP", "EAX", row.getOp2()));
					
					asm.add(new AsemblyRow("CMOVNE", "EAX", "1")); //Move if not equal (ZF=0)
					asm.add(new AsemblyRow("CMOVE", "EAX", "0")); //Move if equal (ZF=1)
					
					asm.add(new AsemblyRow("MOV", (Position)row.getResult(), "EAX"));
									
					break;
				}
				
				case Consts.OPERATORS.DIV : {
					aRow = new AsemblyRow("MOV", "EAX", (Position)row.getOp1());
					
					if(row.getBranch()) {
						aRow.setLabel("LABEL" + i + ":");
					}
					
					asm.add(aRow);
					
					asm.add(new AsemblyRow("DIV", "EAX", row.getOp2()));
					asm.add(new AsemblyRow("MOV", (Position)row.getResult(), "EAX"));
					
					break;
				}
				
				case Consts.OPERATORS.EQU : {
					aRow = new AsemblyRow("MOV", "EAX", (Position)row.getOp1());
					
					if(row.getBranch()) {
						aRow.setLabel("LABEL" + i + ":");
					}
					
					asm.add(aRow);
					
					asm.add(new AsemblyRow("CMP", "EAX", row.getOp2()));
					
					asm.add(new AsemblyRow("CMOVE", "EAX", "1")); //Move if equal (ZF=1)
					asm.add(new AsemblyRow("CMOVNE", "EAX", "0")); //Move if not equal (ZF=0)
					
					asm.add(new AsemblyRow("MOV", (Position)row.getResult(), "EAX"));
					
					break;
				}
				
				case Consts.OPERATORS.GREATER : {
					aRow = new AsemblyRow("MOV", "EAX", (Position)row.getOp1());

					if(row.getBranch()) {
							aRow.setLabel("LABEL" + i + ":");
					}

					asm.add(aRow);
					
					asm.add(new AsemblyRow("CMP", "EAX", row.getOp2()));
					
					asm.add(new AsemblyRow("CMOVG ", "EAX", "1")); //Move if greater (ZF=0 and SF=OF)
					asm.add(new AsemblyRow("CMOVNG", "EAX", "0")); //Move if not greater (ZF=1 or SF<>OF)
					asm.add(new AsemblyRow("MOV", (Position)row.getResult(), "EAX"));
					
					break;
				}
				
				case Consts.OPERATORS.JG : {
					asm.add(new AsemblyRow("JG", "LABEL" + row.getOp1AsInt() + ":", ""));
					
					break;
				}
				
				case Consts.OPERATORS.JL : {
					asm.add(new AsemblyRow("JL", "LABEL" + row.getOp1AsInt() + ":", ""));
					
					break;
				}
				
				case Consts.OPERATORS.JMP : {
					asm.add(new AsemblyRow("JMP", "LABEL" + row.getOp1AsInt() + ":", ""));
					
					break;
				}
				
				case Consts.OPERATORS.JZ : {
					asm.add(new AsemblyRow("JZ", "LABEL" + row.getOp1AsInt() + ":", ""));
					
					
					break;
				}
				
				case Consts.OPERATORS.LESS : {
					aRow = new AsemblyRow("MOV", "EAX", (Position)row.getOp1());
							
					if(row.getBranch()) {
						aRow.setLabel("LABEL" + i + ":");
					}

					asm.add(aRow);
				
					asm.add(new AsemblyRow("CMP", "EAX", row.getOp2()));
					
					asm.add(new AsemblyRow("CMOVL", "EAX", "1")); //Move if less (SF<>OF)
					asm.add(new AsemblyRow("CMOVNL ", "EAX", "0"));  //Move if not less (SF=OF)
					asm.add(new AsemblyRow("MOV", (Position)row.getResult(), "EAX"));
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
					aRow = new AsemblyRow("MOV", "EAX", (Position)row.getOp1());
							
					if(row.getBranch()) {
						aRow.setLabel("LABEL" + i + ":");
					}

					asm.add(aRow);
					
					asm.add(new AsemblyRow("MOD", "EAX", row.getOp2()));
					asm.add(new AsemblyRow("MOV", (Position)row.getResult(), "EAX"));
					
					break;
				}
				
				case Consts.OPERATORS.MOV : {
					// TODO check if MOV is PametPamet
					
					aRow = new AsemblyRow("MOV", (Position)row.getOp1(), (Position)row.getResult());
							
					if(row.getBranch()) {
						aRow.setLabel("LABEL" + i + ":");
					}
					
					asm.add(aRow);
					
					break;
				}
				
				case Consts.OPERATORS.MUL : {
					aRow = new AsemblyRow("MOV", "EAX", (Position)row.getOp1());
							
					if(row.getBranch()) {
						aRow.setLabel("LABEL" + i + ":");
					}
					
					asm.add(aRow);
					
					asm.add(new AsemblyRow("MUL", "EAX", row.getOp2()));
					asm.add(new AsemblyRow("MOV", (Position)row.getResult(), "EAX"));
					
					break;
				}
				
				case Consts.OPERATORS.SUB : {
					aRow = new AsemblyRow("MOV", "EAX", (Position)row.getOp1());
							
					if(row.getBranch()) {
						aRow.setLabel("LABEL" + i + ":");
					}
					
					asm.add(aRow);
					
					asm.add(new AsemblyRow("SUB", "EAX", row.getOp2()));
					asm.add(new AsemblyRow("MOV", (Position)row.getResult(), "EAX"));
					
					break;
				}
				
				case Consts.PROGRAM_END : {
					aRow = new AsemblyRow("INT", "21h", "");
							
					if(row.getBranch()) {
						aRow.setLabel("LABEL" + i + ":");
					}
					
					asm.add(aRow);
					
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
	private String label;
	private String instruction;
	private Object op1;
	private Object op2;
	
	public AsemblyRow(String instruction, String op1, Position op2) {
		this.label = "";
		this.instruction = instruction;
		this.op1 = (String)op1;
		this.op2 = op2;
	}
	
	public AsemblyRow(String instruction, String op1, String op2) {
		this.label = "";
		this.instruction = instruction;
		this.op1 = (String)op1;
		this.op2 = (String)op2;
	}
	
	public AsemblyRow(String instruction, Position op1, Position op2) {
		this.label = "";
		this.instruction = instruction;
		this.op1 = (Position)op1;
		this.op2 = op2;
	}
	
	public AsemblyRow(String instruction, Position op1, String op2) {
		this.label = "";
		this.instruction = instruction;
		this.op1 = (Position)op1;
		this.op2 = (String)op2;
	}
	
	public AsemblyRow(String instruction, int op1, Position op2) {
		this.label = "";
		this.instruction = instruction;
		this.op1 = (Integer)op1;
		this.op2 = op2;
	}
	
	public void setLabel(String name) {
		this.label = name;
	}
	
	public String toString() {
		String output = "";
		
		output += this.label + " | ";
		
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
