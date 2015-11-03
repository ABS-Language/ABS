import java.util.ArrayList;

public class Tetrada{
	private ArrayList<Row> tetrada;
	
	public Tetrada(){
		this.tetrada = new ArrayList<Row>();
	}
	
	public String toString(){
		String result = "";
		return "";
	}
	//fix commit
	//asdasd
}

class Row {
	private int line;
	private int operator;
	private Position op1;
	private Position op2;
	
	public Row(int line, int operator, Position op1, Position op2){
		this.line = line;
		this.operator = operator;
		this.op1 = op1;
		this.op2 = op2;
	}
	
	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
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
