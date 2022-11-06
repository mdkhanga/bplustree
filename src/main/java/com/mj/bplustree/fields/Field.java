package com.mj.bplustree.fields;

public class Field {
    private String name;
    private FieldType fieldType;
    private int len ; // applies to String only

    public Field(String n, FieldType ft, int l) {
        name = n;
        fieldType = ft;
        len = l;
    }

    public Field(String n, FieldType ft) {
        name = n;
        fieldType = ft;
    }

    public String getName() {
        return name ;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public int getLength() {
        return len ;
    }

    public int getSize() {

        switch(fieldType) {
            case integer:
                return 4;
            case string:
                return len*2;
            case bool:
                return 1;
            case decimal:
                return 4;
            default:
                return 8;

        }

    }
}
