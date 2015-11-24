public class Row {
		private boolean branch;
		private int operator;
		private Object op1;
		private Position op2;
		private Position result;
		
		
		
		public Row(boolean branch, int operator, Position op1, Position op2, Position result){
			this.branch = branch;
			this.operator = operator;
			this.op1 = op1;
			this.op2 = op2;
			this.result = result;
		}
		
		public Row(boolean branch, int operator, int op1, Position op2, Position result){
			this.branch = branch;
			this.operator = operator;
			this.op1 = op1;
			this.op2 = op2;
			this.result = result;
		}
		
		public Row(int operator, int op1, Position op2, Position result){
			this.branch = false;
			this.operator = operator;
			this.op1 = op1;
			this.op2 = op2;
			this.result = result;
		}
		
		public Row(int operator, Position op1, Position op2, Position result){
			this.branch = false;
			this.operator = operator;
			this.op1 = op1;
			this.op2 = op2;
			this.result = result;
		}
		
		public int getOperator() {
			return this.operator;
		}

		public void setOperator(int operator) {
			this.operator = operator;
		}

		public int getOp1AsInt() {
			return (int)(this.op1);
		}
		
		public Object getOp1() {
			return this.op1;
		}

		public void setOp1(Position op1) {
			this.op1 = op1;
		}
		
		public void setOp1(int op1){
			this.op1 = op1;
		}

		public Position getOp2() {
			return this.op2;
		}

		public void setOp2(Position op2) {
			this.op2 = op2;
		}
		
		public void setBranch() {
			this.branch = true;
		}
		
		public boolean getBranch() {
			return this.branch;
		}
		
		public Position getResult() {
			return this.result;
		}
		
		@Override
		public String toString(){
			return 	(this.branch ? "true" : "false") + " | " 
					+ this.operator + " | " 
					+ ((op1 instanceof Integer) ? op1 + "." : Tetrada.getHash().get((Position)op1))
					+ Tetrada.getHash().get(op2)
					+ Tetrada.getHash().get(result);
		}
	}
