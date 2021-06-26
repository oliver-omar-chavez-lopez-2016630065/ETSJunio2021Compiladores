import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

class regexProcessor{
	private String regex;
	private static final Map<Character, Integer> precedenceMap;
	static {
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		map.put('(', 1);
		map.put('|', 2);
		map.put('.', 3); // explicit concatenation operator
		map.put('*', 4);
		precedenceMap = Collections.unmodifiableMap(map);
	};

	regexProcessor(String regex){
		this.regex = regex;
	}

	public String getRegex(){
		return this.regex;
	}

	public void addConcatenations(){
        /*
             *#  Si a,b son simbolos en Σ
             *#  Tenemos los siguientes casos:
             *#  a & b
             *#  a & (
             *#  ) & a
             *#  * & a
             *#  * & (
             *#  ) & (
        */
		String newRegular = new String("");
		
        for (int i = 0; i < regex.length() - 1; i++) {
            if (!isOperator(regex.charAt(i))  && (!isOperator(regex.charAt(i + 1)) || regex.charAt(i+1) == '(' )) {
                newRegular += regex.charAt(i) + ".";
                //System.out.println("CASO "+i);
            } else if (regex.charAt(i) == ')' && !isOperator(regex.charAt(i + 1))) {
                newRegular += regex.charAt(i) + ".";
            } else if (regex.charAt(i) == '*' && !isOperator(regex.charAt(i + 1))) {
                newRegular += regex.charAt(i) + ".";
            } else if (regex.charAt(i) == '*' && regex.charAt(i + 1) == '(') {
                newRegular += regex.charAt(i) + ".";
            } else if (regex.charAt(i) == ')' && regex.charAt(i + 1) == '(') {
                newRegular += regex.charAt(i) + ".";
            } else {
                newRegular += regex.charAt(i);
            }
        }
        newRegular += regex.charAt(regex.length() - 1);
  		this.regex = newRegular;
	}

	private static Integer getPrecedence(Character c) {
		Integer precedence = precedenceMap.get(c);
		return precedence == null ? 5 : precedence;
	}

	public void convertToPostfix(){
		String postfix = new String("");

		Stack<Character> stack = new Stack<Character>();

		for (Character c : regex.toCharArray()){
			switch (c) {
				case '(':
					stack.push(c);
					break;

				case ')':
					while (!stack.peek().equals('(')) {
						postfix += stack.pop();
					}
					stack.pop();
					break;

				default:
					while (stack.size() > 0) {
						Character peekedChar = stack.peek();
						Integer peekedCharPrecedence = getPrecedence(peekedChar);
						Integer currentCharPrecedence = getPrecedence(c);

						if (peekedCharPrecedence >= currentCharPrecedence) {
							postfix += stack.pop();
						} else {
							break;
						}
					}
					stack.push(c);
					break;
			}
		}

		while (stack.size() > 0)
			postfix += stack.pop();

		this.regex = postfix;
	}


	public static boolean isOperator(Character c){
		if(c == '|' || c == '*' || c == '.' ||c == '(' || c == ')' )
			return true;
		return false;
	}
}