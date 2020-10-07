package entities.types;

import java.io.Serializable;

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
    public int compareTo(Type o) {
        if (o.getName() != TypeName.INT) {
            throw new WrongTypeException(getName());
        }
        IntType t = (IntType) o;
        return this.data.compareTo(t.data);
    }
}
