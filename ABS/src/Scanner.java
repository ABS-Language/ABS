import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Scanner {
	private final static String wordFilePath = "file.txt";
	
	public static void read(){
		FileReader read = null;
		
		try {
			read = new FileReader(wordFilePath);
		} 
		catch (FileNotFoundException e) {
				e.printStackTrace();
		}
		
		BufferedReader buffer = new BufferedReader(read);
		
		String textRead;
		String word = "";
		
		char ch;
		
		try {
			while((textRead = buffer.readLine()) != null) {		
				if(!(textRead = textRead.trim()).isEmpty()) {
			//		System.out.println(textRead);
					
					for(int i = 0; i < textRead.length(); ++i) {
						ch = textRead.charAt(i);
						
						if(ch == Consts.FORBIDDEN.SOH
							|| ch == Consts.FORBIDDEN.SOH
							|| ch == Consts.FORBIDDEN.STX
							|| ch == Consts.FORBIDDEN.ETX
							|| ch == Consts.FORBIDDEN.ENQ
							|| ch == Consts.FORBIDDEN.ACK
							|| ch == Consts.FORBIDDEN.BEL
							|| ch == Consts.FORBIDDEN.BS
							|| ch == Consts.FORBIDDEN.LF
							|| ch == Consts.FORBIDDEN.CR) {
								continue;
							}
						
						switch(ch) {
							case ';' : {
								if(!word.isEmpty())
									System.out.println(word + " " + analyzeWord(word));
								System.out.println(ch + " " +  Consts.EOS.SEMICOLON);
								word = ""; 
								break; 
							}
							case '+' : { 
								if(!word.isEmpty())
									System.out.println(word + " " + analyzeWord(word)); 
								System.out.println(ch + " " +  Consts.OPERATORS.ADD);
								word = ""; 
								break; 
							}
							case '-' : {
								if(!word.isEmpty())
									System.out.println(word + " " + analyzeWord(word)); 
								System.out.println(ch + " " +  Consts.OPERATORS.SUB);
								word = ""; 
								break; 
							}
							case '*' : {
								if(!word.isEmpty())
									System.out.println(word + " " + analyzeWord(word)); 
								System.out.println(ch + " " +  Consts.OPERATORS.MUL);
								word = ""; 
								break; 
							}
							case '/' : {
								if(!word.isEmpty())
									System.out.println(word + " " + analyzeWord(word)); 
								System.out.println(ch + " " +  Consts.OPERATORS.DIV);
								word = ""; 
								break; 
							}
							case '^' : {
								
							}
							case '%' : {
								if(!word.isEmpty())
									System.out.println(word + " " + analyzeWord(word)); 
								System.out.println(ch + " " +  Consts.OPERATORS.MOD);
								word = ""; 
								break; 
							}
							case '=' : { 
								if(!word.isEmpty())
									System.out.println(word + " " + analyzeWord(word));
								System.out.println(ch + " " +  Consts.OPERATORS.EQU);
								word = "";
								break;
							}
							case ' ' : {
								if(!word.isEmpty())
									System.out.println(word + " " + analyzeWord(word));
								System.out.println(ch + " " +  Consts.SEPARATORS.SPACE);
								word = "";
								break;
							}
							case '	' : { 
								if(!word.isEmpty())
									System.out.println(word + " " + analyzeWord(word));
								System.out.println(ch + " " + Consts.SEPARATORS.TAB);
								word = "";
								break;
							}
							case ',' : {
								if(!word.isEmpty())
									System.out.println(word + " " + analyzeWord(word));
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
	
	private static boolean isDouble(String str) {
		try
	     {
			Double.parseDouble(str);
	         return true;
	     }
	     catch(NumberFormatException e)
	     {
	         return false;
	     }
	}
	
}
