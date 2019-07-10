package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

class UniqueNumbersTest {
	
	static TreeNode root;
	static TreeNode emptyTree;
	
	@BeforeAll
    static void setUp()
    {
		root = UniqueNumbers.addNode(root, 42);
		root = UniqueNumbers.addNode(root, 76);
		root = UniqueNumbers.addNode(root, 21);
		root = UniqueNumbers.addNode(root, 76);
		root = UniqueNumbers.addNode(root, 35);

    }
	
	@Test
	void testTreeCreation() {
		assertEquals(42, root.value);
		assertEquals(76, root.right.value);
		assertEquals(21, root.left.value);
		assertEquals(35, root.left.right.value);
	}
	
	@Test
	void testTreeSize() {
		assertEquals(4, UniqueNumbers.treeSize(root));
		assertEquals(0, UniqueNumbers.treeSize(emptyTree));
	}
	
	@Test
	void testTreeContainsValue() {
		assertEquals(true, UniqueNumbers.containsValue(root, 76));
		assertEquals(false, UniqueNumbers.containsValue(root, 90));
	}

}
