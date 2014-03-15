package com.mj.bplustree;

public class NodeBounds<T> {

	public T low ;
	public T high ;
	public int blockpointer ;
	
	public NodeBounds(T l, T h, int b) {
		
		low = l ;
		high = h ;
		blockpointer = b ;
	}
	

}
