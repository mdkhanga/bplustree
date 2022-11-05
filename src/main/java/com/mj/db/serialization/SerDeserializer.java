package com.mj.db.serialization;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface SerDeserializer<T> {
	
	public  T read(DataInputStream dis) throws IOException ;
	
	public void write(T value, DataOutputStream dos) throws IOException ;

}
