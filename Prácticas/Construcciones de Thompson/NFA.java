import java.io.*;
import java.util.*;
@SuppressWarnings("unchecked")
class NFA{
	private Set<State> states = new HashSet<State>();
	private State initialState, finalState;
	private ArrayList<Transition> transitions = new ArrayList<Transition> ();
	private Set<Character> alphabet = new HashSet<Character>();

	/*NFA(Set<Character>  alphabet){
		this.alphabet = alphabet;
		initialState = new State();
	}*/

	NFA(){
		initialState = new State();
	}
	NFA(State initial){
		this.initialState = initial;
	}

	public void addTransition(State state, Character symbol){
		transitions.add(new Transition(state, new State(), symbol));
	}

	public void addTransition(State origin, Character symbol, State destiny){
		transitions.add(new Transition(origin, destiny, symbol));

	}

	public void addTransition(State origin, Character symbol, NFA automata){
		automata.getFinalState().setFinal(false);
		transitions.add(new Transition(origin, automata.getInitialState(), symbol));
	}

	public void mergeAutomata(NFA automata){
		transitions.addAll(automata.getTransitions());
		//states.addAll(automata.getStates());
	}

	public Set<State> getStates(){
		return this.states;
	}

	public void printStates(){
		int []aux = new int[states.size()];
		int i = 0;
		for(State currentState: states)
			aux[i++] = currentState.getId();
		System.out.println(Arrays.toString(aux));
	}


	public void printTransitions(){
		for (Transition currentTransition : transitions){
		   
		   System.out.println(currentTransition.toString());
		}
	}

	public ArrayList<Transition> getTransitions(){
		return this.transitions;
	}

	public void computeStates(){
		states = new HashSet<State>();
		for (Transition currentTransition : transitions){
			if(!states.contains(currentTransition.getOrigin()))
				states.add(currentTransition.getOrigin());
			if(!states.contains(currentTransition.getDestiny()))
				states.add(currentTransition.getDestiny());
		}	
	}

/*
	public void sortTransitions(){
		Map<Integer,Integer> sortedId = new HashMap<Integer,Integer>();
		int i=0;
		for (Transition currentTransition : transitions){
			State origin = currentTransition.getOrigin();
			if(!sortedId.containsKey(origin.getId())){
				sortedId.put(origin.getId(),i);
				i++;
			}			
		}

		for (Transition currentTransition : transitions){
			State destiny = currentTransition.getDestiny();
			if(!sortedId.containsKey(destiny.getId())){
				sortedId.put(destiny.getId(),i);
				i++;
			}	
		}

		for(State currentState: states){
			Integer id = currentState.getId();
			currentState.setId(sortedId.get(id));
		}
		transitions.sort(Comparator.comparing(transition -> transition.getOrigin().getId()));
	}

	public void sortTransitions2(){
		ArrayList<State> aux = new ArrayList<State>();
		int i = 0;
		
		for (Transition currentTransition : transitions){
			State origin = currentTransition.getOrigin();
			State destiny = currentTransition.getDestiny();
			if(!aux.contains(origin))
				aux.add(origin);
			if(!aux.contains(destiny))
				aux.add(destiny);	
		}


		for (State currentState : aux){
			currentState.setId(i);
			i++;
		}
		
		transitions.sort(Comparator.comparing(transition -> transition.getOrigin().getId()));
	}

*/

		// Ordenar transiciones de forma más natural
		public void sortTransitions(){
		ArrayList<State> aux = new ArrayList<State>();
		int i = 0;
		aux.add(transitions.get(0).getOrigin());


		for(i = 0; i<aux.size(); i++) {
			State currentState = aux.get(i);
			for (Transition currentTransition : transitions){
				if(currentTransition.getOrigin().equals(currentState)){
					if(!aux.contains(currentTransition.getDestiny()))
						aux.add(currentTransition.getDestiny());
				}
			}	
		}


		i=0;
		for (State currentState : aux){
			currentState.setId(i);
			i++;
		}
		
		transitions.sort(Comparator.comparing(transition -> transition.getOrigin().getId()));
	}


	public State getInitialState(){
		return this.initialState;
	}

	public State getFinalState(){
		return this.finalState;
	}

	public void setFinalState(State s){
		this.finalState = s;
		s.setFinal(true);
	}

	public void setInitialState(State s){
		this.initialState = s;
		
	}

	public void updateInitialState(State newState){
		//this.initialState = newState;
		for (Transition currentTransition : transitions){
		   if(currentTransition.getOrigin().equals(initialState))
		   		currentTransition.setOrigin(newState);
		   if(currentTransition.getDestiny().equals(initialState))
		   		currentTransition.setDestiny(newState);
		}
		this.initialState = newState;
	}
}