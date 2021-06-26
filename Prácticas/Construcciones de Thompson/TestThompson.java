import java.util.Scanner;
class TestThompson{

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);

		System.out.println("ESCRIBE LA REGEXP: ");
		String regex = sc.nextLine();
		regexProcessor processor = new regexProcessor(regex);
		processor.addConcatenations();
		System.out.println("REGEXP CON CONCATENACIONES: "+processor.getRegex());
		processor.convertToPostfix();
		System.out.println("REGEXP POSTFIX: "+processor.getRegex());

		NFA_Constructor constructor = new NFA_Constructor(processor.getRegex());
		constructor.run();
		NFA nfa = constructor.getNFA();

		System.out.println("\nTRANSICIONES: ");
		nfa.printTransitions();
		System.out.println("\nESTADOS: ");
		nfa.printStates();
		System.out.println("\nARCHIVO .DOT: ");
		Grapher grapher = new Grapher(nfa);
		try{
			grapher.makeGraph();
		}catch(Exception e){}
	}

}