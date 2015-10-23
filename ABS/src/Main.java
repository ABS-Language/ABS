public class Main {
	public static void main(String[] args) {
	
		Scanner scanner = new Scanner();
		if(scanner.read()) {
			//scanner.getSymbolTable().printTable();
			//scanner.printCodeOrder();
			
			Parser p = new Parser(scanner.getCodeOrder(), scanner.getSymbolTable());
			
			try {
				if(p.read()) {
					System.out.println("Syntax Analyze Successful.");
				}
				else {
					System.out.println("Syntax Analyze Failed");
				}
			} catch (SyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //first line
		}
		
	}
}
