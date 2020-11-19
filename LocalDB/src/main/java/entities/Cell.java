package entities;

import entities.types.StringType;
import entities.types.Type;
import lombok.Data;

import java.io.Serializable;

@Data
public class Cell implements Serializable {
    private Type data;

    public Cell() {
        this.data = new StringType();
    }

}
