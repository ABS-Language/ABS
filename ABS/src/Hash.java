import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


public class Hash {
	private final int SIZE = 10;
	
	private int tableSize;
	
	private List<LinkedList<String>> global__List = new LinkedList<LinkedList<String>>();
	
	Hash(int size) {
		this.tableSize = size;
		
		LinkedList<String> list;
		
		for(int i = 0; i < tableSize; ++i) {
			list = new LinkedList<String>();
			list.add("<init>");
			
			global__List.add(list);
		}
	}
	
	Hash() {
		this.tableSize = SIZE;
		
		LinkedList<String> list;
		for(int i = 0; i < tableSize; ++i) {
			list = new LinkedList<String>();
			list.add("<init>");
			
			global__List.add(list);
		}
	}
	
	private int hash(String key) {
		int sum = 1;
		
		for(int i = 0; i < key.length(); ++i) {
			sum *= key.charAt(i);
		}

		return (sum % tableSize);
	}
	
	private int insert(String key) {
		int index = this.hash(key);
		
		global__List.get(index).add(key);
		
		return index;		
	}
	
	public int lookup(String key) {
		int index = this.hash(key);
		
		LinkedList<String> list = global__List.get(index);
		
		if(list.isEmpty()) {
			//key not found
			return -1;
		}
		
		ListIterator<String> it = list.listIterator();
		
		while(it.hasNext()) {
			if(it.next().compareTo(key) == 0) {
				return index;
			}
		}
		//key not found
		return -1;
	}
	
	public int lookupInsert(String key) {
		return this.lookup(key) == -1 ? this.insert(key) : this.hash(key);
	}
	
	public void printTable() {
		ListIterator<String> it;
		
		int i = 0;
		
		for(LinkedList<String> list : global__List) {
			
			it = list.listIterator();
			
			System.out.print(i++);
			
			while(it.hasNext()) {
					System.out.print(" -> '" + it.next() + "'");
			}
			
			System.out.println("\n=========");
		}
	}
}