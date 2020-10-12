package com.example.db.entities;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Data
@Component
public class DataBase {
    private String name;
    private HashMap<String, Table> tables;

    public DataBase() {
        this.name = "";
        this.tables = new HashMap<>();
    }

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
