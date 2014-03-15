/**
 * 
 */
package com.mj.db.serialization;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author manoj
 *
 */
public class IntegerSerDeser implements SerDeserializer<Integer> {
	
	public Integer read(DataInputStream dis) throws IOException {
		
		int key = dis.readInt() ;
		return key ;
	}

	public void write(Integer value, DataOutputStream dos) throws IOException {
		
		dos.writeInt(value) ;
		
	}
	
	
}
