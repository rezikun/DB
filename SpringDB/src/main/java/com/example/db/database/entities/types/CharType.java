package com.example.db.database.entities.types;

import java.io.Serializable;

public class CharType implements Type, Serializable {
    private Character data;

    public CharType() {
        this.data = '-';
    }

    @Override
    public TypeName getName() {
        return TypeName.CHAR;
    }

    @Override
    public Type setData(Object data) {
        if (data.getClass().equals(Character.class)) {
            this.data = (Character) data;
            return this;
        }
        if (data.getClass().equals(String.class)) {
            String check = (String) data;
            if (isValid(check)) {
                this.data = check.charAt(0);
            }
        }
        throw new WrongTypeException(getName());
    }

    private boolean isValid(String data) {
        return data.length() <= 1;
    }

    @Override
    public String getData() {
        return this.data.toString();
    }

    @Override
    public int compareTo(Type o) {
        CharType t = (CharType) o;
        return this.data.compareTo(t.data);
    }

    @Override
    public Class getViewClass() {
        return Character.class;
    }
}
