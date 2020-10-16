package com.example.db.database.entities;

import com.example.db.database.entities.types.Mapper;
import com.example.db.database.entities.types.Type;
import lombok.Data;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class Table implements Serializable {
    private String name;
    private List<List<Type>> rows;
    private Map<Column, Integer> columns;
    private Map<String, Column> columnsNameToClass;

    public Table (String name) {
        this.name = name;
        this.columns = new HashMap<>();
        this.columnsNameToClass = new HashMap<>();
        this.rows = new ArrayList<>();
    }

    public Table (String name, List<Column> columns) {
        this.name = name;
        this.columnsNameToClass = new HashMap<>();
        this.columns = new HashMap<>();
        for (int i = 0; i < columns.size(); i++) {
            System.out.println(columns.get(i) + " " + i);
            this.columns.put(columns.get(i), i);
            this.columnsNameToClass.put(columns.get(i).getName(),columns.get(i));
        }
        this.rows = new ArrayList<>();
        this.addRow();
    }

    public List<String> getColumnsName() { //sorted
        return this.columns.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(entry -> entry.getKey().getName())
                .collect(Collectors.toList());
    }

    public List<Column> getColumns() { //sorted
        return this.columns.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<Class> getViewTypes() {
        if (this.getRows().size() == 0) {
            throw new RuntimeException("Everything is bad with type view logic");
        }
        return this.rows.get(0)
                .stream()
                .map(Type::getViewClass)
                .collect(Collectors.toList());
    }

    public List<List<String>> getRows() {
        return this.rows.stream()
                .map(row -> row.stream()
                        .map(Type::getData)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public void addColumn (Column newColumn) { //TODO: test this
        if (columnsNameToClass.containsKey(newColumn.getName())) {
            throw new RuntimeException("Column with such name already exists");
        }
        this.columns.put(newColumn, columns.size());
        this.columnsNameToClass.put(newColumn.getName(), newColumn);
        this.rows = this.rows.stream()
                .peek(row -> row.add(Mapper.typeNameToType(newColumn.getType())))
                .collect(Collectors.toList());
    }

    public void updateColumnName (String oldColumnName, String newColumnName) {
        if (!this.columnsNameToClass.containsKey(oldColumnName)) {
            throw new RuntimeException("No such column exception");
        }
        //var oldColumn = this.columnsNameToClass.get(oldColumnName);
        //var index = this.columns.get(oldColumn);

        var oldColumn = this.columnsNameToClass.remove(oldColumnName);
        var index = this.columns.remove(oldColumn);

        Column newColumn = new Column(newColumnName, oldColumn.getType());
        this.columns.put(newColumn, index);
        this.columnsNameToClass.put(newColumnName, newColumn);
    }

    public void deleteColumn (String columnName) { // TODO: test this
        if (!this.columnsNameToClass.containsKey(columnName)) {
            throw new RuntimeException("No such column exception");
        }
        var column = this.columnsNameToClass.remove(columnName);
        var index = this.columns.remove(column);
        this.rows = this.rows.stream()
                .peek(row -> row.remove(index.intValue()))
                .collect(Collectors.toList());
    }

    public void addRow() {
        List<Type> newRow = this.columns.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .map(column -> Mapper.typeNameToType(column.getType()))
                .collect(Collectors.toList());
        this.rows.add(newRow);
    }

    public List<Type> updateRow(Integer index, HashMap<String, Type> row){
        var currRow = this.rows.get(index);
        row.forEach((key, value) -> {
            var column = this.columnsNameToClass.get(key);
            var i = this.columns.get(column);
            currRow.set(i, value);
        });
        return this.rows.set(index, currRow);
    }

    public void deleteRow(Integer index) {
        this.rows.remove(index.intValue());
    }

    public Table sortByColumn(String columnName) {
        var column = this.columnsNameToClass.get(columnName);
        var index = this.columns.get(column);
        rows.sort(Comparator.comparing(o -> o.get(index)));
        return this;
    }

    public Type getCellByRowAndColumn(Integer rowIndex, String columnName) {
        Integer columnIndex = this.columns.get(this.columnsNameToClass.get(columnName));
        return this.rows.get(rowIndex).get(columnIndex);
    }

    public Type setCellByRowAndColumn(Integer rowIndex, Integer columnIndex, Object value) {
        Type oldValue = this.rows.get(rowIndex).get(columnIndex);
        Type newValue = Mapper.typeNameToType(oldValue.getName()).setData(value);
        this.rows.get(rowIndex).set(columnIndex, newValue);
        return newValue;
    }

    public Integer getIndexByColumnName(String name) {
        return this.columns.get(this.columnsNameToClass.get(name));
    }

    public Column getColumnByName(String name) {
        return this.columnsNameToClass.get(name);
    }

    public Type getCellByString(String colName, String value) {
        Column col = this.getColumnByName(colName);
        return Mapper.typeNameToType(col.getType()).setData(value);
    }
}
