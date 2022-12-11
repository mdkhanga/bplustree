package com.mj.bplustree;

import com.mj.bplustree.fields.Field;
import com.mj.bplustree.impl.BPlusTreeImpl;

import java.io.IOException;
import java.util.List;

public interface BPlusTree {

    List find(List key);

    void insert(List value);

    void delete(List key);

    void printTree() throws IOException;

    boolean isTreeValid() throws IOException;

    static BPlusTree create(String storeDir, String filename, List<String> keySpec, List<Field> tableSpec) throws IOException {
        return new BPlusTreeImpl(storeDir, filename, keySpec, tableSpec);
    }

}
