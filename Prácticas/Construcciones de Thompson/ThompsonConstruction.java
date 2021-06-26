class ThompsonConstruction{
	private NFA automata;

	ThompsonConstruction(Character a){
	    automata = new NFA();
		State finalState = new State();
		finalState.setFinal(true);
		automata.setFinalState(finalState);
		automata.addTransition(automata.getInitialState(), a, finalState);
	}

	ThompsonConstruction(Character a, Character operation, Character b){
		if(operation == '|'){
			automata = new NFA();

			NFA automataA = new ThompsonConstruction(a).getAutomata();
			//System.out.println("--");
			automataA.getFinalState().setFinal(false);
			NFA automataB = new ThompsonConstruction(b).getAutomata();
			automataB.getFinalState().setFinal(false);

			automata.addTransition(automata.getInitialState(), 'E', automataA.getInitialState());
			automata.mergeAutomata(automataA);
			automata.addTransition(automata.getInitialState(), 'E', automataB.getInitialState());
			automata.mergeAutomata(automataB);

			State auxFinal = new State();
			auxFinal.setFinal(true);
			automata.addTransition(automataA.getFinalState(), 'E', auxFinal);
			automata.addTransition(automataB.getFinalState(), 'E', auxFinal);
			automata.setFinalState(auxFinal);
		}else if( operation == '.'){
			NFA automataA = new ThompsonConstruction(a).getAutomata();
			automataA.getFinalState().setFinal(false);
			NFA automataB = new ThompsonConstruction(b).getAutomata();
			automataB.getFinalState().setFinal(false);
			automata = new NFA(automataA.getInitialState());
			automataA.getFinalState().setFinal(false);
			//System.out.println("Setted");
			automata.mergeAutomata(automataA);
			//System.out.println("MErged Automata A");

			automataB.updateInitialState(automataA.getFinalState());

			automata.mergeAutomata(automataB);
			automata.setFinalState(automataB.getFinalState());
			//automata.addTransition(automata.getInitialState(), 'E', automataB.getInitialState());
		}
	}

	ThompsonConstruction(NFA automataA, Character operation, NFA automataB){	
		if(operation == '|'){
			automata = new NFA();
			automataA.getFinalState().setFinal(false);
			automataB.getFinalState().setFinal(false);

			automata.addTransition(automata.getInitialState(), 'E', automataA.getInitialState());
			automata.mergeAutomata(automataA);
			automata.addTransition(automata.getInitialState(), 'E', automataB.getInitialState());
			automata.mergeAutomata(automataB);

			State auxFinal = new State();
			auxFinal.setFinal(true);
			automata.addTransition(automataA.getFinalState(), 'E', auxFinal);
			automata.addTransition(automataB.getFinalState(), 'E', auxFinal);
			automata.setFinalState(auxFinal);
		}
		else if( operation == '.'){
			automata = new NFA(automataA.getInitialState());
			automataA.getFinalState().setFinal(false);
			automata.mergeAutomata(automataA);
			automataB.updateInitialState(automataA.getFinalState());
			automata.mergeAutomata(automataB);
			automata.setFinalState(automataB.getFinalState());
		}
		//System.out.println("ADDED automata " + operation);	
	}

	ThompsonConstruction(NFA automataA, Character operation){
		if( operation == '*'){
			automata = new NFA();
						
			automataA.addTransition(automataA.getFinalState(), 'E', automataA.getInitialState());
			automataA.getFinalState().setFinal(false);
			automata.addTransition(automata.getInitialState(), 'E', automataA.getInitialState());
			
			State auxFinal = new State();
			auxFinal.setFinal(true);
			automata.addTransition(automataA.getFinalState(), 'E', auxFinal);
			automata.addTransition(automata.getInitialState(), 'E', auxFinal);
			automata.mergeAutomata(automataA);
			automata.setFinalState(auxFinal);
		}
	}

	public NFA getAutomata(){
		return this.automata;
	}
}