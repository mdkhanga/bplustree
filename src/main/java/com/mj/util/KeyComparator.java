package com.mj.util;

import com.mj.bplustree.fields.Field;
import com.mj.bplustree.fields.FieldType;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class KeyComparator implements Comparator<List> {

    private Map<String, Field> tableSpec;
    private List<String> keySpec;

    private IntegerComparator integerComparator = new IntegerComparator();
    private StringComparator stringComparator = new StringComparator();

    public KeyComparator(List<String> kSpec, Map<String, Field> spec) {
        keySpec = kSpec;
        tableSpec = spec;
    }
    @Override
    public int compare(List o1, List o2) {
        int n1 = o1.size() ;
        int n2 = o2.size();

        if (n1 == 0 && n2 != 0 ) {
            return -1; // case where comparing low , key and low is null. -1 because low is < key
        } else if (n1 != 0 && n2 == 0) {
            return -1; // case when comparing key, high and high is null, -1 because high is < key
        } else if (n1 == 0 && n2 == 0) {
            return 0;
        }

        for (int i =0 ; i < n1 ; i++) {
           String fieldname = keySpec.get(i);
           FieldType type = tableSpec.get(fieldname).getFieldType();

           int res = 0 ;

           if (type.equals(FieldType.integer)) {
               res = integerComparator.compare((Integer)o1.get(i),(Integer)o2.get(i));
           } else if (type.equals(FieldType.string)) {
               res = stringComparator.compare((String)o1.get(i),(String)o2.get(i));
           }

           if (res != 0) {
               return res ;
           }
        }

        return 0;
    }


}
