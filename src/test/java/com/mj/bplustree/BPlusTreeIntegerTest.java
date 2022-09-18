package com.mj.bplustree;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.mj.db.serialization.IntegerSerDeser;
import com.mj.bplustree.BPlusNode;
import com.mj.bplustree.BPlusTree;
import com.mj.util.IntegerComparator;

public class BPlusTreeIntegerTest {

	@Test
	public void testCreate() throws IOException {
		BPlusTree<Integer> tree = new BPlusTree<Integer>(null,"intindex.db",4,128,
					new IntegerSerDeser(), new IntegerComparator()) ;
		
		
		tree.insert(4, 4) ;
		tree.insert(10, 10) ;
		tree.insert(20, 20) ;
		tree.insert(30, 30) ;
		
	    tree.insert(15, 15) ;
	    tree.insert(12, 12) ;
	    tree.insert(17, 17) ;
	    tree.insert(25, 25) ;
		
	    tree.insert(6,6) ;
	    tree.insert(8,8) ; 
	 	
		tree.printTree() ;
		
		assertTrue(tree.isTreeValid()) ;
	}

	
	/*
	@Test
	public void testInsert() throws IOException {
		
		BPlusTree tree = new BPlusTree("C:\\mjprojects","index.db",4,128) ;
		// tree.insert(9, 9) ;
		// tree.insert(11,11) ;
		// tree.insert(13,13) ;
		tree.insert(14,14) ;
		
		tree.printTree();
		
		
	} */
	
	@Test
	public void testInsert100() throws IOException {
		
		BPlusTree<Integer> tree = new BPlusTree<Integer>(null,"intindex100.db",4,128,
				new IntegerSerDeser(), new IntegerComparator()) ;
		// tree.insert(9, 9) ;
		// tree.insert(11,11) ;
		// tree.insert(13,13) ;
		
		for (int i = 1 ; i <=100 ; i++) {
			
			tree.insert(i, i) ;
		}
		
		tree.printTree();
		assertTrue(tree.isTreeValid()) ;
		
		
	}
	
	
	@Test
	public void testwriteandvalidate1000() throws IOException {
		
		BPlusTree<Integer> tree = new BPlusTree<Integer>(null,"intindexMil.db",4,128,
				new IntegerSerDeser(), new IntegerComparator()) ;
		
		for (int i = 1 ; i <=1000 ; i++) {
			
			System.out.println(i) ;
			
			tree.insert(i, i) ;
		}
		
		// tree.printTree();
		
		// BPlusTree tree1 = new BPlusTree("C:\\mjprojects","index1000.db",4,128) ;
		
		assertTrue(tree.isTreeValid()) ;
		
	}
	
	@Test
	public void testFind() throws IOException {
		
		BPlusTree<Integer> tree = new BPlusTree<Integer>(null,"intindexMil.db",4,128,
				new IntegerSerDeser(), new IntegerComparator()) ;
		
		// int l = 613819 ;
		int l = 153;
		
		long ptr = tree.find(l) ;
		
		System.out.println(ptr) ;
		
		assertTrue(ptr == 153) ;
		
		
	}
	
	
	@Test
	public void testDelete() throws IOException {
		BPlusTree<Integer> tree = new BPlusTree<Integer>(null,"intindex100.db",4,128,
				new IntegerSerDeser(), new IntegerComparator()) ;
		
		assertTrue(tree.isTreeValid()) ;
		
		tree.printTree();
		
		tree.delete(73);
		
		assertTrue(tree.isTreeValid()) ;
		
		assertNull(tree.find(73)) ;
		
		tree.printTree();
		
		
		
	}
	
	
	@Test
	public void testRead() throws IOException {
		BPlusTree<Integer> tree = new BPlusTree<Integer>(null,"intindex100.db",4,128,
				new IntegerSerDeser(), new IntegerComparator()) ;
		
		
		tree.printTree() ;
		
		assertTrue(tree.isTreeValid()) ;
	}
	
	@Test
	public void testValidate() throws IOException {
		BPlusTree<Integer> tree = new BPlusTree<Integer>(null,"intindex100.db",4,128,
				new IntegerSerDeser(), new IntegerComparator()) ;
		
		
		assertTrue(tree.isTreeValid()) ;
	}
	
	/*
	@Test
	public void printBlocks() throws IOException {
		
		BPlusTree tree = new BPlusTree(null","intindex.db",4,128) ;
		
		BPlusNode node = tree.readFromDisk(0) ;
		
		node.printNode() ;
		
		BPlusNode node1 = tree.readFromDisk(1) ;
		node1.printNode() ;
		
		BPlusNode node2 = tree.readFromDisk(2) ;
		node2.printNode() ;
		
		BPlusNode node3 = tree.readFromDisk(3) ;
		node3.printNode() ;
		
	
	} */
	
	

}
