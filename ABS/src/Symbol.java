public class Symbol {
	private final String DEFAULT_STRING = "";
	private String name;
	private int code;
	
	Symbol() {
		this.code = 0;
		this.name = DEFAULT_STRING;
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
		return this.name;
	}
	
	public boolean isNull() {
		return this.name == DEFAULT_STRING;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public boolean compareTo(Symbol item) {
		return (this.name == item.getName() && this.code == item.getCode()) ? true : false;
	}
}
