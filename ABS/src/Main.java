public class Main {
	public static void main(String[] args) {
		
		final String CODE_FILE_PATH = "file.txt";
		
		new IDE();
		
		Scanner scanner = new Scanner(CODE_FILE_PATH/*args*/);
		
		Parser p = new Parser(scanner.getCodeOrder(), scanner.getSymbolTable());

		try {
			scanner.read();
			//scanner.getSymbolTable().printTable();
			//scanner.printCodeOrder();

			

			try {
				if(p.read()) {
					System.out.println("Syntax Analyze Successful.");
				}
				else {
					System.out.println("Syntax Analyze Failed");
				}
			} 
			catch (SyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		catch(NullPointerException e) {
			System.out.println("Syntax Analyze Failed");
			e.printStackTrace();
		}
		catch (LexicalException e1) {
				e1.printStackTrace();
		}
		
		Assemblifier asm = new Assemblifier(p.getTetrada(), scanner.getSymbolTable());
		asm.toAsm();
		asm.toString();
		
		Interpretefier i = new Interpretefier(p.getTetrada(), scanner.getSymbolTable());
		i.start();
	}
}
