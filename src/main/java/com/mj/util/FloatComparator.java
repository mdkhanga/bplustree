package com.mj.util;
import java.util.Comparator;


public class FloatComparator implements Comparator<Float> {

	public int compare(Float o1, Float o2) {
		// TODO Auto-generated method stub
		if (o1 == null  && o2 == null)
			throw new NullPointerException() ;
		
		if (o1 != null && o2 == null)
			return -1 ;
		
		if (o1 == null && o2 != null)
			return -1 ;
		
		float f1 = o1.intValue() ;
		float f2 = o2.intValue() ;
		
		if (f1 == f2)
			return 0 ;
		else if (f1 < f2)
			return -1 ;
		else 
			return 1 ;

	}

}
