package entities;

import entities.types.Type;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Row implements Serializable {
    private HashMap<String, Type> row;

    public Row () {
        this.row = new HashMap<>();
    }

    public HashMap<String, Type> addValue(String columnName, Type value) {
        row.put(columnName, value);
        return row;
    }
}
