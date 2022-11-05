package com.mj.bplustree;

import java.util.List;

public class NodeBounds {

	public List low ;
	public List high ;
	public int blockpointer ;
	
	public NodeBounds(List l, List h, int b) {
		
		low = l ;
		high = h ;
		blockpointer = b ;
	}
	

}
