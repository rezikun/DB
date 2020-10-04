package entities.types;

import java.io.Serializable;

public class IntType implements Type, Serializable, Comparable<IntType> {
    private Integer data;

    public IntType() {
        this.data = 0;
    }

    @Override
    public TypeName getName() {
        return TypeName.INT;
    }

    @Override
    public void setData(Object data) {
        if (!data.getClass().equals(Integer.class)) {
            throw new WrongTypeException(getName());
        }
        this.data = (int) data;
    }

    @Override
    public String getData() {
        return this.data.toString();
    }

    @Override
    public int compareTo(IntType o) {
        return this.data.compareTo(o.data);
    }
}
