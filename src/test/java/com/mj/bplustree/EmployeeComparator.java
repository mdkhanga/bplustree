package com.mj.bplustree;

import java.util.Comparator;

import com.mj.util.StringComparator;

public class EmployeeComparator implements Comparator<Employee> {

	StringComparator sc = new StringComparator() ;
	
	public int compare(Employee o1, Employee o2) {
		// TODO Auto-generated method stub

        if (o1 == null  && o2 == null)
            throw new NullPointerException() ;

        if (o1 != null && o2 == null)
            return -1 ;

        if (o1 == null && o2 != null)
            return -1 ;


		int c =sc.compare(o1.getLastName(), o2.getLastName()) ;
		
		if (c != 0)
			return c ;
		
		c = sc.compare(o1.getFirstName(), o2.getFirstName()) ;
		
		if (c != 0)
			return c ;
		
		return (o1.getId() - o2.getId());
		

	}

}
