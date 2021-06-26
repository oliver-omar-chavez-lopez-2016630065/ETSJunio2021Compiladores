class State{
	private boolean isFinal;
	private int id;
	public static int numOfStates = 0;

	State(){
		this.id = numOfStates;
		this.numOfStates++;
	}

	public int getId(){
		return this.id;
	}

	public void setFinal(boolean value){
		this.isFinal = value;
	}

	public void setId(int id){
		this.id = id;
	}

/*
	public static void updateNumOfStates(int num){
		//this.numOfStates = num;
	}*/
}