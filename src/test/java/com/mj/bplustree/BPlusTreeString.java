package com.mj.bplustree;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.mj.bplustree.fields.Field;
import com.mj.bplustree.fields.FieldType;
import org.junit.Test;

import com.mj.db.serialization.StringSerDeser;
import com.mj.util.StringComparator;


public class BPlusTreeString {
	
	public static char[] alpha = {'a','b','c','d','e','f','g','h','i','j',
								  'k','l','n','o','p','q','r','s','t','u',
								  'v','w','x','y','z' } ;


	@Test
	public void testCreate() throws IOException {

		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("name", FieldType.string, 10));

		BPlusTree tree = new BPlusTree(null,"strindex.db",
				Arrays.asList("name"), tableSpec) ;

		
		tree.insert(Arrays.asList("Apple"), Arrays.asList("Apple")) ;
		tree.insert(Arrays.asList("zip"), Arrays.asList("zip")) ;
		tree.insert(Arrays.asList("ball"), Arrays.asList("ball")) ;
		tree.insert(Arrays.asList("Tree"), Arrays.asList("Tree")) ;
		tree.insert(Arrays.asList("small"), Arrays.asList("small")) ;
		tree.insert(Arrays.asList("color"), Arrays.asList("color")) ;
		tree.insert(Arrays.asList("hammer"), Arrays.asList("hammer")) ;
		tree.insert(Arrays.asList("quorum"), Arrays.asList("quorum")) ;
		tree.insert(Arrays.asList("dull"), Arrays.asList("dull")) ;

		tree.isTreeValid() ;
		tree.printTree() ;
	}


	@Test
	public void testCreate20() throws IOException {

		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("name", FieldType.string, 10));

		BPlusTree tree = new BPlusTree(null,"strindex20.db",
				Arrays.asList("name"), tableSpec) ;
	
		for (int i = 1 ; i <= 20 ; i++) {

			String t = genRandomWord();
			tree.insert(Arrays.asList(t),Arrays.asList(t)) ;
		}
	
	
	assertTrue(tree.isTreeValid()) ;
	tree.printTree() ;

	}


	@Test
	public void testCreate1000() throws IOException {

		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("name", FieldType.string, 10));

		BPlusTree tree = new BPlusTree(null,"strindex1000.db",
				Arrays.asList("name"), tableSpec) ;
	
		for (int i = 1 ; i <= 1000 ; i++) {

			String t = genRandomWord();
			tree.insert(Arrays.asList(t), Arrays.asList(t)) ;
		}
	
	
	assertTrue(tree.isTreeValid()) ;
	tree.printTree() ;

	}



	@Test
	public void find() throws IOException {
		List<Field> tableSpec = new ArrayList<>();
		tableSpec.add(new Field("name", FieldType.string, 10));

		BPlusTree tree = new BPlusTree(null,"strindex.db",
				Arrays.asList("name"), tableSpec) ;
		
		// String k = tree.find("qvvuuea") ;
		// zsinvpjpw
		List k = tree.find(Arrays.asList("small")) ;
		String s = (String)k.get(0);
		assertTrue(s.contentEquals("small")) ;
		
	}

	private String genRandomWord() {
		
		Random r = new Random() ;
		
		int size = 0 ;
		
		while (size == 0)
		 size = r.nextInt(10) ;
		
		StringBuilder b = new StringBuilder() ;
		
		for (int i = 0 ; i < size ; i++) {
			
			int next = r.nextInt(25) ;
			b.append(alpha[next]) ;
			
		}
		
		System.out.println(b) ;
		return b.toString() ;
	}

}
