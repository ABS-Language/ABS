public class Symbol {
	private final String defaultString = "";
	private String name;
	private int code;
	
	Symbol() {
		this.code = 0;
		this.name = defaultString;
	}
	
	Symbol(String name) {
		this.code = 0;
		this.name = name;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getName() {
		try {
			return this.name;
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public boolean isNull() {
		return this.name == defaultString;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public boolean compareTo(Symbol item) {
		return (this.name == item.getName() && this.code == item.getCode()) ? true : false;
	}
}
