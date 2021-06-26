import java.util.Stack;
import java.io.*;
import java.util.*;
class Grapher{
	private NFA nfa;

	Grapher(NFA nfa){
		this.nfa = nfa;
	}

	public String makeGraph() throws IOException{
		ArrayList<Transition> trasitions = nfa.getTransitions();
		Set<State> states = nfa.getStates();
		String s = "digraph finite_state_machine { \nrankdir=LR;\nsize=\"8,5\"\n";

		State finalState = nfa.getFinalState();
		s += "node [shape = doublecircle]; "+finalState.getId()+";\nnode [shape = circle];\n";
		for(Transition transition: trasitions)
			s+=transition.toGraphString();
		s+="}";

		System.out.println(s);

		File myFile = new File("automataGraph.dot");
		FileWriter fooWriter = new FileWriter(myFile, false); 
		fooWriter.write(s);
		fooWriter.close();
		//dot -Tpng test.dot > output.png
		return s;
	}
}