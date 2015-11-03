import java.util.ArrayList;

public class Tetrada{
	private ArrayList<Row> tetrada;
	
	public Tetrada(){
		this.tetrada = new ArrayList<Row>();
	}
	
	public String toString(){
		String result = "";
		for (Row row : tetrada) {
			result += row.getOperator() + ", " 
					+ row.getOp1() + ", "
					+ row.getOp2() 
					+ "/n";
		}
		return result;
	}
	
	public void add(Row row) {
		tetrada.add(row);
	}
}

class Row {
	private int operator;
	private Position op1;
	private Position op2;
	
	public Row(int operator, Position op1, Position op2){
		this.operator = operator;
		this.op1 = op1;
		this.op2 = op2;
	}
	
	public int getOperator() {
		return operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}

	public Position getOp1() {
		return op1;
	}

	public void setOp1(Position op1) {
		this.op1 = op1;
	}

	public Position getOp2() {
		return op2;
	}

	public void setOp2(Position op2) {
		this.op2 = op2;
	}
}
