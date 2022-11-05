package com.mj.bplustree;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

import com.mj.bplustree.fields.Field;
import com.mj.db.serialization.SerDeserializer;



/**
 * B+ tree
 * First implementation with integer key
 * @author manoj
 *
 */

@SuppressWarnings("unused")
public class BPlusTree {

	private final int BLOCK_SIZE = 1024 ; // bytes
	// private final int BLOCK_SIZE = 128 ;
	// private final int BLOCK_SIZE = 256 ;
	private final int VALUE_SIZE = 8 ; //long
	
	private int keysize ;
	
	private RandomAccessFile treeStore ;
	
	private BPlusNode root = null ;
	
	private int nextBlockPointer = 0 ;
	
	private boolean newtree = false ;
	
		private int M = 0 ;

	private List<String> keySpec = null ; // fieldnames
	private List<Field> tableSpec;
	private Map<String, Field> tableSpecMap = new HashMap<>() ; // fieldname , datatype

	private boolean isClustered = false ;
	
	private BPlusTree() {
		
		
	}
	
	
	public BPlusTree(String storeDir, String filename, int keysize, int recordsize, 
			        List<String> keySpec, List<Field> tableSpec) throws IOException {
		
		

		this.keySpec = keySpec;
		this.tableSpec = tableSpec;

		for (Field f: tableSpec) {
			tableSpecMap.put(f.getName(),f) ;
		}

		String fname = null ;
		
		if (storeDir != null && storeDir.length() > 0 ) {
			fname = storeDir + "\\" + filename ;
		} else {
			fname = filename ;
		}
		
		File f = new File(fname) ;
		
		if (f.exists()) {
			newtree = false ;
		} else
			newtree = true ;

		keysize = keysize ;
		
		M = (BLOCK_SIZE - 14)/( keysize + VALUE_SIZE) ;
		
		treeStore = new RandomAccessFile(fname,"rw") ;

		load() ;
	
	}

	public List<String> getKeySpec() {
		return keySpec;
	}

	public List<Field> getTableSpec() {
		return tableSpec;
	}

	public Map<String, Field> getTableSpecMap() {
		return tableSpecMap;
	}

	protected int getNumKeysPerBlock() {
		return M ;
	}
	

	public List find(List key) {
		
		return root.find(key) ;
	}
	
	public byte[] getNext() {
		return null ;
	}
	
	
	public void insert(List key, List value) {
		
		BPlusNode node = null ;
		
		if (root == null) {
			node = new BPlusNode(this) ;
			root = node ;
			node.setRoot(true) ;
			node.setLeaf(true) ;
		}
		
		BPlusNode newchild = root.insert(key, value) ;
		
		// if a child is returned , it means root was split
		// we need to create a new nonleaf root
		if (newchild != null) {
			
			BPlusNode newnode = new BPlusNode(this) ;
			newnode.setRoot(true) ;
			newnode.setLeaf(false) ;
			root.setRoot(false) ;
			root.moveBlock() ;
			newnode.addChildren(root,newchild) ;  
			// int promotedkey = newnode.getPromotedKey() ;
			// int ptr = newchild.getPointer() ;
			// newnode.insert(promotedkey, ptr) ;
			newnode.writetoDisk() ;
			
			root = newnode ;
		}
		
	}
	
	public void delete(List key) {
		
		root.delete(key) ;
	}
	
	public BPlusNode readFromDisk(int blockpointer) throws IOException {
		
				
		treeStore.seek(blockpointer*BLOCK_SIZE) ;
		
		
		int freeornot = treeStore.readByte() ;
		
		if (freeornot == 1) { // valid block
		
			byte[] b = new byte[BLOCK_SIZE-1] ;
		
			treeStore.readFully(b, 0, BLOCK_SIZE-1) ;
				
			return new BPlusNode(this,b,blockpointer) ;
		} 
			return null ;
	}
	
	public void writeToDisk(BPlusNode node) throws IOException {
		
		int pointer = node.getPointer() ;
		treeStore.seek(pointer*BLOCK_SIZE) ;
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream(BLOCK_SIZE) ;
		DataOutputStream ds = new DataOutputStream(bos) ;
		
		if (node.isLeaf())
			node.writeLeaf(ds) ;
		else
			node.writeNonLeaf(ds) ;
		
		int recsize = bos.size() ;
		int fill = BLOCK_SIZE-recsize ;
		
		for (int i = 1 ; i <= fill ;i ++) {
			
			bos.write(0) ;
		}
		   
		byte[] record = bos.toByteArray() ;
		
		if (record.length != BLOCK_SIZE) {
            node.printNode();
            throw new RuntimeException("rec size= " + record.length + " block size=" + BLOCK_SIZE);
        }
		
		
		treeStore.write(record) ;
		
		
	}
	
	
	
	
	public long getFilePointer() throws IOException {
		
		return treeStore.getFilePointer() ;
	}
	
	public int getNextBlockPointer() {
		
		int next = nextBlockPointer ;
		nextBlockPointer++ ;
		return next ;
	}
	
	public void decNextBlockPointer() {
		
		if (nextBlockPointer > 1)
			nextBlockPointer-- ;
	}
	
	public boolean isTreeValid() throws IOException {
		
				
		if (root == null) {
			System.out.println("Root is null. Tree is empty") ;
			return true ;
		}
		
			
		// Breadth first traverse
		
		ArrayDeque<NodeBounds> queue = new ArrayDeque<NodeBounds>() ;
		
		
		queue.addAll(root.getChildrenNodeBounds()) ;
		
		NodeBounds current = null ;
		while( !queue.isEmpty() && (current = queue.poll()) != null) {
			
			BPlusNode cNode = readFromDisk(current.blockpointer) ;
			
			if (cNode.isNodeValid(current.low, current.high))
				queue.addAll(cNode.getChildrenNodeBounds()) ;
			else
				return false ;
			
		}

		return true ;
	}
	
	
	
	public void printTree() throws IOException {
		
		
		if (root == null) {
			System.out.println("Root is null. Tree is empty") ;
			return ;
		}
		
	
		
		// Breadth first traverse
		
		ArrayDeque<Integer> queue = new ArrayDeque<Integer>() ;
		
		root.printNode() ;
		
		queue.addAll(root.getChildren()) ;
		

		
		Integer current = null ; 
		while( !queue.isEmpty() && (current = queue.poll()) != null) {
			
			BPlusNode cNode = readFromDisk(current) ;
			cNode.printNode() ;
			queue.addAll(cNode.getChildren()) ;
			
		}
		
	}
	
	private void load() throws IOException {
		
		try {
			
			long filesize = treeStore.length() ;
			int numblocks = (int)filesize/ this.BLOCK_SIZE ;
			
			nextBlockPointer = numblocks  ;
			root = readFromDisk(0) ;
			
		} catch(EOFException e) {
			root = null ;
		}
	}
	
	public void printBlock(int i) {

	}
	
}