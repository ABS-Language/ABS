public class Hash {
	private final int TABLE_SIZE = 50;
	private final int CHAIN_SIZE = 5;
	
	private int tableSize;
	private int tableMask;
	
	private Chain[] global__array;
	
	Hash() {
		this.tableSize = TABLE_SIZE;
		this.tableMask = this.tableSize - 1;
		
		this.global__array = new Chain[tableSize];
		
		for(int i = 0; i < tableSize; ++i) {
			this.global__array[i] = new Chain(CHAIN_SIZE);
		}
	}
	
	Hash(int table_size) {
		this.tableSize = table_size;
		this.tableMask = tableSize - 1;
		
		global__array = new Chain[tableSize];
		
		for(int i = 0; i < tableSize; ++i) {
			this.global__array[i] = new Chain(CHAIN_SIZE);
		}
	}
	
	Hash(int table_size, int chain_size) {
		this.tableSize = table_size;
		this.tableMask = this.tableSize - 1;
		
		this.global__array = new Chain[this.tableSize];
		
		for(int i = 0; i < this.tableSize; ++i) {
			this.global__array[i] = new Chain(chain_size);
		}
	}
	
	
	
	/*private int hash(Symbol symbol) {

		int sum = 1;
		String name = symbol.getName();
		for(int i = 0; i < name.length(); ++i) {
			sum += name.charAt(i);
		}
		return ( (sum + (symbol.getCode() % this.tableSize) )% this.tableSize );

		//s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
	}*/
	
	
	public int hash(Symbol symbol) {
		long hash = 5381;
		
		for(char c : symbol.getName().toCharArray()) {
			hash = ((hash << 5) + hash) + c;
			hash = ((hash << 5) + hash) + c;
			hash = ((hash << 5) + hash) + c;
			hash = ((hash << 5) + hash) + c;
			hash = ((hash << 5) + hash) + c;
			hash = ((hash << 5) + hash) + c;
			hash = ((hash << 5) + hash) + c;
			hash = ((hash << 5) + hash) + c;
		}	
		hash /= 5381;
		return (int) (hash & tableMask);
	}
	
	/*
	public int hash(Symbol symbol) {
		int hash = 0;
		for(int i = 0; i < symbol.getName().length(); i++) {
			hash = (hash << 4) + symbol.getName().charAt(i);
		}
		
		return hash & tableMask;
	}
	*/
	public Position insert(Symbol symbol) {
		int cell = this.hash(symbol);
		
		Position pos = new Position();
		pos.setCell(cell); //cell position in hash table
		
		pos.setChain(global__array[cell].add(symbol));
		
		symbol.setPosition(pos);
		
		return pos;		
	}
	
	public Symbol get(int cell, int chain) {
		return global__array[cell].get(chain);
	}
	
	public Symbol get(Position pos) {
		return global__array[pos.getCell()].get(pos.getChain());
	}
	
	public Position lookup(Symbol symbol) {
		int cell = this.hash(symbol);
		
		Position pos = new Position();
		pos.setCell(cell); //cell position in hash table
		
		Chain chain = global__array[cell];

		for(int i = 0; i < chain.size(); i++) {
				if(chain.get(i).isNull()) {
					return null;
				}
				
				if(chain.get(i).compareTo(symbol)) {
					pos.setChain(i);
					return pos;
				}
		}
		//key not found
		return null;
	}
	
	public Position lookupInsert(Symbol symbol) {
		Position pos = this.lookup(symbol);
		
		return pos == null ? this.insert(symbol) : pos;
	}
	
	public void printTable() {
		int i = 0;
		
		for(Chain chain : global__array) {
			System.out.print(i++);
			
			for(int j = 0; j < chain.size(); ++j) {
				System.out.print(" -> '" + chain.get(j) + "'");
			}
			
			System.out.println("\n=========");			
		}
	}
}

class Position {
	private int cell;
	private int chain;
	
	Position() {
		this.setCell(-1);
		this.setChain(-1);
	}
	
	Position(int cell, int chain){
		this.setCell(cell);
		this.setChain(chain);
	}
	
	public int getCell() {
		return cell;
	}

	public void setCell(int cell) {
		this.cell = cell;
	}

	public int getChain() {
		return chain;
	}

	public void setChain(int chain) {
		this.chain = chain;
	}
	
	public String toString(){
		return "[" + this.getCell() + "]" + "[" + this.getChain() + "]";
	}
	
}