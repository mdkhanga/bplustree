package com.mj.db.serialization;

import com.mj.bplustree.fields.Field;
import com.mj.bplustree.fields.FieldType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecordSerDeserializer {

    private List<Field> recordSpec;

    public RecordSerDeserializer(List<Field> spec) {
        recordSpec = spec;
    }


    public  List read(DataInputStream dis) throws IOException {
        
        List ret = new ArrayList() ;

        for (Field field : recordSpec) {

            FieldType type = field.getFieldType();

            if (type.equals(FieldType.integer)) {
                int v = dis.readInt();
                ret.add(v) ;
            } else {
                // String
                int len = dis.readInt();
                byte[] sbytes = new byte[len];
                dis.read(sbytes,0,len);
                ret.add(new String(sbytes, StandardCharsets.UTF_8));
            }

        }

        return ret ;
    }

    public void write(List values, DataOutputStream dos) throws IOException {

        int index = 0;
        for (Field field : recordSpec) {
            FieldType type = field.getFieldType();

            if (type.equals(FieldType.integer)) {
                dos.writeInt((int) values.get(index));
                index++;
            } else {
                // String
                int len = field.getLength();
                String val = (String) values.get(index);
                byte[] valBytes = val.getBytes(StandardCharsets.UTF_8);
                dos.writeInt(valBytes.length);
                dos.write(valBytes);
                index++;
            }

        }
    }
}
