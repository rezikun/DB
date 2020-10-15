package com.example.db.database.entities.types;

public interface Type extends Comparable<Type> {
    TypeName getName();
    Type setData(Object data);
    String getData();
    Class getViewClass();
}
