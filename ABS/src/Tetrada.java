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
	
	public Row get(int index) {
		return tetrada.get(index);
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
	
	public int size() {
		return tetrada.size();
	}
}
