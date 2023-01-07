package com.mj.bplustree;

import com.mj.bplustree.fields.Field;
import com.mj.bplustree.fields.FieldType;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

		BPlusTree tree = BPlusTree.create(null, "floatindex.db",
				Arrays.asList("id"), tableSpec);

		try {

			tree.insert(Arrays.asList(4.1f));
			tree.insert(Arrays.asList(10.2f));
			tree.insert(Arrays.asList(20.3f));
			tree.insert(Arrays.asList(304f));
			tree.insert(Arrays.asList(15.5f));
			tree.insert(Arrays.asList(12.6f));
			tree.insert(Arrays.asList(17.7f));
			tree.insert(Arrays.asList(6.9f));
			tree.insert(Arrays.asList(25.11f));
			tree.insert(Arrays.asList(6.12f));


			tree.printTree();

			assertTrue(tree.isTreeValid());
		} finally {
			tree.close();
			Files.delete(Paths.get("floatindex.db"));
		}
	}


	@Test
	public void testInsert100() throws IOException {

		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("id", FieldType.decimal));

		BPlusTree tree = BPlusTree.create(null, "floatindex100.db",
				Arrays.asList("id"), tableSpec);

		try {

			for (int i = 1; i <= 100; i++) {

				float f = (float)(i*2.734);
				tree.insert(Arrays.asList(f));
			}

			tree.printTree();
			assertTrue(tree.isTreeValid());
		} finally {
			tree.close();
			Files.delete(Paths.get("floatindex100.db"));
		}
	}
	

	@Test
	public void testwriteandvalidate1000() throws IOException {

		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("id", FieldType.decimal));

		BPlusTree tree = BPlusTree.create(null, "floatindex1000.db",
				Arrays.asList("id"), tableSpec);

		try {

			for (int i = 1; i <= 1000; i++) {
				float f = (float)(i*3.317);
				tree.insert(Arrays.asList(f));
			}

			assertTrue(tree.isTreeValid());
			tree.printTree();
		} finally {
			tree.close();
			Files.delete(Paths.get("floatindex1000.db"));
		}
	}


	@Test
	public void testFind() throws IOException {

		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("id", FieldType.decimal));

		BPlusTree tree = BPlusTree.create(null, "floatindex1000.db",
				Arrays.asList("id"), tableSpec);
		BPlusTree tree2 = null ;

		try {
			for (int i = 1; i <= 1000; i++) {
				float f = (float)(i*3.317);
				tree.insert(Arrays.asList(f));
			}

			tree2 = BPlusTree.create(null, "floatindex1000.db",
					Arrays.asList("id"), tableSpec);

			float l = 872.371f;

			List ptr = tree2.find(Arrays.asList(l));

			System.out.println(ptr.get(0));

			assertTrue((float) ptr.get(0) == 872.371f);
		} finally {
			tree.close();
			tree2.close();
			File f = new File("floatindex1000.db");
			f.delete();
		}
		
	}

	@Test
	public void testDelete() throws IOException {
		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("id", FieldType.decimal));

		BPlusTree tree = BPlusTree.create(null, "floatindex100.db",
				Arrays.asList("id"), tableSpec);
		BPlusTree tree2 = null;
		try {

			for (int i = 1; i <= 100; i++) {
				float f = (float)(i*2.734);
				tree.insert(Arrays.asList(f));
			}

			tree2 = BPlusTree.create(null, "floatindex100.db",
					Arrays.asList("id"), tableSpec);

			assertTrue(tree2.isTreeValid());

			List ptr = tree2.find(Arrays.asList(65.616f));
			assertTrue((float) ptr.get(0) == 65.616f);

			tree2.printTree();
			tree2.delete(Arrays.asList(65.616f));

			assertTrue(tree2.isTreeValid());

			assertNull(tree2.find(Arrays.asList(65.616f)));
		} finally {
			tree.close();
			tree2.close();
			Files.delete(Paths.get("floatindex100.db"));
		}
	}


}
