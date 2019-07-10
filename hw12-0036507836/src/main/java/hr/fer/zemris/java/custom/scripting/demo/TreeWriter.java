package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

public class TreeWriter {

	public static void main(String[] args) {
		
		String docBody;
		try {
			docBody = Files.readString(Paths.get("examples/osnovni.smscr"));
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return;
		}
		
		SmartScriptParser parser;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			return;
		}
		
		WriteVisitor visitor = new WriteVisitor();
		parser.getDocumentNode().accept(visitor);
		
	}
	
	private static class WriteVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			int children = node.numberOfChildren();
			System.out.print(node);
			for(int i=0; i<children; i++) {
				node.getChild(i).accept(this);;
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			int children = node.numberOfChildren();
			System.out.print(node);
			for(int i=0; i<children; i++) {
				node.getChild(i).accept(this);;
			}
			System.out.print("{$END$}");
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			int children = node.numberOfChildren();
			System.out.print(node);
			for(int i=0; i<children; i++) {
				node.getChild(i).accept(this);;
			}
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			int children = node.numberOfChildren();
			for(int i=0; i<children; i++) {
				node.getChild(i).accept(this);;
			}
		}
		
	}

}
