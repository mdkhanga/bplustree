package com.mj.bplustree;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Random;

import org.junit.Test;

import com.mj.db.serialization.StringSerDeser;
import com.mj.util.StringComparator;

public class CompositeKeyEmployeeTest {
	
	public static char[] alpha = {'a','b','c','d','e','f','g','h','i','j',
		  'k','l','n','o','p','q','r','s','t','u',
		  'v','w','x','y','z' } ;

	/*
	@Test
	public void testBasicCreate() throws IOException {
		BPlusTree<Employee> tree = new BPlusTree<Employee>(null,"empindex.db",34,128, 
				new EmployeeSerDeser(),new EmployeeComparator()) ;
		
		tree.insert(new Employee(1,"Nori","Aoki"), 1);
		tree.insert(new Employee(2,"Joe","Panik"), 1);
		tree.insert(new Employee(3,"Angel","Pagan"), 1);
		tree.insert(new Employee(4,"Buster","Posey"), 1);
		tree.insert(new Employee(5,"Brandon","Belt"), 1);
		tree.insert(new Employee(6,"Brandon","Crawford"), 1);
		tree.insert(new Employee(7,"Joe","Maxwell"), 1);
		tree.insert(new Employee(8,"Matt","Duff"), 1);
		tree.insert(new Employee(9,"Tim","Lincicum"), 1);
		tree.insert(new Employee(10,"Santiago","Casilla"), 1);
		
		
		assertTrue(tree.isTreeValid()) ;
		
		tree.printTree();
	}
	
	
	@Test
	public void testCreate100() throws IOException {

		BPlusTree<Employee> tree = new BPlusTree<Employee>(null,"empindex100.db",38,128,
				new EmployeeSerDeser(),new EmployeeComparator()) ;
	
		for (int i = 1 ; i <= 1000 ; i++) {
			
			// if (i == 26) {
				System.out.println(i) ;
			// }
			tree.insert(new Employee(i,genRandomWord(10),genRandomWord(20)),1) ; 
		}
	
	
	assertTrue(tree.isTreeValid()) ;
	tree.printTree() ;

	}

private String genRandomWord(int maxsize) {
		
		Random r = new Random() ;
		
		int size = 0 ;
		
		while (size == 0)
		 size = r.nextInt(maxsize) ;
		
		StringBuilder b = new StringBuilder() ;
		
		for (int i = 0 ; i < size ; i++) {
			
			int next = r.nextInt(25) ;
			b.append(alpha[next]) ;
			
		}
		
		// System.out.println(b) ;
		return b.toString() ;
	}
	
	*/
	
}
