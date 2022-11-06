package com.mj.bplustree;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;

import com.mj.bplustree.fields.Field;
import com.mj.bplustree.fields.FieldType;
import org.junit.Test;

import com.mj.db.serialization.IntegerSerDeser;
import com.mj.bplustree.BPlusNode;
import com.mj.bplustree.BPlusTree;
import com.mj.util.IntegerComparator;

public class BPlusTreeIntegerTest {

	@Test
	public void testCreate() throws IOException {

		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("id", FieldType.integer));

		BPlusTree tree = new BPlusTree(null,"intindex.db",
				Arrays.asList("id"), tableSpec) ;
		
		
		tree.insert(Arrays.asList(4), Arrays.asList(4)) ;
		tree.insert(Arrays.asList(10), Arrays.asList(10)) ;
		tree.insert(Arrays.asList(20), Arrays.asList(20)) ;
		tree.insert(Arrays.asList(30), Arrays.asList(30)) ;
		tree.insert(Arrays.asList(15), Arrays.asList(15)) ;
		tree.insert(Arrays.asList(12), Arrays.asList(12)) ;
		tree.insert(Arrays.asList(17), Arrays.asList(17)) ;
		tree.insert(Arrays.asList(6), Arrays.asList(6)) ;
		tree.insert(Arrays.asList(25), Arrays.asList(25)) ;
		tree.insert(Arrays.asList(8), Arrays.asList(6)) ;


		tree.printTree() ;
		
		assertTrue(tree.isTreeValid()) ;
	}

	

	@Test
	public void testInsert100() throws IOException {

		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("id", FieldType.integer));
		
		BPlusTree tree = new BPlusTree(null,"intindex100.db",
				Arrays.asList("id"), tableSpec) ;

		
		for (int i = 1 ; i <=100 ; i++) {
			
			tree.insert(Arrays.asList(i), Arrays.asList(i)) ;
		}
		
		tree.printTree();
		assertTrue(tree.isTreeValid()) ;
		
		
	}
	

	@Test
	public void testwriteandvalidate1000() throws IOException {

		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("id", FieldType.integer));

		BPlusTree tree = new BPlusTree(null,"intindex1000.db",
				Arrays.asList("id"), tableSpec) ;
		
		for (int i = 1 ; i <=1000 ; i++) {
			
			System.out.println(i) ;
			
			tree.insert(Arrays.asList(i), Arrays.asList(i)) ;
		}
		
		// tree.printTree();
		
		// BPlusTree tree1 = new BPlusTree("C:\\mjprojects","index1000.db",4,128) ;
		
		assertTrue(tree.isTreeValid()) ;
		
	}

	@Test
	public void testFind() throws IOException {

		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("id", FieldType.integer));

		BPlusTree tree = new BPlusTree(null,"intindex1000.db",
				Arrays.asList("id"), tableSpec) ;
		
		// int l = 613819 ;
		int l = 153;
		
		List ptr = tree.find(Arrays.asList(l)) ;
		
		System.out.println(ptr.get(0)) ;
		
		assertTrue((int)ptr.get(0) == 153) ;
		
		
	}
	

	@Test
	public void testDelete() throws IOException {
		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("id", FieldType.integer));
		BPlusTree tree = new BPlusTree(null,"intindex100.db",
				Arrays.asList("id"), tableSpec) ;
		
		assertTrue(tree.isTreeValid()) ;
		
		tree.printTree();
		
		tree.delete(Arrays.asList(73));
		
		assertTrue(tree.isTreeValid()) ;
		
		assertNull(tree.find(Arrays.asList(73))) ;
		
		tree.printTree();
		
		
		
	}


	@Test
	public void printBlocks() throws IOException {

		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("id", FieldType.integer));
		BPlusTree tree = new BPlusTree(null,"intindex100.db",
				Arrays.asList("id"), tableSpec) ;
		BPlusNode node = tree.readFromDisk(0) ;
		
		node.printNode() ;
		
		BPlusNode node1 = tree.readFromDisk(1) ;
		node1.printNode() ;
		
		BPlusNode node2 = tree.readFromDisk(2) ;
		node2.printNode() ;


		/*
			BPlusNode node3 = tree.readFromDisk(3) ;
			node3.printNode() ;
		*/
	
	}

}
