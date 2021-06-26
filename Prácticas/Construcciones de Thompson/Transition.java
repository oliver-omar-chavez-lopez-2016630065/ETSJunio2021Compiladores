class Transition{
	private State origin, destiny;
	private Character symbol;
	Transition(State origin, State destiny, Character symbol){
		this.origin = origin;
		this.destiny = destiny;
		this.symbol = symbol; 
	}

	public String toString(){
		return "("+origin.getId()+","+symbol+") -> "+destiny.getId();
	}

	public State getOrigin(){
		return this.origin;
	}

	public void setOrigin(State s){
		this.origin = s;
	}

	public State getDestiny(){
		return this.destiny;
	}

	public void setDestiny(State s){
		this.destiny = s;
	}

	public Character getSymbol(){
		return this.symbol;
	}

	public String toGraphString(){
		return origin.getId()+" -> " + destiny.getId()+" [label = \""+symbol+"\"];\n";
	}
}