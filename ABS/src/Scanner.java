import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Scanner {
	private final static String CODE_FILE_PATH = "file.txt";
	private static boolean isString = false;
	private static boolean isChar = false;
	private static boolean isSeparator = false;
	
	private static Hash symbols = new Hash(10);
	private static ArrayList<Position> codeOrder = new ArrayList<>();
	
	private static Symbol symbol;
	private static Position pos;
	
	public static void read(){
		loadSpecialSymbols(); //tuka zarejda specialni simvoli
		FileReader read = null;
		
		try {
			read = new FileReader(CODE_FILE_PATH);
		} 
		catch (FileNotFoundException e) {
				System.out.println("File not found!");
				e.printStackTrace();
		}
		
		BufferedReader buffer = new BufferedReader(read);
		
		String textRead;
		String word = "";

		char ch;
		int line = 0;
		try {
			while((textRead = buffer.readLine()) != null) {	
					line++; //pravilno
					
				if(!(textRead = textRead.trim()).isEmpty()) {
					for(int i = 0; i < textRead.length(); ++i) {
						ch = textRead.charAt(i);
						
						if(ch == '"'){
							if(isString == true){
								symbol = new Symbol(word, Consts.LEXICALS.CONSTANT);
								pos = symbols.lookupInsert(symbol);
								codeOrder.add(pos);

								isString = false;
								word = "";
								continue;
							}
							isString = true;
							word = "";
							continue;
						}
						
						if(isString == true){
							word += ch;
							continue;
						}
												
						if(ch == '\''){
							if(isChar == true){
								symbol = new Symbol(word, Consts.LEXICALS.CONSTANT);
								pos = symbols.lookupInsert(symbol);
								codeOrder.add(pos);

								isChar = false;
								word = "";
								continue;
							}
							isChar = true;
							word = "";
							continue;
						}
						
						if(isChar == true){
							word += ch;
							continue;
						}
						
						isSeparator = false;
						
						if((ch < Consts.PERMITTED.ASCII_LOW || ch > Consts.PERMITTED.ASCII_CYRILLIC_HIGH)
						|| 
						(ch > Consts.PERMITTED.ASCII_HIGH && ch < Consts.PERMITTED.ASCII_CYRILLIC_LOW)
						|| ch == Consts.FORBIDDEN.ASCII_AT) {
							if(ch != Consts.SEPARATORS.ASCII_TAB) {
								System.out.println("Invalid character on " + line + " : " + (i+1));
								return;
							}
						}
						
						for(int k = 0; k < GramaticConfigs.SEPARATORS.length; k++) {
							if(GramaticConfigs.SEPARATORS[k].getName().equals(ch + "")) {
								if(!word.isEmpty()) {
										codeOrder.add(symbols.lookupInsert(new Symbol(word, analyzeWord(word))));
										word = "";
									}
									codeOrder.add(symbols.lookupInsert(new Symbol(ch + "", analyzeWord(word))));
									
									isSeparator = true;
									
									break;
								}
						}
						
						if(!isSeparator) {
							word += ch;
						}
						
							/*
						switch(ch) {
							case ';' : 
							case '+' : 
							case '-' : 
							case '*' : 
							case '/' : 
							case '%' : 
							case '=' : 
							case ' ' : 
							case ',' : 
							case Consts.SEPARATORS.TAB : {
								if(!word.isEmpty()) {
									symbol = new Symbol(word, Consts.LEXICALS.IDENTIFIER);
									pos = symbols.lookupInsert(symbol);
									codeOrder.add(pos);
								}
								word = "";
								break;
							}
							case '(' :
							case ')' :
							case '[' :
							case ']' : 
							case '{' : 
							case '}' : {
								if(!word.isEmpty()) {
									symbol = new Symbol(word, Consts.LEXICALS.SEPARATOR);
									pos = symbols.lookupInsert(symbol);
									codeOrder.add(pos);
								}
								word = "";
								break;
							}
							default : {
								word += ch;
								break;
							}
						}
						*/
					}
				}
			}
			symbols.printTable();
			
			for (int i = 0; i < Scanner.getCodeOrder().size(); i++) {
				System.out.println(Scanner.getCodeOrder().get(i) + " -> " + symbols.get(getCodeOrder().get(i).getCell(), getCodeOrder().get(i).getChain()) + " :: " + symbols.get(getCodeOrder().get(i).getCell(), getCodeOrder().get(i).getChain()).getCode());
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void loadSpecialSymbols(){
		for (int i = 0; i < GramaticConfigs.SPECIAL_SYMBOLS.length; i++) {
			symbols.insert(GramaticConfigs.SPECIAL_SYMBOLS[i]);
		}
		
		for (int i = 0; i < GramaticConfigs.SEPARATORS.length; i++) {
			symbols.insert(GramaticConfigs.SEPARATORS[i]);
		}
	}
	
	public static ArrayList<Position> getCodeOrder(){
		return codeOrder;
	}
	
	private static int analyzeWord(String word) {
		return (isInt(word) || isFloat(word)) ? Consts.LEXICALS.CONSTANT : Consts.LEXICALS.IDENTIFIER;
	}
		
	private static boolean isInt(String str) {
		try
	     {
	         Integer.parseInt(str);
	         return true;
	     }
	     catch(NumberFormatException e)
	     {
	         return false;
	     }
	}
	
	private static boolean isFloat(String str) {
		try
	     {
	         Float.parseFloat(str);
	         return true;
	     }
	     catch(NumberFormatException e)
	     {
	         return false;
	     }
	}	
}
