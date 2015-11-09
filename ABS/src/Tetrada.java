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
		for (Row row : tetrada) {
			result += row.getOperator() + " | " 
					+ Tetrada.hash.get(row.getOp1()) + " | "
					+ ((row.getOp2() == null) ? " null " : Tetrada.hash.get(row.getOp2())) + " | "
					+ ((row.getResult() == null) ? " null " : Tetrada.hash.get(row.getResult()))
					+ "\n";
		}
		return result;
	}
	
	public void add(Row row) {
		tetrada.add(row);
	}
	
	public static Hash getHash() {
		return Tetrada.hash;
	}
}

class Row {
	private int operator;
	private Position op1;
	private Position op2;
	private Position result;
	
	public Row(int operator, Position op1, Position op2, Position result){
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

	public Position getOp1() {
		return this.op1;
	}

	public void setOp1(Position op1) {
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
				+ Tetrada.getHash().get(op1)
				+ Tetrada.getHash().get(op2)
				+ Tetrada.getHash().get(result);
	}
}
