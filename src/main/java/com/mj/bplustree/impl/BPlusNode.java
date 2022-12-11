package com.mj.bplustree.impl;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

import com.mj.bplustree.NodeBounds;
import com.mj.bplustree.fields.Field;
import com.mj.db.serialization.KeySerDeserializer;
import com.mj.db.serialization.RecordSerDeserializer;
import com.mj.util.KeyComparator;

public class BPlusNode {
	
	// private int M = 4 ; // maximum number of entries per node
	// 6/29/03 experiment with larger M
	private int Mleaf = 0 ;
	private int Mnonleaf=0;

	private int blockpointer ; // address within disk for this block
	
	// leaf node size is 1024 bytes
	// emp rec size 86 
	// 10 records = 860 bytes
	// 1 block ptrs to ad = 4 bytes
	
	//010513
	// make leaf node of 128 bytes
	// each data item is also 4 bytes
	// 10 records 40 bytes
	// 1 ptr to adjacent 4 bytes
	
	
	// non leaf node 128 bytes
	// has only keys
	// id is int is 4 bytes bytes
	//  10 keys per node = 40 bytes
	// 11 block pointers = 44 bytes
	// private int[] children = new int[11] ; // pointers to child Node blocks
	private List<Integer> children = new LinkedList<Integer>() ;
	
	// private int[] keys = new int[10] ; // 10 keys for non leaf nodes
	// use list instead
	private List<List> keys = new LinkedList<>() ; // 10 keys For now stick to primitive key ( not compound
	
	// private byte[][] data  = new byte[10][] ; // array of byte arrays 10 records for leaf nodes
	// for a non leaf node each byte[i][] contain key bytes
	// for a lead node each byte[i][] contains record bytes
	private List<List> data = new LinkedList() ; // 10 data items in a leaf node list of list of field values

	private boolean isLeaf = false ;
	private boolean isRoot = false ;
	
	private BPlusTreeImpl container = null ;
	
	// For Leaf node pointer to next sibling
	// BPlusNode next ;
	private int nextBlockPointer ; 
	
	// temp store for promoted key
	private List promotedKey ;
	private int[] promotedChildPtrs ;
	
	private List<Field> tableSpec ;

	private Map<String, Field> tableSpecMap;

	private Map<String, Integer> fieldPostionMap ;

	private List<String> keySpec ;

	private KeySerDeserializer keySerDeserializer ;
	private RecordSerDeserializer recordSerDeserializer ;

	private KeyComparator keyComparator;

	public BPlusNode(BPlusTreeImpl tree) {
		
		container = tree;
		blockpointer = container.getNextBlockPointer();
		keySpec = tree.getKeySpec();
		tableSpec = tree.getTableSpec();
		tableSpecMap = tree.getTableSpecMap();
		fieldPostionMap = tree.getFieldPositionMap();

		keySerDeserializer = new KeySerDeserializer(tableSpecMap, keySpec);
		recordSerDeserializer = new RecordSerDeserializer(tableSpec);
		keyComparator = new KeyComparator(keySpec, tableSpecMap);

		Mnonleaf = container.getNumKeysPerBlock() ;
		Mleaf = container.getNumRecordsPerBlock();
		
	}
	
	public BPlusNode(BPlusTreeImpl tree, byte[] b, int blockpointer) throws IOException {
		
		container = tree ;
		this.blockpointer = blockpointer ;
		keySpec = tree.getKeySpec() ;
		tableSpec = tree.getTableSpec() ;
		tableSpecMap = tree.getTableSpecMap();
		fieldPostionMap = tree.getFieldPositionMap();
		keySerDeserializer = new KeySerDeserializer(tableSpecMap, keySpec);
		recordSerDeserializer = new RecordSerDeserializer(tableSpec);
		keyComparator = new KeyComparator(keySpec, tableSpecMap);

		Mnonleaf = container.getNumKeysPerBlock() ;
		Mleaf = container.getNumRecordsPerBlock();
		
		if (blockpointer == 0)
			isRoot = true ;
		
		ByteArrayInputStream bis = new ByteArrayInputStream(b) ;
		DataInputStream dis = new DataInputStream(bis) ;
		
		readNode(dis) ;
		
	}
	
	public List find(List key) {
		
		int ptr = -1 ;
		
		if (!isLeaf()) {
			
			
			int ksize = keys.size();
			int i ;
			for (i = 0 ; i < ksize ;i++) {
				
				List k = keys.get(i) ;
				
				// if (key < k) {
				if (keyComparator.compare(key, k) < 0 ) {
					
					ptr = (Integer)children.get(i) ;
					// return key ;
					
					break ;
				}
				
				
			}
		
			if (ptr == -1)
				ptr = (Integer)children.get(i) ;
			

			BPlusNode nextNode = readFromDisk(ptr) ;
			
			return nextNode.find(key) ;
		
		
		}
		
		// In the leaf node
		
		int ksize = keys.size();
		int i ;
		for (i = 0 ; i < ksize ;i++) {
			
			List k = keys.get(i) ;
			
			// if (key < k) {
			if (keyComparator.compare(key, k) == 0 ) {
				
				// ptr = (Integer)children.get(i) ;
				// return key ;
				return data.get(i) ;
			}
			
			
		}
		
		
		return null ;
		
	}

	
	public void delete(List key) {
		
		int ptr = -1 ;
		
		if (!isLeaf()) {
			
			
			int ksize = keys.size();
			int i ;
			for (i = 0 ; i < ksize ;i++) {
				
				List k = keys.get(i) ;
				
				// if (key < k) {
				if (keyComparator.compare(key, k) < 0 ) {
					
					ptr = (Integer)children.get(i) ;
					// return key ;
					
				  break ;
				}
				
				
			}
		
			if (ptr == -1)
				ptr = (Integer)children.get(i) ;

			BPlusNode nextNode = readFromDisk(ptr) ;
			
			nextNode.delete(key) ;
			
			nextNode.writetoDisk();
			
			return ;
		
		}
		
		// In the leaf node
		
		int ksize = keys.size();
		int i ;
		
		for (i = 0 ; i < ksize ;i++) {
					
					List k = keys.get(i) ;
					
					// if (key < k) {
					if (keyComparator.compare(key, k) == 0 ) {
						
						// ptr = (Integer)children.get(i) ;
						
						keys.remove(i) ;
						data.remove(i) ;
						
						break ;
					}
		}
	}
	
	public boolean isLeaf() {
		return isLeaf ;
	}
	
	public boolean isRoot() {
		return isRoot ;
	}
	
	public void setRoot(boolean root) {
		
		isRoot = root ;
		
		if (isRoot == true) {
			// root must always be the first block
			blockpointer = 0 ;
			container.decNextBlockPointer() ;
			
		}
		
	}
	
	public void setLeaf(boolean leaf) {
		isLeaf = leaf ;
	}
	
	// return any node created as a result of spliting this node
	public BPlusNode insert( List value) {

		List key = keyFromRecord(value);

		if (!isLeaf()) {
			
			int ptr = -1 ;
			int ksize = keys.size();
			int i ;
			for (i = 0 ; i < ksize ;i++) {
				
				List k = keys.get(i) ;
				
				// if (key < k) {
				if (keyComparator.compare(key, k) < 0 ) {
					
					ptr = (Integer)children.get(i) ;
					break ;
				}
				
				
			}
			
			if (ptr == -1)
				ptr = (Integer)children.get(i) ;
			
			
			BPlusNode nextNode = readFromDisk(ptr) ;
			
			BPlusNode newchild = nextNode.insert(value) ; // on the way down to the leaf
			nextNode.writetoDisk() ;
			
			if (newchild == null)
				return null ;

			
			List skey = newchild.getPromotedKey() ;
			
			
			int[] pointers = newchild.getPromotedPointers() ;
			
			// newchild = insert(skey,sptr) ; // on the way up from leaf 
			newchild = insert(skey,pointers) ; // on the way up from leaf 
			
			return newchild ;
			
		}
		
		
		if (isLeaf()) {
			
			int size = keys.size() ;
			// if (size < M) {
				
				boolean foundpos = false ;
				for (int i = 0 ; i < size ; i++) {
					
					// int k = (Integer)keys.get(i) ;
					List k = keys.get(i) ;
					// if (key < k) {
					if (keyComparator.compare(key, k) < 0) {
						keys.add(i,key) ;
						data.add(i,value) ;
						// data.add(i,key) ;
						foundpos=true ;
						break ;
						
					// } else if (key == k) {
					} else if (keyComparator.compare(key, k) == 0) {
						//replace the value
						data.set(i, value) ;
						//data.set(i, key) ;
						foundpos=true ;
						break ;
					}
					
				}
				
				if (!foundpos) {
					// keys.add(size+1,key) ;
					keys.add(key) ; // just append to end
					// data.add(size+1,value) ; 
					data.add(value) ;
					// data.add(key) ;
					
				}
				
				
				size = keys.size() ;
				if (size <= Mleaf) {
				
					writetoDisk() ;
					return null ;
				} 
			
			// Leaf node is full
			// split the leaf Node
			
			// create a new leaf node
			BPlusNode newnode = new BPlusNode(container) ;
			newnode.setLeaf(true) ;
			
			// move last 5 of new list to new node
			int s_half_b = Mleaf/2  ;
			// int s_half_e = M -1 ;
			int s_half_e = size - 1 ;
			
			// for( int i = 5 ; i <= 9 ; i++) {
			for (int i = s_half_b ; i <= s_half_e ;i++) {
				List lkey = getKey(i) ;
				List ldata = getData(i) ;
				newnode.insert(ldata) ;
			}
			
			List promotedkey = getKey(s_half_b) ;
			
			// remove the keys/data that have been copied
			for (int i = s_half_e ; i >= s_half_b ;i--) {
				removeKey(i) ;
				removeData(i) ;		
			}
			
			// add link between this and new node
			nextBlockPointer = newnode.getPointer() ;
			newnode.setPromotedKey(promotedkey) ;
			
			int[] promotedpointers = new int[2] ;
			promotedpointers[0] = getPointer() ;
			promotedpointers[1] = newnode.getPointer() ;
			newnode.setPromotedPointers(promotedpointers) ;

			writetoDisk() ;
			newnode.writetoDisk() ;
			
			return newnode ;
			
			
		} // end isLeaf
		
		return null ;
		
	}
	
	
	// public BPlusNode insert(int key,int blockpointer) {
	public BPlusNode insert(List key, int[] blockpointer) {
		if (isLeaf()) {
			
			throw new RuntimeException("Method Applies only to Non Leaf nodes") ;
		}
		
		int size = keys.size() ;
		// if (size < M) {
			
			// insert key & ptr into write slot
			boolean foundpos = false ;
			for (int i = 0 ; i < size ; i++) {
				
				List k = keys.get(i) ;
				// if (key < k) {
				if (keyComparator.compare(key,k) < 0 ) {
					keys.add(i,key) ;
					children.add(i+1,blockpointer[1]) ;	
					foundpos=true ;
					break ;
					
				// } else if (key == k) {
				} else if (keyComparator.compare(key,k) == 0) {
					//replace the value
					
				//	children.set(i,blockpointer[0]) ;
					children.set(i+1, blockpointer[1]) ;
					foundpos=true ;
					break ;
				}
				
			}
			
			if (!foundpos) {
				// keys.add(size+1,key) ;
				keys.add(size,key) ;
			//	children.add(size,blockpointer[0]) ;
				children.add(size+1,blockpointer[1]) ;
			}
				
			size = keys.size() ;
			if (size <= Mnonleaf) {
				writetoDisk() ;
				return null ;
			}
			
		// }
		
		// split the node
		// create a new non leaf node
		// move half of new keys & pointers to newnode
		// return newNode

		BPlusNode newnode = new BPlusNode(container) ;
		newnode.setLeaf(false) ;
		
		// insert new key & pointer into position even through size is 10
		// we now have 11 key
		
		
		// move half of new list to new node
		int s_half_b = Mnonleaf/2   ;
		int s_half_e = size -1 ;
		
		for( int i = s_half_b+1 ; i <= s_half_e ; i++) {
			List lkey = getKey(i) ;
			newnode.appendKey(lkey) ;
		}
		
		// remove key from current node
		for( int i = s_half_e ; i >= s_half_b+1 ; i--) {
			removeKey(i) ;
		}
		
		
		for(int i = s_half_b+1 ; i <= s_half_e +1 ; i++) { // there is 1 more ptr than key
			newnode.appendChildPtr(getChildPtr(i)) ;
		}
		// remove child ptr from current node
		for(int i = s_half_e+1 ; i >= s_half_b+1; i--) { // there is 1 more ptr than key
			removeChildPtr(i) ;
		}
				
		// middle key at is pushed up
		int middle_idx = s_half_b;
		newnode.setPromotedKey(keys.get(middle_idx)) ;
		
		int[] promotedpointers = new int[2] ;
		promotedpointers[0] = getChildPtr(middle_idx) ;
		promotedpointers[1] = newnode.getPointer() ;
		newnode.setPromotedPointers(promotedpointers) ;
				
		keys.remove(middle_idx) ;
		// removeChildPtr(middle_idx) ;
				
		// add link between this and new node
		// nextBlockPointer = newnode.getPointer() ;
		newnode.writetoDisk() ;
		return newnode ;
		
	}
	
	
	public Object getSmallestKey() {
		return keys.get(0) ;
	}
	
	public int getPointer() {
		
		return blockpointer ;
	}
	
	public List<Integer> getChildren() {
		
		return children ;
	}
	
	public List<NodeBounds> getChildrenNodeBounds() {
		
		List<NodeBounds> clist = new ArrayList<NodeBounds>() ;
		int numchildren = children.size();
		
		for (int i =0 ; i < numchildren ; i++ ) {
			
			List low = new ArrayList() ;
			List high = new ArrayList() ;
			int ptr ;
			
			if (i == 0) {
				
				// low = Integer.MIN_VALUE ;
				high = keys.get(i) ;
			} else if (i == numchildren -1 ) {
				
				low = keys.get(i-1) ;
				// high = Integer.MAX_VALUE ;
			} else {
				
				low = keys.get(i-1) ;
				high = keys.get(i) ;
			}
			
			ptr = children.get(i) ;
			
			NodeBounds n = new NodeBounds(low,high,ptr) ;
			clist.add(n) ;
		}
		
		
		return clist ;
	}
	
	public void addChildren(BPlusNode oldroot, BPlusNode newchild) {
		
	
		if (!isRoot)
			throw new RuntimeException("Method should be called for root only!") ;
		
		List key = newchild.getPromotedKey() ;
		
		appendKey(key) ;
		
		int[] promotedPointers = newchild.getPromotedPointers() ;
		

		appendChildPtr(oldroot.getPointer()) ; // old root has moved so we need the new pointer
		appendChildPtr(promotedPointers[1]) ;

		
	}
	
	public List getKey(int index) {
		
		return keys.get(index) ;
	}
	
	public List get(List key) {
		
		// find the index ;
		int index = 0 ;
		for (List k : keys) {
			
			// if (k == key)
			if (keyComparator.compare(k, key) == 0)
				break ;
			
			++index ;
		}
		
		if (index == 10) // not found
			return null ;
		
		return data.get(index) ;
		
	}
	
	private void removeKey(int index) {
		
		keys.remove(index) ;
	}
	
	public int getChildPtr(int index) {
		
		return children.get(index) ;
	}
	
	private void removeChildPtr(int index) {
		children.remove(index) ;
	}
	
	
	public List getData(int index) {
		
		
		return data.get(index) ;
	}
	
	
	private void removeData(int index) {
		
		data.remove(index) ;
	}
	
	public void setPromotedKey(List key) {
		promotedKey = key ;
	}
	
	public List getPromotedKey() {
		return promotedKey ;
	}
	
	public void setPromotedPointers(int[] pointers) {
		promotedChildPtrs = pointers ;
	}
	
	public int[] getPromotedPointers() {
		return promotedChildPtrs ;
	}
	 
	public void appendKey(List key) {
		// add key to the end
		keys.add(key) ;
	}
	
	public void appendChildPtr(int p) {
		//add child ptr to the end
		children.add(p) ;
	}
	
	
	public void writetoDisk() {
		
		try {
		
			container.writeToDisk(this) ;
		} catch(IOException e) {
			
			// throw a domain exception
		}
		
	}
	
	private void readNode(DataInputStream ds) throws IOException {
		
		byte type = ds.readByte();
		
		if (type == 1)
			this.isLeaf = true ;
		else if (type == 0)
			this.isLeaf = false ;
		else
			throw new RuntimeException("first byte of the block is not 0 or 1. Invalid") ;
		
		
		if (isLeaf)
			readLeaf(ds) ;
		else
			readNonLeaf(ds) ;
		
	}
	
	private void readLeaf(DataInputStream ds) throws IOException {
		
		/* int numkeys = ds.readInt() ;
		
		for (int i = 0 ; i < numkeys ; i++) {
			
						
			// int key = ds.readInt() ;
			List key = keySerDeserializer.read(ds) ;

			keys.add(key) ;
		} */
		
		int numitems = ds.readInt() ;
		
		for (int i = 0 ; i < numitems ; i++) {
			
			// 100922 long dataitem = ds.readLong() ;
			// 100922 need tofix data.add(dataitem) ;
			List dataitem = recordSerDeserializer.read(ds);
			data.add(dataitem);
			List key = keyFromRecord(dataitem);
			keys.add(key);
		}
		
		this.nextBlockPointer = ds.readInt() ;
	}

	private List keyFromRecord(List dataitem) {

		List ret = new ArrayList();
		for (String fieldName: keySpec) {
			Object val = dataitem.get(fieldPostionMap.get(fieldName));
			ret.add(val);
		}
		return ret;
	}
	
	public void writeLeaf(DataOutputStream ds) throws IOException {
		
		// first byte indicate whether the block is used 1 or free 0
		ds.writeByte(1) ;
		
		// indicates that the node is a leaf node
		ds.writeByte(1) ;
		
		// write num of keys
		/* int knum = keys.size() ;
		ds.writeInt(knum) ;
		// write each key
		for (int i = 0 ; i < knum ; i++) {
			List val = keys.get(i) ;
			keySerDeserializer.write(val,ds) ;
		} */
		
		
		// write int num of data items
		int num = data.size();
		ds.writeInt(num) ;
		
		// write each data item
		for (int i = 0 ; i < num ; i++) {
			// long val = data.get(i) ;
			List val = data.get(i);
			// TODO fix 100922 write the list
			// ds.writeLong(val) ;
			recordSerDeserializer.write(val, ds);

		}
			
		// write next block pointer
		ds.writeInt(nextBlockPointer) ;
		
		
	}
	
	private void readNonLeaf(DataInputStream ds) throws IOException {
		
		int num_keys = ds.readInt() ;
		
		for (int i = 0 ; i < num_keys ; i++) {
			
			// int key = ds.readInt();
			List key = keySerDeserializer.read(ds) ;
			keys.add(key) ;
			
		}
		
		int num_childptrs = ds.readInt();
		
		for (int i = 0 ; i < num_childptrs ; i++) {
			
			int ptr = ds.readInt();
			children.add(ptr) ;
			
		}
		
	}
	
	public void writeNonLeaf(DataOutputStream ds) throws IOException {
		
		// first byte indicate whether the block is used 1 or free 0
		ds.writeByte(1) ;
		
		// indicates that the node is a non leaf node
		ds.writeByte(0) ;
		
		int num_keys = keys.size() ;
		
		ds.writeInt(num_keys) ;
		
		for(int i = 0 ; i < num_keys ; i++) {
			
			// Integer key = keys.get(i) ;
			List val = keys.get(i) ;
			// ds.writeInt(key.intValue()) ;
			keySerDeserializer.write(val,ds) ;
			
		}
		
		
		int num_childptrs = children.size() ;
		
		ds.writeInt(num_childptrs) ;
		
		for(int i = 0 ; i < num_childptrs ; i++ ) {
			
			Integer ptr = children.get(i) ;
			ds.writeInt(ptr.intValue()) ;
			
		}

	}

	
	public void moveBlock() {
		
		blockpointer = container.getNextBlockPointer() ; // change the block pointer to a new block
		writetoDisk() ;
		
	}
	
	
	
	public BPlusNode readFromDisk(int pointer)  {
		
		try {
		
			return container.readFromDisk(pointer) ;
		} catch (Exception e) {
			System.out.println(e) ;
		}
		
		return null ;
	}
	
	
	public boolean isNodeValid(List low, List high) {

		for (int i = 0 ; i < keys.size() ; i++) {
			List key = keys.get(i) ;
			
			if (keyComparator.compare(low, key) <=0 && keyComparator.compare(key, high) <= 0)
				continue ;
			else {
				System.out.println("Invalid node: low =" + low + " high=" + high + "key =" + key ) ;
				printNode() ;
				return false ;
			}	
		}
		
		return true ;
	}
	
	
	public void printNode() {
		
		System.out.println("---- Begin Node") ;
		
		System.out.println("BlockPointer : " + blockpointer) ;
		
		if (isRoot)
			System.out.println("Root \n") ;
		
		if (isLeaf) {
			System.out.println("Leaf ") ;
			System.out.println("data : " + keys.size()) ;
			StringBuilder sb = new StringBuilder() ;
			for (int i = 0 ; i < data.size() ; i++) {
				sb.append(data.get(i) + ",") ;
			}
			System.out.println(sb.toString()) ;

			System.out.println("--- End Node ") ;
			
			return ;
		
		}	
		else {
			
			System.out.println("Non leaf \n") ;
			System.out.println("Keys: "+ keys.size() + "keys \n") ;


			StringBuilder kb = new StringBuilder() ;
			for(int i =0 ; i < keys.size(); i++) {
				kb.append(keys.get(i)) ;
				kb.append(',') ;
			}
			
			System.out.println(kb.toString()) ;
			
			System.out.println("Child Pointers: \n") ;
			
			StringBuilder cb = new StringBuilder() ;
			for (int i =0 ; i < children.size(); i++ ) {
				cb.append(children.get(i)) ;
				cb.append(',') ;
			}
			
			System.out.println(cb.toString()) ;
			
		}
		
		System.out.println("--- End Node ") ;
		
		return  ;
		
		
	}

	// @Override
	public int compare(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
