import java.util.Arrays;

public class Chain{
	private int SIZE = 100;
	private int size;
	
	private Symbol[] array;
	private int index;
	
	Chain() {
		this.array = new Symbol[SIZE];
		
		for(int i = 0; i < SIZE; ++i) {
			this.array[i] = new Symbol();
		}
		
		this.index = -1;
		this.size = this.SIZE;
	}
	
	Chain(int size) {
		this.array = new Symbol[size];
		
		for(int i = 0; i < size; ++i) {
			this.array[i] = new Symbol();
		}
		
		index = -1;
		this.size = size;
	}
	
	//resize array with given size
	public int resize(int size) {		
		int newLen = this.size + size;
		
		Symbol newArray[] = Arrays.copyOf(this.array, this.size);
		
		this.array = new Symbol[newLen];
		this.array = Arrays.copyOf(newArray, this.size);
		
		//initialize new cells
		for(int i = this.size + 1; i < newLen; ++i) {
			this.array[i] = new Symbol();
		}
				
		this.size = newLen;
		
		return newLen;
	}
	
	//resize array with double length
	public int resize() {
		int newLen = this.size * 2;
		
		Symbol newArray[] = Arrays.copyOf(this.array, this.size);
		
		this.array = new Symbol[newLen];
		this.array = Arrays.copyOf(newArray, newLen);
		
		//initialize new cells
		for(int i = this.size + 1; i < newLen; ++i) {
			this.array[i] = new Symbol();
		}
		
		this.size = newLen;

		return newLen;
	}
	
	public int add(Symbol item) {
		index++;
		
		try {
			this.array[index] = item;
		}
		catch(ArrayIndexOutOfBoundsException e) {
			this.resize();
		}

		this.array[index] = item;
		
		return index;
	}
	
	public Symbol get(int index) {
		try {
			return this.array[index];		
		}
		catch(ArrayIndexOutOfBoundsException e) {
			//...?
		}
		
		return null;
	}
	
	public void set(int index, Symbol item) {
		try {
			this.array[index] = item;
		}
		catch(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	public int size() {
		return this.size;
	}
}
