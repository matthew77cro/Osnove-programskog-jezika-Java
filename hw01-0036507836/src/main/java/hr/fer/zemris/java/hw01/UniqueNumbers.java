package hr.fer.zemris.java.hw01;

import java.util.NoSuchElementException;
import java.util.OptionalInt;
import java.util.Scanner;

/**
 * 
 * This program has no console arguments. As input, it receives whole numbers and warns you when
 * the input number is already in the list (has already been entered). Program will quit
 * if value entered in console input is "kraj".
 * 
 * @author Matija Bačić
 *
 */
public class UniqueNumbers {
	
	private static final String TERMINATE_KEYWORD = "kraj";
	
	static class TreeNode{
		TreeNode left;
		TreeNode right;
		int value;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		TreeNode root = null;
		
		while(true) {
			OptionalInt input;
			try {
				input = nextIntegerConsoleInput("Unesite broj > ", "'%s' nije cijeli broj.%n", sc);
			}catch (NoSuchElementException ex) {
				break;
			}
			int number;
			if(input.isPresent()) number = input.getAsInt();
			else continue;
			root = checkUnique(root, number);
		}
		
		System.out.print("Ispis od najmanjeg: ");
		printAscending(root);
		System.out.println();
		System.out.print("Ispis od najvećeg: ");
		printDescending(root);
		
		sc.close();
	}
	
	//checks if number represented by string s is unique only if string is parseable to int
	private static TreeNode checkUnique(TreeNode root, int number) {
		//check for uniqueness
		if(containsValue(root, number)) {
			System.out.println("Broj već postoji. Preskačem.");
			return root;
		} else {
			System.out.println("Dodano.");
			return addNode(root, number);
		}
	}
	
	
	/**
	 * Adds a node with given value to a binary tree with given root.
	 * Returns new node with given value if root is null.
	 * @param root root of the binary tree
	 * @param value value of a new node to be added to the binary tree
	 * @return new TreeNode if tree is empty (root is null), root otherwise
	 */
	public static TreeNode addNode(TreeNode root, int value) {
		TreeNode newNode = createNewNode(value, null, null);
		
		if(root==null) {
			return newNode;
		}
		
		TreeNode temporaryNode = root;
		while(true) {
			if(temporaryNode.value==value) break;
			if(value<temporaryNode.value && temporaryNode.left==null) {temporaryNode.left = newNode; break;}
			if(value>temporaryNode.value && temporaryNode.right==null) {temporaryNode.right = newNode; break;}
			if(value<temporaryNode.value) {temporaryNode = temporaryNode.left; continue;}
			if(value>temporaryNode.value) {temporaryNode = temporaryNode.right; continue;}
		}
		return root;
	}
	
	/**
	 * Calculates number of nodes in a binary tree whose root is
	 * parameter of this method. Returns 0 if given
	 * parameter is null (tree has no nodes).
	 * @param root root of the binary tree
	 * @return number of nodes in a binary tree
	 */
	public static int treeSize(TreeNode root) {
		if(root==null) return 0;
		int left = treeSize(root.left);
		int right = treeSize(root.right);
		return left+right+1;
	}
	
	/**
	 * Tests whether given value is in a binary tree with given root. If
	 * null is given as a first parameter, returns false (tree is empty).
	 * @param root root of the binary tree
	 * @param value value for which to check the binary tree
	 * @return true if value is in given tree, false otherwise
	 */
	public static boolean containsValue(TreeNode root, int value) {
		if(root==null) return false;
		if(root.value==value) return true;
		boolean left = containsValue(root.left, value);
		boolean right = containsValue(root.right, value);
		return left||right;
	}
	
	//This method returns next integer that user entered via console input
	private static OptionalInt nextIntegerConsoleInput(String inputMsg, String errorMsg, Scanner sc) {
		String in = nextConsoleInput(inputMsg, sc);
		
		if(checkForTerminate(in,TERMINATE_KEYWORD)) throw new NoSuchElementException("Terminate key word detected");
		
		int input;
		try {
			input = Integer.parseInt(in);
		} catch (NumberFormatException ex) {
			System.out.printf(errorMsg, in);
			return OptionalInt.empty();
		}
		
		return OptionalInt.of(input);
	}
	
	//Returns next input user entered via console as a string, null if scanner does not have next element
	private static String nextConsoleInput(String inputMsg, Scanner sc) {
		System.out.print(inputMsg);
		if(!sc.hasNext()) return null;
		String input = sc.next();
		return input;
	}
	
	//checks whether string s is terminate keyword
	private static boolean checkForTerminate(String s, String terminateKeyword) {
		if(s.equals(terminateKeyword)) return true;
		return false;
	}
	
	private static void printAscending(TreeNode root) {
		if(root == null) return;
		printAscending(root.left);
		System.out.print(root.value + " ");
		printAscending(root.right);
	}
	
	private static void printDescending(TreeNode root) {
		if(root == null) return;
		printDescending(root.right);
		System.out.print(root.value + " ");
		printDescending(root.left);
	}
	
	private static TreeNode createNewNode(int value, TreeNode left, TreeNode right) {
		TreeNode temporaryNode = new TreeNode();
		temporaryNode.value = value;
		temporaryNode.left = left;
		temporaryNode.right = right;
		return temporaryNode;
	}

}
