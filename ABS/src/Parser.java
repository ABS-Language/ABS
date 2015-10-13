import java.util.ArrayList;

public class Parser {
	
	private ArrayList<Position> codeOrder;
	private Hash symbols;
	private int currentIndex = -1;
	private Symbol currentSymbol;
	private Position currentPosition;
	
	public Parser(ArrayList<Position> codeOrder, Hash symbols ){
		this.codeOrder = codeOrder;
		this.symbols = symbols;
	}
	
	public void codeBlock(){
		
	}
	
	public void operator(){
		
	}
	
	public void getNextSymbol(){
		this.currentPosition = codeOrder.get(currentIndex++);
		// TODO : create get method this.currentSymbol = symbols.get(this.currentPosition., chain)
	}
}
