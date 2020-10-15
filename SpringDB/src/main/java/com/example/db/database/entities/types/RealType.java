package com.example.db.database.entities.types;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RealType implements Type, Serializable {
    private Double data;

    public Double getRowData() {
        return this.data;
    }

    public RealType() {
        this.data = 0.;
    }

    @Override
    public TypeName getName() {
        return TypeName.REAL;
    }

    @Override
    public Type setData(Object data) {
        if (data.getClass().equals(Double.class)) {
            this.data = (Double) data;
            return this;
        }
        if (data.getClass().equals(String.class)) {
            String check = (String) data;
            if (isValid(check)) {
                this.data = Double.parseDouble(check);
                return this;
            }
        }
        throw new WrongTypeException(this.getName());
    }

    private boolean isValid(String data) {
        Pattern pattern = Pattern.compile("\\d+\\.\\d+");
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }

    @Override
    public String getData() {
        return data.toString();
    }

    @Override
    public Class getViewClass() {
        return Double.class;
    }

    @Override
    public int compareTo(Type o) {
        RealType t = (RealType) o;
        return this.data.compareTo(t.data);
    }
}
