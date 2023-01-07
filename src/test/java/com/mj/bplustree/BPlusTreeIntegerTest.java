package com.mj.bplustree;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

		BPlusTree tree = BPlusTree.create(null, "intindex.db",
				Arrays.asList("id"), tableSpec);

		try {

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
			tree.close();
			Files.delete(Paths.get("intindex.db"));
		}
	}

	

	@Test
	public void testInsert100() throws IOException {

		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("id", FieldType.integer));

		BPlusTree tree = BPlusTree.create(null, "intindex100.db",
				Arrays.asList("id"), tableSpec);
		try {
			for (int i = 1; i <= 100; i++) {
				tree.insert(Arrays.asList(i));
			}

			tree.printTree();
			assertTrue(tree.isTreeValid());
		} finally {
			tree.close();
			Files.delete(Paths.get("intindex100.db"));
		}
	}
	

	@Test
	public void testwriteandvalidate1000() throws IOException {

		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("id", FieldType.integer));

		BPlusTree tree = BPlusTree.create(null, "intindex1000.db",
				Arrays.asList("id"), tableSpec);
		try {

			for (int i = 1; i <= 1000; i++) {
				System.out.println(i);
				tree.insert(Arrays.asList(i));
			}

			assertTrue(tree.isTreeValid());
		} finally {
			tree.close();
			Files.delete(Paths.get("intindex1000.db"));
		}
	}

	@Test
	public void testFind() throws IOException {

		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("id", FieldType.integer));

		BPlusTree tree = BPlusTree.create(null, "intindex1000.db",
				Arrays.asList("id"), tableSpec);

		BPlusTree tree2 = null;
		try {

			for (int i = 1; i <= 1000; i++) {
				tree.insert(Arrays.asList(i));
			}

			// int l = 613819 ;
			int l = 153;

			tree2 = BPlusTree.create(null, "intindex1000.db",
					Arrays.asList("id"), tableSpec);


			List ptr = tree2.find(Arrays.asList(l));

			System.out.println(ptr.get(0));

			assertTrue((int) ptr.get(0) == 153);
		} finally {
			tree.close();
			tree2.close();
			Files.delete(Paths.get("intindex1000.db"));
		}
		
	}
	

	@Test
	public void testDelete() throws IOException {
		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("id", FieldType.integer));

		BPlusTree tree = BPlusTree.create(null, "intindex100.db",
				Arrays.asList("id"), tableSpec);
		BPlusTree tree2 = null ;

		try {

			for (int i = 1; i <= 100; i++) {
				tree.insert(Arrays.asList(i));
			}

			tree2 = BPlusTree.create(null, "intindex100.db",
					Arrays.asList("id"), tableSpec);

			assertTrue(tree2.isTreeValid());

			tree2.printTree();

			tree2.delete(Arrays.asList(73));
			assertTrue(tree2.isTreeValid());
			assertNull(tree2.find(Arrays.asList(73)));
		} finally {
			tree.close();
			tree2.close();
			Files.delete(Paths.get("intindex100.db"));
		}
	}


}
