import java.util.ArrayList;


public class Main {
	public static void main(String[] args) {
	
		Scanner scanner = new Scanner();
		if(scanner.read()) {
			//scanner.getSymbolTable().printTable();
			scanner.printCodeOrder();
			
			Parser p = new Parser(scanner.getCodeOrder(), scanner.getSymbolTable());
			
			p.read(); //first line
		}
		
		
		
	}
}
