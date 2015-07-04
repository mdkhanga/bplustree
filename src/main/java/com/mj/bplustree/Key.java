package com.mj.bplustree;


public interface Key {

    public void addVarchar(String name, String size) ;
    public void addVarchar(int position, String name, String size) ;

    public void addInteger(String name) ;
    public void addInteger(int position,String name) ;

    public void addFloat(String name) ;
    public void addFloat(int position, String name) ;

    public void addBoolean(String name) ;
    public void addBoolean(int position, String name) ;

}
