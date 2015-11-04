public class Symbol {
	private String name;
	private int code;
	private int type;
	private Position pos;
	
	private int iValue;
	private double dValue;
	private String sValue;
	private char cValue;
	
	
	Symbol() {
		this.code = Consts.UNKNOWN;;
		this.name = Consts.DEFAULT_STRING;
		this.type = Consts.TYPES.UNKNOWN_TYPE;
		this.pos = null;
		
		this.iValue = 0;
		this.dValue = 0;
		this.cValue = 0;
		this.sValue = null;
	}
	
	Symbol(String name) {
		this.code = Consts.UNKNOWN;;
		this.name = name;
		this.type = Consts.TYPES.UNKNOWN_TYPE;
		this.pos = null;
		
		this.iValue = 0;
		this.dValue = 0;
		this.cValue = 0;
		this.sValue = null;
	}
	
	Symbol(String name, int code) {
		this.code = code;
		this.name = name;
		this.type = Consts.TYPES.UNKNOWN_TYPE;
		this.pos = null;
		
		this.iValue = 0;
		this.dValue = 0;
		this.cValue = 0;
		this.sValue = null;
	}
	
	Symbol(String name, int code, int type) {
		this.code = code;
		this.name = name;
		this.type = type;
		this.pos = null;
		
		this.iValue = 0;
		this.dValue = 0;
		this.cValue = 0;
		this.sValue = null;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isNull() {
		return this.name == Consts.DEFAULT_STRING;
	}
	//====
	public void setCode(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
	//====
	public void setType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return this.type;
	}
	//====
	public void setIntValue(int value) {
		this.iValue = value;
	}
	
	public int getIntValue() {
		return this.iValue;
	}
	//====
	public void setDoubleValue(double value) {
		this.dValue = value;
	}
	
	public double getDoubleValue() {
		return this.dValue;
	}
	//====
	public void setCharValue(char value) {
		this.cValue = value;
	}
	
	public char getCharValue() {
		return this.cValue;
	}
	//====
	public void setStringValue(String value) {
		this.sValue = value;
	}
	
	public String getStringValue() {
		return this.sValue;
	}
	//====
	public void setPosition(Position pos) {
		this.pos = pos;
	}
	
	public Position getPosition() {
		return this.pos;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public boolean compareTo(Symbol item) {
		return (this.name.equals(item.getName()) && this.code == item.getCode()) ? true : false;
	}
}
