

import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

public class MINIMALNI_UVJET3 {

	public static void main(String[] args) {
		String doc = "This is sample text.\r\n" + 
				"{$ FOR i 1 10 1 $}\r\n" + 
				" This is {$= i $}-th time this message is generated.\r\n" + 
				"{$END$}\r\n" + 
				"{$FOR i 0 10 2 $}\r\n" + 
				" sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n" + 
				"{$END$}";
		
		SmartScriptParser parser = null;
		try {
		 parser = new SmartScriptParser(doc);
		} catch(SmartScriptParserException e) {
		 System.out.println("Unable to parse document!");
		 System.exit(-1);
		} catch(Exception e) {
		 System.out.println("If this line ever executes, you have failed this class!");
		 System.exit(-1);
		}
		String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(parser.getDocumentNode());
		//System.out.println(originalDocumentBody); // should write something like original
		 											// content of docBody
		
		DocumentNode document = parser.getDocumentNode();
		//DocumentNode document = new SmartScriptParser(originalDocumentBody).getDocumentNode();
		
		if(document.numberOfChildren()!=4) System.out.println("FAILED!");
		if(!(document.getChild(0) instanceof TextNode)) System.out.println("FAILED!");
		if(!(document.getChild(1) instanceof ForLoopNode)) System.out.println("FAILED!");
		if(!(((ForLoopNode)document.getChild(1)).getVariable() instanceof ElementVariable)) System.out.println("FAILED");
		if(!(((ForLoopNode)document.getChild(1)).getStartExpression() instanceof ElementConstantInteger)) System.out.println("FAILED");
		if(!(((ForLoopNode)document.getChild(1)).getEndExpression() instanceof ElementConstantInteger)) System.out.println("FAILED");
		if(!(((ForLoopNode)document.getChild(1)).getStepExpression() instanceof ElementConstantInteger)) System.out.println("FAILED");
		if(!(document.getChild(2) instanceof TextNode)) System.out.println("FAILED!");
		if(!(document.getChild(3) instanceof ForLoopNode)) System.out.println("FAILED!");
		if(!(((ForLoopNode)document.getChild(3)).getStartExpression() instanceof ElementConstantInteger)) System.out.println("FAILED");
		if(!(((ForLoopNode)document.getChild(3)).getEndExpression() instanceof ElementConstantInteger)) System.out.println("FAILED");
		if(!(((ForLoopNode)document.getChild(3)).getStepExpression() instanceof ElementConstantInteger)) System.out.println("FAILED");
		
		if(document.getChild(1).numberOfChildren()!=3) System.out.println("FAILED!");
		if(!(document.getChild(1).getChild(0) instanceof TextNode)) System.out.println("FAILED!");
		if(!(document.getChild(1).getChild(1) instanceof EchoNode)) System.out.println("FAILED!");
		if(((EchoNode)document.getChild(1).getChild(1)).getElements().length!=1) System.out.println("FAILED");
		if(!(((EchoNode)document.getChild(1).getChild(1)).getElements()[0] instanceof ElementVariable)) System.out.println("FAILED");
		if(!(document.getChild(1).getChild(2) instanceof TextNode)) System.out.println("FAILED!");
		
		if(document.getChild(3).numberOfChildren()!=5) System.out.println("FAILED!");
		if(!(document.getChild(3).getChild(0) instanceof TextNode)) System.out.println("FAILED!");
		if(!(document.getChild(3).getChild(1) instanceof EchoNode)) System.out.println("FAILED!");
		if(((EchoNode)document.getChild(3).getChild(1)).getElements().length!=1) System.out.println("FAILED");
		if(!(((EchoNode)document.getChild(3).getChild(1)).getElements()[0] instanceof ElementVariable)) System.out.println("FAILED");
		if(!(document.getChild(3).getChild(2) instanceof TextNode)) System.out.println("FAILED!");
		if(!(document.getChild(3).getChild(3) instanceof EchoNode)) System.out.println("FAILED!");
		if(((EchoNode)document.getChild(3).getChild(3)).getElements().length!=6) System.out.println("FAILED");
		if(!(((EchoNode)document.getChild(3).getChild(3)).getElements()[0] instanceof ElementVariable)) System.out.println("FAILED");
		if(!(((EchoNode)document.getChild(3).getChild(3)).getElements()[1] instanceof ElementVariable)) System.out.println("FAILED");
		if(!(((EchoNode)document.getChild(3).getChild(3)).getElements()[2] instanceof ElementOperator)) System.out.println("FAILED");
		if(!(((EchoNode)document.getChild(3).getChild(3)).getElements()[3] instanceof ElementFunction)) System.out.println("FAILED");
		if(!(((EchoNode)document.getChild(3).getChild(3)).getElements()[4] instanceof ElementString)) System.out.println("FAILED");
		if(!(((EchoNode)document.getChild(3).getChild(3)).getElements()[5] instanceof ElementFunction)) System.out.println("FAILED");
		if(!(document.getChild(3).getChild(4) instanceof TextNode)) System.out.println("FAILED!");
	}
	
}
