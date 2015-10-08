
public class Main {
	public static void main(String[] args) {
		/*Hash h = new Hash(50);
		

		System.out.println(h.lookupInsert(new Symbol("asda")));
		System.out.println(h.lookupInsert(new Symbol("asda")));
		System.out.println(h.lookupInsert(new Symbol("asda")));
		System.out.println(h.lookupInsert(new Symbol("asda")));

		System.out.println(h.lookupInsert(new Symbol("123132")));
		System.out.println(h.lookupInsert(new Symbol("asda")));
		System.out.println(h.lookupInsert(new Symbol("asda")));

		System.out.println(h.lookupInsert(new Symbol("123123123")));

		System.out.println(h.lookupInsert(new Symbol("123123123")));
			
		
		h.printTable();
		*/
	/*
		sHash h1 = new sHash(50);
		for(int i = 0; i < 100; ++i) {
			h1.lookupInsert(new Symbol(i + ""));
		}
		
		h1.printTable();
	*/	
		
		Scanner.read();	
		for (int i = 0; i < Scanner.getCodeOrder().size(); i++) {
			System.out.println(Scanner.getCodeOrder().get(i));
		}
	}
}
