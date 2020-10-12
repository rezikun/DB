package com.example.db.entities.types;

import java.io.Serializable;

public class StringType implements Type, Serializable{
    private String data;

    public StringType() {
        this.data = "";
    }
    public StringType(String data) { this.data = data;}

    @Override
    public TypeName getName() {
        return TypeName.STRING;
    }

    @Override
    public Type setData(Object data) {
        if (!data.getClass().equals(String.class)) {
            throw new WrongTypeException(this.getName());
        }
        this.data = data.toString();
        return this;
    }

    @Override
    public String getData() {
        return this.data;
    }

    @Override
    public Class getViewClass() {
        return String.class;
    }

    @Override
    public int compareTo(Type o) {
        return this.data.compareTo(o.getData());
    }
}
