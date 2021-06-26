import java.util.Stack;
import java.io.*;
import java.util.*;
class NFA_Constructor{
	private String regex;
	private NFA fullNFA;

	NFA_Constructor(String regex){
		this.regex = regex;
	}

	public void run(){
		Stack<NFA> stackNFA = new Stack<NFA>();

		for (Character currentCharacter : regex.toCharArray()){
			NFA nfa1, nfa2;
			switch(currentCharacter){
				case '.': 
							nfa2 = stackNFA.pop();
							nfa1 = stackNFA.pop();
							stackNFA.push(new ThompsonConstruction(nfa1,'.',nfa2).getAutomata());
							break;
				case '|': 
							nfa2 = stackNFA.pop();
							nfa1 = stackNFA.pop();
							stackNFA.push(new ThompsonConstruction(nfa1,'|',nfa2).getAutomata());
							//System.out.println("or");
							break;
				case '*': 
							nfa1 = stackNFA.pop();
							stackNFA.push(new ThompsonConstruction(nfa1,'*').getAutomata());
							//System.out.println("cerradura");
							break;
				default:
							stackNFA.push(new ThompsonConstruction(currentCharacter).getAutomata());
			}
		}
		this.fullNFA = stackNFA.pop();
		fullNFA.computeStates();
		fullNFA.sortTransitions();

	}

	public NFA getNFA(){
		return this.fullNFA;
	}



}