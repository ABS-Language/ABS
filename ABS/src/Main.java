public class Main {
	public static void main(String[] args) {
		
		final String CODE_FILE_PATH = "file.txt";
		
		
		Scanner scanner = new Scanner(CODE_FILE_PATH/*args*/);
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
			} 
			catch(NullPointerException e) {
				System.out.println("Empty file provided.");
			}
		}
		
	}
}
