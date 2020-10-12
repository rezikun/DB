package com.example.db.entities.types;

import java.io.Serializable;

public interface Type extends Comparable<Type> {
    TypeName getName();
    Type setData(Object data);
    String getData();
    Class getViewClass();
}
