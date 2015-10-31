public class Symbol {
	private String name;
	private int code;
	private int type;
	
	private int iValue;
	private float fValue;
	private String sValue;
	private char cValue;
	
	
	Symbol() {
		this.code = 0;
		this.name = Consts.DEFAULT_STRING;
		this.type = Consts.TYPES.UNKNOWN_TYPE;
		
		this.iValue = 0;
		this.fValue = 0;
		this.cValue = 0;
		this.sValue = null;
	}
	
	Symbol(String name) {
		this.code = 0;
		this.name = name;
		this.type = Consts.TYPES.UNKNOWN_TYPE;
		
		this.iValue = 0;
		this.fValue = 0;
		this.cValue = 0;
		this.sValue = null;
	}
	
	Symbol(String name, int code) {
		this.code = code;
		this.name = name;
		this.type = Consts.TYPES.UNKNOWN_TYPE;
		
		this.iValue = 0;
		this.fValue = 0;
		this.cValue = 0;
		this.sValue = null;
	}
	
	Symbol(String name, int code, int type) {
		this.code = code;
		this.name = name;
		this.type = type;
		
		this.iValue = 0;
		this.fValue = 0;
		this.cValue = 0;
		this.sValue = null;
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
	
	public float setIntValue() {
		return this.iValue;
	}
	
	public float getFloatValue() {
		return this.fValue;
	}
	
	public String getStringValue() {
		return this.sValue;
	}
	
	public char getCharValue() {
		return this.cValue;
	}
	//======
	public void setValue(int value) {
		this.iValue = value;
	}
	
	public void setValue(float value) {
		this.fValue = value;
	}
	
	public void setValue(String value) {
		this.sValue = value;
	}
	
	public void setValue(char value) {
		this.cValue = value;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public boolean compareTo(Symbol item) {
		return (this.name.equals(item.getName()) && this.code == item.getCode()) ? true : false;
	}
}
