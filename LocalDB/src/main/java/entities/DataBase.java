package entities;

import lombok.Data;

import java.util.HashMap;

@Data
public class DataBase {
    private final String name;
    private HashMap<String, Table> tables;

    public DataBase(String name) {
        this.name = name;
        this.tables = new HashMap<>();
    }

    public void addTable(Table table) {
        this.tables.put(table.getName(), table);
    }

    public Table getTable(String name) {
        return this.tables.get(name);
    }

    public void deleteTable(String name) {
        this.tables.remove(name);
    }
}
