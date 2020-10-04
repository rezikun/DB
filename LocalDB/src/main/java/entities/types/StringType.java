package entities.types;

import java.io.Serializable;

public class StringType implements Type, Serializable, Comparable<StringType> {
    private String data;

    public StringType() {
        this.data = "";
    }

    @Override
    public TypeName getName() {
        return TypeName.STRING;
    }

    @Override
    public void setData(Object data) {
        if (!data.getClass().equals(String.class)) {
            throw new WrongTypeException(this.getName());
        }
        this.data = data.toString();
    }

    @Override
    public String getData() {
        return this.data;
    }

    @Override
    public int compareTo(StringType o) {
        return this.data.compareTo(o.getData());
    }
}
