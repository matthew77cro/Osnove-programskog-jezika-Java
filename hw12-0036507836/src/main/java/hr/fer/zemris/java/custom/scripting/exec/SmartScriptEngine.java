package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class models an engine used for executing scripts for SmartScript language
 * @author Matija
 *
 */
public class SmartScriptEngine {

	private DocumentNode documentNode;
	private RequestContext requestContext;
	private ObjectMultistack multistack = new ObjectMultistack();
	
	/**
	 * Used for executing script by visiting all of its nodes in the syntax tree
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		private Stack<Object> tmpStack = new Stack<Object>();
		
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String varName = node.getVariable().getName();
			multistack.push(varName, new ValueWrapper(node.getStartExpression().asText()));
			
			while(multistack.peek(varName).numCompare(node.getEndExpression().asText()) <= 0) {
				
				int children = node.numberOfChildren();
				for(int i=0; i<children; i++) {
					node.getChild(i).accept(this);
				}
				multistack.peek(varName).add(node.getStepExpression().asText());
				
			}
			
			multistack.pop(varName);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Element[] elems = node.getElements();
			for(Element e : elems) {
				if(e instanceof ElementConstantDouble) {
					tmpStack.push(((ElementConstantDouble)e).getValue());
				} else if(e instanceof ElementConstantInteger) {
					tmpStack.push(((ElementConstantInteger)e).getValue());
				} else if(e instanceof ElementString) {
					tmpStack.push(((ElementString)e).getValue());
				} else if(e instanceof ElementVariable) {
					var variable = (ElementVariable)e;
					String varName = variable.getName();
					Object value = multistack.peek(varName).getValue();
					if(value==null) throw new RuntimeException("No such variable " + varName);
					tmpStack.push(value);
				} else if(e instanceof ElementOperator) {
					var operation = (ElementOperator)e;
					String operator = operation.getSymbol();
					operation(operator);
				} else if(e instanceof ElementFunction) {
					var function = (ElementFunction)e;
					String functionName = function.getName();
					SmartScriptFunctions.executeFunction(functionName, tmpStack, requestContext);
				}
			}
			
			for(Object o : tmpStack) {
				try {
					requestContext.write(o.toString());
				} catch (IOException e1) {
					System.err.println(e1.getCause() + " : " + e1.getMessage());
					System.exit(-1);
				}
			}
			
			tmpStack.clear();
		}
		
		/**
		 * Calculating the operation on numbers. Supported operations : +, -, *, /
		 * @param operator operator for the operation
		 */
		private void operation(String operator) {
			ValueWrapper operator1 = new ValueWrapper(tmpStack.pop());
			Object operator2 = tmpStack.pop();
			
			switch(operator) {
				case "+" :
					operator1.add(operator2);
					break;
				case "-" :
					operator1.subtract(operator2);
					break;
				case "*" :
					operator1.multiply(operator2);
					break;
				case "/" :
					operator1.divide(operator2);
					break;
			}
			
			tmpStack.push(operator1.getValue());
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			int children = node.numberOfChildren();
			for(int i=0; i<children; i++) {
				node.getChild(i).accept(this);;
			}
		}
	};
	
	/**
	 * Creates and initialises the engine
	 * @param documentNode root of the syntax tree
	 * @param requestContext context for returning the result
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = Objects.requireNonNull(documentNode);
		this.requestContext = Objects.requireNonNull(requestContext);
	}
	
	/**
	 * This method, upon call, will execute script whose syntax tree root is given to the constructor
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

}
