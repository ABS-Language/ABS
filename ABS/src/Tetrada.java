import java.util.ArrayList;

public class Tetrada{
	private ArrayList<Row> tetrada;
	private static Hash hash; //TODO: for testing purpose
	
	public Tetrada(){
		this.tetrada = new ArrayList<Row>();
	}
	
	public Tetrada(Hash hash) { //TODO: for testing purpose
		this.tetrada = new ArrayList<Row>();
		Tetrada.hash = hash;
	}
	
	@Override
	public String toString(){
		String result = new String();
		int counter = 0;
				
		for (Row row : tetrada) {
			result += counter++ + ". ";
			
			switch(row.getOperator()) {
				case Consts.OPERATORS.ADD : result += "ADD | "; break;
				case Consts.OPERATORS.DIFF : result += "DIFF | "; break;
				case Consts.OPERATORS.DIV : result += "DIV | "; break;
				case Consts.OPERATORS.EQU : result += "EQU | "; break;
				case Consts.OPERATORS.GREATER : result += "GREATER | "; break;
				case Consts.OPERATORS.JG : result += "JG | "; break;
				case Consts.OPERATORS.JL : result += "JL | "; break;
				case Consts.OPERATORS.JMP : result += "JMP | "; break;
				case Consts.OPERATORS.JZ : result += "JZ | "; break;
				case Consts.OPERATORS.LESS : result += "LESS | "; break;
				case Consts.OPERATORS.MOD : result += "MOD | "; break;
				case Consts.OPERATORS.MOV : result += "MOV | "; break;
				case Consts.OPERATORS.MUL : result += "MUL | "; break;
				case Consts.OPERATORS.SUB : result += "SUB | "; break;
				case Consts.PROGRAM_END : result += "END | "; break;
			}
			
			result += ((row.getOp1() == null) ? " null " : ((row.getOp1() instanceof Integer) ? (int)row.getOp1() + "." : Tetrada.getHash().get((Position)row.getOp1()))) + " | "
					+ ((row.getOp2() == null) ? " null " : Tetrada.hash.get(row.getOp2())) + " | "
					+ ((row.getResult() == null) ? " null " : "\'" + Tetrada.hash.get(row.getResult()) + "\'")
					+ "\n";
		}
		return result;
	}
	
	public void add(Row row) {
		tetrada.add(row);
//		System.out.println(row.getOperator() + " | " 
//				+ Tetrada.hash.get(row.getOp1()) + " | "
//				+ ((row.getOp2() == null) ? " null " : Tetrada.hash.get(row.getOp2())) + " | "
//				+ ((row.getResult() == null) ? " null " : "\'" + Tetrada.hash.get(row.getResult()) + "\'")
//				);
	}
	
	public static Hash getHash() {
		return Tetrada.hash;
	}
	
	public int getLastElementIndex(){
		return this.tetrada.size() - 1;
	}
	
	public void addJumpLine(int index, int lineToContinue){
		this.tetrada.get(index).setOp1(lineToContinue);
	}
}

class Row {
	private int operator;
	private Object op1;
	private Position op2;
	private Position result;
	
	public Row(int operator, Position op1, Position op2, Position result){
		this.operator = operator;
		this.op1 = op1;
		this.op2 = op2;
		this.result = result;
	}
	
	public Row(int operator, int op1, Position op2, Position result){
		this.operator = operator;
		this.op1 = op1;
		this.op2 = op2;
		this.result = result;
	}
	
	public int getOperator() {
		return this.operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}

	public int getOp1AsInt() {
		return (int)(this.op1);
	}
	
	public Object getOp1() {
		return this.op1;
	}

	public void setOp1(Position op1) {
		this.op1 = op1;
	}
	
	public void setOp1(int op1){
		this.op1 = op1;
	}

	public Position getOp2() {
		return this.op2;
	}

	public void setOp2(Position op2) {
		this.op2 = op2;
	}
	
	public Position getResult() {
		return this.result;
	}
	
	@Override
	public String toString(){
		return this.operator + " | " 
				+ ((op1 instanceof Integer) ? op1 + "." : Tetrada.getHash().get((Position)op1))
				+ Tetrada.getHash().get(op2)
				+ Tetrada.getHash().get(result);
	}
}
