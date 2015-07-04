package com.mj.bplustree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.mj.db.serialization.SerDeserializer;

public class Employee   {
	
	private int id ;
	private String firstname ;
	private String lastname ;
	
	public Employee(int i , String fn, String ln) {
		id = i ;
		firstname= fn ;
		lastname = ln ;
	}

	public int getId() {
		return id ;
	}

	public String getFirstName() {
		return firstname ;
	}
	
	public String getLastName() {
		
		return lastname;
		
	}
	
	public String toString() {
		
		StringBuffer buf = new StringBuffer() ;
		
		buf.append(lastname) ;
		buf.append(' ') ;
		buf.append(firstname) ;
		buf.append(' ') ;
		buf.append(id) ;
		
		return buf.toString() ;
		
		
	}
}
