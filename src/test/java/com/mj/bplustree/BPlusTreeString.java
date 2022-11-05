package com.mj.bplustree;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Random;

import org.junit.Test;

import com.mj.db.serialization.StringSerDeser;
import com.mj.util.StringComparator;


public class BPlusTreeString {
	
	public static char[] alpha = {'a','b','c','d','e','f','g','h','i','j',
								  'k','l','n','o','p','q','r','s','t','u',
								  'v','w','x','y','z' } ;

	/*
	@Test
	public void testCreate() throws IOException {
		BPlusTree<String> tree = new BPlusTree<String>(null,"strindex.db",12,128,
				new StringSerDeser(),new StringComparator()) ;
		
		tree.insert("Apple", 4) ;
		tree.insert("zip", 4) ;
		tree.insert("ball", 4) ;
		tree.insert("Tree", 4) ;
		tree.insert("small", 4) ;
		tree.insert("color", 4) ;
		tree.insert("hammer", 4) ;
		tree.insert("quorum", 4) ;
		
		tree.insert("dull",4) ;
		
		
		tree.isTreeValid() ;
		tree.printTree() ;
	}
	
	@Test
	public void testCreate20() throws IOException {

	BPlusTree<String> tree = new BPlusTree<String>(null,"strindex20.db",12,128,
			new StringSerDeser(),new StringComparator()) ;
	
		for (int i = 1 ; i <= 20 ; i++) {
			
			tree.insert(genRandomWord(),1) ; 
		}
	
	
	assertTrue(tree.isTreeValid()) ;
	tree.printTree() ;

	}

	@Test
	public void testCreateMil() throws IOException {

	BPlusTree<String> tree = new BPlusTree<String>(null,"strindexMil.db",12,128,
			new StringSerDeser(),new StringComparator()) ;
	
		for (int i = 1 ; i <= 1000 ; i++) {
			
			tree.insert(genRandomWord(),1) ; 
		}
	
	
	assertTrue(tree.isTreeValid()) ;
	tree.printTree() ;

	}
	
	@Test
	public void read() throws IOException {
		
		BPlusTree<String> tree = new BPlusTree<String>(null,"strindex20.db",12,128,
				new StringSerDeser(),new StringComparator()) ;
		
		assertTrue(tree.isTreeValid()) ;
		
	}
	
	// @Test
	public void find() throws IOException {
		
		BPlusTree<String> tree = new BPlusTree<String>(null,"strindexMil.db",12,128,
				new StringSerDeser(),new StringComparator()) ;
		
		// String k = tree.find("qvvuuea") ;
		// zsinvpjpw
		String k = tree.find("zsinvpjpw") ;
		
		assertTrue(k.contentEquals("zsinvpjpw")) ;
		
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
	*/
}
