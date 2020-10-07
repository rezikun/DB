package entities.types;

import java.io.Serializable;

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
    public void setData(Object data) {
        if (!data.getClass().equals(Double.class)) {
            throw new WrongTypeException(this.getName());
        }
        this.data = (Double) data;
    }

    @Override
    public String getData() {
        return data.toString();
    }

    @Override
    public int compareTo(Type o) {
        RealType t = (RealType) o;
        return this.data.compareTo(t.data);
    }
}
