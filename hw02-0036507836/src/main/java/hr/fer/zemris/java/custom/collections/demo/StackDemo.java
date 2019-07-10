package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

public class StackDemo {

	public static void main(String[] args) {
		
		if(args.length!=1) {
			System.err.println("Exactly one argument must be provided.");
			System.exit(1);
		}
		
		ObjectStack stack = new ObjectStack();
		String[] expression = args[0].split(" ");
		
		for(String s : expression) {
			//case it is blank
			if(s.isBlank()) continue;
			
			evaluateNext(s, stack);
			
		}
		
		if(stack.size()!=1) {
			System.err.println("There's an error in the expression.");
			System.exit(1);
		}
		
		System.out.printf("Expression evaluates to %d.%n", stack.pop());
		
	}
	
	public static void evaluateNext(String s, ObjectStack stack) {
		//case it is a number
		Integer nextInteger = null;
		try {
			nextInteger = Integer.parseInt(s);
			stack.push(nextInteger);
			return;
		}catch (NumberFormatException ex){
		}
		
		if(s.equals("+") || s.equals("-") ||  s.equals("*") || s.equals("/") || s.equals("%")) {
			if(stack.size()<2) {
				System.err.println("There is an error in the expression.");
				System.exit(1);
			}
		}
		
		//case it is an operation
		switch (s) {
			case "+":
				stack.push((Integer)stack.pop()+(Integer)stack.pop());
				return;
			case "-":
				Integer secoundSub = (Integer)stack.pop();
				Integer firstSub = (Integer)stack.pop();
				stack.push(firstSub-secoundSub);
				return;
			case "*":
				stack.push((Integer)stack.pop()*(Integer)stack.pop());
				return;
			case "/":
				if((Integer)stack.peek()==0) {
					System.err.println("Cannot devide by zero");
					System.exit(1);
				}
				Integer secoundDiv = (Integer)stack.pop();
				Integer firstDiv = (Integer)stack.pop();
				stack.push(firstDiv/secoundDiv);
				return;
			case "%":
				if((Integer)stack.peek()==0) {
					System.err.println("Cannot devide by zero");
					System.exit(1);
				}
				Integer secoundRem = (Integer)stack.pop();
				Integer firstRem = (Integer)stack.pop();
				stack.push(firstRem%secoundRem);
				return;
			default:
				System.err.println("Unsupported operation " + s);
				System.exit(1);
		}
	}

}
