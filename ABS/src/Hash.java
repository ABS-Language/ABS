
public class Hash {
	private final int TABLE_SIZE = 50;
	private final int CHAIN_SIZE = 5;
	
	private int tableSize;
	private int tableMask;
	
	private Array[] global__array;
	
	Hash() {
		this.tableSize = TABLE_SIZE;
		this.tableMask = this.tableSize - 1;
		
		this.global__array = new Array[tableSize];
		
		for(int i = 0; i < tableSize; ++i) {
			this.global__array[i] = new Array(CHAIN_SIZE);
		}
	}
	
	Hash(int table_size) {
		this.tableSize = table_size;
		this.tableMask = tableSize - 1;
		
		global__array = new Array[tableSize];
		
		for(int i = 0; i < tableSize; ++i) {
			this.global__array[i] = new Array(CHAIN_SIZE);
		}
	}
	
	Hash(int table_size, int chain_size) {
		this.tableSize = table_size;
		this.tableMask = this.tableSize - 1;
		
		this.global__array = new Array[this.tableSize];
		
		for(int i = 0; i < this.tableSize; ++i) {
			this.global__array[i] = new Array(chain_size);
		}
	}
	
	
	/*
	private int hash(String key) {

		int sum = 1;
		
		for(int i = 0; i < key.length(); ++i) {
			sum *= key.charAt(i);
		}

		return (sum % tableSize);
	}
	*/
	/*
	public int hash(String key) {
		long hash = 5381;
		
		for(char c : key.toCharArray()) {
			hash = ((hash << 5) + hash) + c;
			hash = ((hash << 5) + hash) + c;
			hash = ((hash << 5) + hash) + c;
			hash = ((hash << 5) + hash) + c;
			hash = ((hash << 5) + hash) + c;
			hash = ((hash << 5) + hash) + c;
			hash = ((hash << 5) + hash) + c;
			hash = ((hash << 5) + hash) + c;
		}	
		
		return (int) (hash & tableMask);
	}
	*/
	public int hash(Symbol key) {
		int hash = 0;
		
		for(int i = 0; i < key.getName().length(); i++) {
			hash = (hash << 4) + key.getName().charAt(i);
		}
		
		return hash & tableMask;
	}
	
	public Position insert(Symbol key) {
		int cell = this.hash(key);
		
		Position pos = new Position();
		pos.setCell(cell); //cell position in hash table
		
		pos.setChain(global__array[cell].add(key));
		
		return pos;		
	}
	
	public Position lookup(Symbol key) {
		int cell = this.hash(key);
		
		Position pos = new Position();
		pos.setCell(cell); //cell position in hash table
		
		Array chain = global__array[cell];

		for(int i = 0; i < chain.size(); i++) {
				if(chain.get(i).isNull()) {
					return null;
				}
				else if(chain.get(i).compareTo(key)) {
					pos.setChain(i);
					return pos;
			}
		}
		//key not found
		return null;
	}
	
	public Position lookupInsert(Symbol key) {
		Position pos = this.lookup(key);
		
		return pos == null ? this.insert(key) : pos;
	}
	
	public void printTable() {
		int i = 0;
		
		for(Array chain : global__array) {
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
	
	
}