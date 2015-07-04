package com.mj.bplustree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.mj.db.serialization.SerDeserializer;

public class EmployeeSerDeser implements SerDeserializer<Employee> {

	public Employee read(DataInputStream dis) throws IOException {
		// TODO Auto-generated method stub
		int id = dis.readInt() ;
		String fn = dis.readUTF();
		String ln = dis.readUTF() ;
		
		return new Employee(id,fn,ln) ;
	}

	public void write(Employee value, DataOutputStream dos) throws IOException {
		// TODO Auto-generated method stub
		dos.writeInt(value.getId());
		dos.writeUTF(value.getFirstName());
		dos.writeUTF(value.getLastName()) ;
		
	}

}
