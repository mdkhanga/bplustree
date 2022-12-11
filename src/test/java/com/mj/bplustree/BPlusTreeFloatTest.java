package com.mj.bplustree;

import com.mj.bplustree.fields.Field;
import com.mj.bplustree.fields.FieldType;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class BPlusTreeFloatTest {

	@Test
	public void testCreate() throws IOException {

		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("id", FieldType.decimal));

		try {


			BPlusTree tree = BPlusTree.create(null, "floatindex.db",
					Arrays.asList("id"), tableSpec);

			tree.insert(Arrays.asList(4.1));
			tree.insert(Arrays.asList(10.2));
			tree.insert(Arrays.asList(20.3));
			tree.insert(Arrays.asList(304));
			tree.insert(Arrays.asList(15.5));
			tree.insert(Arrays.asList(12.6));
			tree.insert(Arrays.asList(17.7));
			tree.insert(Arrays.asList(6.9));
			tree.insert(Arrays.asList(25.11));
			tree.insert(Arrays.asList(6.12));


			tree.printTree();

			assertTrue(tree.isTreeValid());
		} finally {
			File f = new File("floatindex.db");
			f.delete();
		}
	}

	
	/*
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
	*/

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
