package entities.types;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

public class IntIntervalType implements Type, Serializable, Comparable<IntIntervalType> {
    @Data
    @AllArgsConstructor
    class Interval implements Serializable{
        private Integer min;
        private Integer max;
    }

    private Interval data;

    public IntIntervalType() {
        this.data = new Interval(0, 0);
    }

    @Override
    public TypeName getName() {
        return TypeName.INT_INTERVAL;
    }

    @Override
    public void setData(Object data) {
        if (!data.getClass().equals(Interval.class)) {
            throw new WrongTypeException(this.getName());
        }
        var val = (Interval) data;
        if (val.min > val.max) {
            throw new RuntimeException("Minimum can`t be bigger than maximum");
        }
        this.data = val;
    }

    @Override
    public String getData() {
        return "[" + data.min.toString() + ", " + data.max.toString() + "]";
    }

    @Override
    public int compareTo(IntIntervalType o) { // compare by interval length
        Integer length1 = this.data.max - this.data.min;
        Integer length2 = o.data.max - this.data.min;
        return length1.compareTo(length2);
    }
}
