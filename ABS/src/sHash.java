
public class sHash {
	private final int TABLE_SIZE = 10;
	private final int CHAIN_SIZE = 5;
	
	private int tableSize;
	private int tableMask;
	
	private Array[] global__array;
	
	sHash() {
		this.tableSize = TABLE_SIZE;
		this.tableMask = this.tableSize - 1;
		
		this.global__array = new Array[tableSize];
		
		for(int i = 0; i < tableSize; ++i) {
			this.global__array[i] = new Array(CHAIN_SIZE);
		}
	}
	
	sHash(int table_size) {
		this.tableSize = table_size;
		this.tableMask = tableSize - 1;
		
		global__array = new Array[tableSize];
		
		for(int i = 0; i < tableSize; ++i) {
			this.global__array[i] = new Array(CHAIN_SIZE);
		}
	}
	
	sHash(int table_size, int chain_size) {
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
		
		for(char c : key.getName().toCharArray()) {
			hash = (hash << 4) + c;
		}
		
		return hash & tableMask;
	}
	
	private int insert(Symbol key) {
		int index = this.hash(key);
		
		global__array[index].add(key);
		
		return index;		
	}
	
	public int lookup(Symbol key) {
		int index = this.hash(key);
		
		Array chain = global__array[index];

		for(int i = 0; i < chain.size(); i++) {
				if(chain.get(i).isNull()) {
					return -1;
				}
				else if(chain.get(i).compareTo(key)) {
					return index;
			}
		}
		//key not found
		return -1;
	}
	
	public int lookupInsert(Symbol key) {
		return this.lookup(key) == -1 ? this.insert(key) : this.hash(key);
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