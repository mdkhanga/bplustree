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
public class StringSerDeser implements SerDeserializer<String> {
	
	public String read(DataInputStream dis) throws IOException {
		
		String key = dis.readUTF() ;
		return key ;
	}

	public void write(String value, DataOutputStream dos) throws IOException {
		
		dos.writeUTF(value) ;
		
	}
	
	
}
