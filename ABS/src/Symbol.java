public class Symbol {
	private String name;
	private int code;
	private int type;
	
	Symbol() {
		this.code = 0;
		this.name = Consts.DEFAULT_STRING;
		this.type = Consts.TYPES.UNKNOWN_TYPE;
	}
	
	Symbol(String name) {
		this.code = 0;
		this.name = name;
	}
	
	Symbol(String name, int code) {
		this.code = code;
		this.name = name;
		this.type = type;
	}
	
	Symbol(String name, int code, int type) {
		this.code = code;
		this.name = name;
		this.type = type;
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
		return this.name == Consts.DEFAULT_STRING;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public boolean compareTo(Symbol item) {
		return (this.name.equals(item.getName()) && this.code == item.getCode()) ? true : false;
	}
}
