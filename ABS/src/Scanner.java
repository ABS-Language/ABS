import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Scanner {
	private final static String CODE_FILE_PATH = "file.txt";
	private static boolean isString = false;
	private static boolean isChar = false;
	private static sHash symbols = new 
	
	public static void read(){
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
				line = line++;
				
				if(!(textRead = textRead.trim()).isEmpty()) {
			//		System.out.println(textRead);
					
					for(int i = 0; i < textRead.length(); ++i) {
						ch = textRead.charAt(i);
						
						if(ch == Consts.FORBIDDEN.SOH
							|| ch == Consts.FORBIDDEN.STX
							|| ch == Consts.FORBIDDEN.ETX
							|| ch == Consts.FORBIDDEN.ENQ
							|| ch == Consts.FORBIDDEN.ACK
							|| ch == Consts.FORBIDDEN.BEL
							|| ch == Consts.FORBIDDEN.BS
							|| ch == Consts.FORBIDDEN.LF
							|| ch == Consts.FORBIDDEN.CR) {
								System.out.println("Used forbidden symbol at line: " + line);
								return;
							}
						
						if(ch == '"'){
							if(isString == true){
								System.out.println(word + " " + Consts.CONSTANTS.STRING);
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
								System.out.println(word + " " + Consts.CONSTANTS.CHAR);
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
						
						switch(ch) {
							case ';' : {
								if(!word.isEmpty()){
									System.out.println(word + " " + analyzeWord(word));
								}
								System.out.println(ch + " " +  Consts.EOS.SEMICOLON);
								word = ""; 
								break; 
							}
							case '+' : { 
								if(!word.isEmpty()){
									System.out.println(word + " " + analyzeWord(word));
								} 
								System.out.println(ch + " " +  Consts.OPERATORS.ADD);
								word = ""; 
								break; 
							}
							case '-' : {
								if(!word.isEmpty()){
									System.out.println(word + " " + analyzeWord(word)); 
								}
								System.out.println(ch + " " +  Consts.OPERATORS.SUB);
								word = ""; 
								break; 
							}
							case '*' : {
								if(!word.isEmpty()){
									System.out.println(word + " " + analyzeWord(word)); 
								}
								System.out.println(ch + " " +  Consts.OPERATORS.MUL);
								word = ""; 
								break; 
							}
							case '/' : {
								if(!word.isEmpty()){
									System.out.println(word + " " + analyzeWord(word)); 
								}
								System.out.println(ch + " " +  Consts.OPERATORS.DIV);
								word = ""; 
								break; 
							}
							case '^' : {
								
							}
							case '%' : {
								if(!word.isEmpty()){
									System.out.println(word + " " + analyzeWord(word)); 
								}
								System.out.println(ch + " " +  Consts.OPERATORS.MOD);
								word = ""; 
								break; 
							}
							case '=' : { 
								if(!word.isEmpty()){
									System.out.println(word + " " + analyzeWord(word));
								}
								System.out.println(ch + " " +  Consts.OPERATORS.EQU);
								word = "";
								break;
							}
							case ' ' : {
								if(!word.isEmpty()){
									System.out.println(word + " " + analyzeWord(word));
								}
								System.out.println(ch + " " +  Consts.SEPARATORS.SPACE);
								word = "";
								break;
							}
							case '	' : { 
								if(!word.isEmpty()){
									System.out.println(word + " " + analyzeWord(word));
								}
								System.out.println(ch + " " + Consts.SEPARATORS.TAB);
								word = "";
								break;
							}
							case ',' : {
								if(!word.isEmpty()){
									System.out.println(word + " " + analyzeWord(word));
								}
								System.out.println(ch + " " + Consts.CHARACTERS.COMMA);
								word = "";
								break;
							}
							default : {
								word += ch;
								break;
							}
						}
					}
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static int analyzeWord(String word) {
		int ret = Consts.VARIABLE;
		
		if(word.isEmpty()) {
			return ret;
		}
		
		switch(word) {
			case "ako" 		: ret = Consts.CONDITIONAL_OPERATORS.IF; break;
			case "inache" 	: ret = Consts.CONDITIONAL_OPERATORS.ELSE; break;
			
			case "cqlo"		: ret = Consts.DATA_TYPES.INTEGER; break;
			case "drobno"	: ret = Consts.DATA_TYPES.FLOAT; break;
			case "dvoino"	: ret = Consts.DATA_TYPES.DOUBLE; break;
			case "simvol"	: ret = Consts.DATA_TYPES.CHAR; break;
			case "niz"		: ret = Consts.DATA_TYPES.STRING; break;
			
			case "za"		: ret = Consts.LOOPS.FOR; break;
			case "dokato"	: ret = Consts.LOOPS.WHILE; break;
		}
		
		if(isInt(word)) {
			ret = Consts.CONSTANTS.INTEGER;
		}
		else if(isFloat(word)) {
			ret = Consts.CONSTANTS.FLOAT;
		}
		
		return ret;
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
	
	/*private static boolean isDouble(String str) {
		try
	     {
			Double.parseDouble(str);
	         return true;
	     }
	     catch(NumberFormatException e)
	     {
	         return false;
	     }
	}*/
	
}
