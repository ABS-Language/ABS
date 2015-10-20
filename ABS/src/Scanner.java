import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Scanner {
	private final  String CODE_FILE_PATH = "file.txt";
	
	private  boolean isString = false;
	private  boolean isChar = false;
	private  boolean isSeparator = false;
	private  boolean isComment = false;
	
	private  Hash symbols = new Hash(10);
	private  ArrayList<Position> codeOrder = new ArrayList<>();
	
	private  Symbol symbol;
	private  Position pos;
	
	public  boolean read(){
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

		char ch, next_ch = ' ';
		int line = 0;
		try {
			while((textRead = buffer.readLine()) != null) {	
					line++; //pravilno
					
				if(!(textRead = textRead.trim()).isEmpty()) {
					for(int i = 0; i < textRead.length(); ++i) {
						ch = textRead.charAt(i);
						
						try {
							next_ch = textRead.charAt(i+1);
						}
						catch(StringIndexOutOfBoundsException e) {}
						
						if(isComment) {
							if(ch == '*' && next_ch == '/') {
								isComment = false;
								i++;
							}
							continue;
						}					
						
						if(ch == '"'){							
							if(isString == true){
								if(!word.isEmpty()) {
									codeOrder.add(symbols.lookupInsert(new Symbol(word, Consts.LEXICALS.CONSTANT)));
									word = "";
								}
								isString = false;
								
								codeOrder.add(symbols.lookupInsert(new Symbol(ch + "", Consts.CHARACTERS.QUOTE)));
								
								continue;
							}
							else {
								codeOrder.add(symbols.lookupInsert(new Symbol(ch + "", Consts.CHARACTERS.QUOTE)));
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
								if(!word.isEmpty()) {
									codeOrder.add(symbols.lookupInsert(new Symbol(word, Consts.LEXICALS.CONSTANT)));
									word = "";
								}
								isChar = false;
																
								codeOrder.add(symbols.lookupInsert(new Symbol(ch + "", Consts.CHARACTERS.APOSTROPHE)));
																
								continue;
							}
							else {
								codeOrder.add(symbols.lookupInsert(new Symbol(ch + "", Consts.CHARACTERS.APOSTROPHE)));
							}
							
							isChar = true;
							word = "";
							continue;
						}
						
						if(isChar == true){
							word += ch;
							continue;
						}
						
						if(ch == '/') {
							if(next_ch == '/') {
								break; //stop reading line - its a comment
							}
							else if(next_ch == '*') {
								i++;
								isComment = true;
								
								continue;
							}
						}

						if((ch < Consts.PERMITTED.ASCII_LOW || ch > Consts.PERMITTED.ASCII_CYRILLIC_HIGH)
						|| 
						(ch > Consts.PERMITTED.ASCII_HIGH && ch < Consts.PERMITTED.ASCII_CYRILLIC_LOW)
						|| ch == Consts.FORBIDDEN.ASCII_AT) {
							if(ch != Consts.SEPARATORS.ASCII_TAB) {
								System.out.println("Invalid character on " + line + " : " + (i+1));
								return false;
							}
						}
						//==
						isSeparator = false;
						
						for(int k = 0; k < GramaticConfigs.SEPARATORS.length; k++) {
							if(GramaticConfigs.SEPARATORS[k].getName().equals(ch + "")) {
								if(!word.isEmpty()) {
									codeOrder.add(symbols.lookupInsert(new Symbol(word, analyzeWord(word))));
									word = "";
								}
								isSeparator = true;
								
								if(ch != Consts.SEPARATORS.ASCII_SPACE && ch != Consts.SEPARATORS.ASCII_TAB) {
									codeOrder.add(symbols.lookupInsert(new Symbol(ch+"", GramaticConfigs.SEPARATORS[k].getCode())));	
								}
									break;
								}
						}
						
						if(!isSeparator) {
							word += ch;
						}
					}
				}
			}
			//add the last word if no separator is met
			if(!word.isEmpty()) {
				codeOrder.add(symbols.lookupInsert(new Symbol(word, analyzeWord(word))));	
				word = "";
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	private  void loadSpecialSymbols(){
		for (int i = 0; i < GramaticConfigs.SPECIAL_SYMBOLS.length; i++) {
			symbols.insert(GramaticConfigs.SPECIAL_SYMBOLS[i]);
		}
		
		for (int i = 0; i < GramaticConfigs.SEPARATORS.length; i++) {
			symbols.insert(GramaticConfigs.SEPARATORS[i]);
		}
	}
	
	public  ArrayList<Position> getCodeOrder(){
		return codeOrder;
	}
	
	public  Hash getSymbolTable() {
		return symbols;
	}
	
	private  int analyzeWord(String word) {
		for(int k = 0; k < GramaticConfigs.SPECIAL_SYMBOLS.length; k++) {
			if(GramaticConfigs.SPECIAL_SYMBOLS[k].getName().equals(word)) {
				return GramaticConfigs.SPECIAL_SYMBOLS[k].getCode();
			}
		}
		return (isInt(word) || isFloat(word)) ? Consts.LEXICALS.CONSTANT : Consts.LEXICALS.IDENTIFIER;
	}
		
	private  boolean isInt(String str) {
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
	
	private  boolean isFloat(String str) {
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
	
	public  void printCodeOrder() {
		for (int i = 0; i < this.getCodeOrder().size(); i++) {
			System.out.println(getCodeOrder().get(i) + " -> " + symbols.get(getCodeOrder().get(i).getCell(), getCodeOrder().get(i).getChain()) + " :: " + symbols.get(getCodeOrder().get(i).getCell(), getCodeOrder().get(i).getChain()).getCode());
		}
	}
}
