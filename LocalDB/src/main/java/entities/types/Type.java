package entities.types;

import java.io.Serializable;

public interface Type extends Comparable<Type> {
    TypeName getName();
    void setData(Object data);
    String getData();
}
