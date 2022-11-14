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

public class CompositeKeyEmployeeTest {
	
	public static char[] alpha = {'a','b','c','d','e','f','g','h','i','j',
		  'k','l','n','o','p','q','r','s','t','u',
		  'v','w','x','y','z' } ;


	@Test
	public void testBasicCreate() throws IOException {

		List<Field> tableSpec = new ArrayList<>();

		tableSpec.add(new Field("id", FieldType.integer));
		tableSpec.add(new Field("firstname", FieldType.string, 10));
		tableSpec.add(new Field("lastname", FieldType.string, 10));
		tableSpec.add(new Field("salary", FieldType.integer));


		BPlusTree tree = new BPlusTree(null,"empindex.db",
				Arrays.asList("lastname", "firstname"), tableSpec) ;
		
		tree.insert(Arrays.asList("Aoki","Nori"), Arrays.asList(1,"Nori","Aoki",5000));
		tree.insert(Arrays.asList("Panik","Joe"), Arrays.asList(2,"Joe","Panik",5200));
		tree.insert(Arrays.asList("Pagan","Angel"), Arrays.asList(3,"Angel","Pagan",5400));
		tree.insert(Arrays.asList("Posey","Buster"), Arrays.asList(4,"Buster","Posey",5600));
		tree.insert(Arrays.asList("Belt","Brandon"), Arrays.asList(5,"Brandon","Belt",5800));
		tree.insert(Arrays.asList("Crawford","Brandon"), Arrays.asList(6,"Brandon","Crawford",6000));
		tree.insert(Arrays.asList("Maxwell","Joe"), Arrays.asList(7,"Joe","Maxwell",6200));
		tree.insert(Arrays.asList("Duff","Matt"), Arrays.asList(8,"Matt","Duffy",6275));
		tree.insert(Arrays.asList("Maxwell","Joe"), Arrays.asList(9,"Joe","Maxwell",6348));
		tree.insert(Arrays.asList("Lincicum","Tim"), Arrays.asList(10,"Tim","Lincicum",6493));
		tree.insert(Arrays.asList("Casilla","Santiago"), Arrays.asList(11,"Santiago","Casilla",6777));


		assertTrue(tree.isTreeValid()) ;
		
		tree.printTree();
	}
	

	@Test
	public void testCreate100() throws IOException {

		List<Field> tableSpec = new ArrayList<>();

		tableSpec.add(new Field("id", FieldType.integer));
		tableSpec.add(new Field("firstname", FieldType.string, 10));
		tableSpec.add(new Field("lastname", FieldType.string, 10));
		tableSpec.add(new Field("salary", FieldType.integer));

		BPlusTree tree = new BPlusTree(null,"empindex100.db",
				Arrays.asList("lastname", "firstname"), tableSpec) ;
	
		for (int i = 1 ; i <= 1000 ; i++) {
			
			String firstname = genRandomWord(10);
			String lastname = genRandomWord(20);

			tree.insert(Arrays.asList(lastname, firstname), Arrays.asList(i, firstname, lastname, 21*i)) ;
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
	

	
}
