package com.example.db.entities.types;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntType implements Type, Serializable {
    private Integer data;

    public IntType() {
        this.data = 0;
    }
    public IntType(Integer data) { this.data = data; }

    @Override
    public TypeName getName() {
        return TypeName.INT;
    }

    @Override
    public Type setData(Object data) {
        if (data.getClass().equals(Integer.class)) {
            this.data = (int) data;
            return this;
        }
        if (data.getClass().equals(String.class)) {
            String check = (String) data;
            if (isValid(check)) {
                this.data = Integer.parseInt(check);
                return this;
            }
        }

        throw new WrongTypeException(getName());
    }

    private boolean isValid(String data) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }

    @Override
    public String getData() {
        return this.data.toString();
    }

    @Override
    public Class getViewClass() {
        return Integer.class;
    }

    @Override
    public int compareTo(Type o) {
        if (o.getName() != TypeName.INT) {
            throw new WrongTypeException(getName());
        }
        IntType t = (IntType) o;
        return this.data.compareTo(t.data);
    }
}
