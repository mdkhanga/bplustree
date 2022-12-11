package com.mj.bplustree;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.mj.bplustree.fields.Field;
import com.mj.bplustree.fields.FieldType;
import com.mj.bplustree.impl.BPlusNode;
import org.junit.Test;

public class BPlusTreeIntegerTest {

	@Test
	public void testCreate() throws IOException {

		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("id", FieldType.integer));

		try {


			BPlusTree tree = BPlusTree.create(null, "intindex.db",
					Arrays.asList("id"), tableSpec);

			tree.insert(Arrays.asList(4));
			tree.insert(Arrays.asList(10));
			tree.insert(Arrays.asList(20));
			tree.insert(Arrays.asList(30));
			tree.insert(Arrays.asList(15));
			tree.insert(Arrays.asList(12));
			tree.insert(Arrays.asList(17));
			tree.insert(Arrays.asList(6));
			tree.insert(Arrays.asList(25));
			tree.insert(Arrays.asList(6));


			tree.printTree();

			assertTrue(tree.isTreeValid());
		} finally {
			File f = new File("intindex.db");
			f.delete();
		}
	}

	

	@Test
	public void testInsert100() throws IOException {

		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("id", FieldType.integer));

		try {

			BPlusTree tree = BPlusTree.create(null, "intindex100.db",
					Arrays.asList("id"), tableSpec);

			for (int i = 1; i <= 100; i++) {

				tree.insert(Arrays.asList(i));
			}

			tree.printTree();
			assertTrue(tree.isTreeValid());
		} finally {
			File f = new File("intindex100.db");
			f.delete();
		}
	}
	

	@Test
	public void testwriteandvalidate1000() throws IOException {

		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("id", FieldType.integer));

		try {
			BPlusTree tree = BPlusTree.create(null, "intindex1000.db",
					Arrays.asList("id"), tableSpec);

			for (int i = 1; i <= 1000; i++) {

				System.out.println(i);

				tree.insert(Arrays.asList(i));
			}

			assertTrue(tree.isTreeValid());
		} finally {
			File f = new File("intindex1000.db");
			f.delete();
		}
	}

	@Test
	public void testFind() throws IOException {

		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("id", FieldType.integer));

		try {
			BPlusTree tree = BPlusTree.create(null, "intindex1000.db",
					Arrays.asList("id"), tableSpec);
			for (int i = 1; i <= 1000; i++) {
				tree.insert(Arrays.asList(i));
			}

			BPlusTree tree2 = BPlusTree.create(null, "intindex1000.db",
					Arrays.asList("id"), tableSpec);

			// int l = 613819 ;
			int l = 153;

			List ptr = tree2.find(Arrays.asList(l));

			System.out.println(ptr.get(0));

			assertTrue((int) ptr.get(0) == 153);
		} finally {
			File f = new File("intindex1000.db");
			f.delete();
		}
		
	}
	

	@Test
	public void testDelete() throws IOException {
		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("id", FieldType.integer));
		try {
			BPlusTree tree = BPlusTree.create(null, "intindex100.db",
					Arrays.asList("id"), tableSpec);

			for (int i = 1; i <= 100; i++) {
				tree.insert(Arrays.asList(i));
			}

			BPlusTree tree2 = BPlusTree.create(null, "intindex100.db",
					Arrays.asList("id"), tableSpec);
			assertTrue(tree2.isTreeValid());


			tree2.printTree();

			tree2.delete(Arrays.asList(73));

			assertTrue(tree2.isTreeValid());

			assertNull(tree2.find(Arrays.asList(73)));
		} finally {
			File f = new File("intindex100.db");
			f.delete();
		}
	}


	// @Test
	/*
	public void printBlocks() throws IOException {

		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("id", FieldType.integer));
		BPlusTreeImpl tree = new BPlusTreeImpl(null,"intindex100.db",
				Arrays.asList("id"), tableSpec) ;
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
