package entities;

import entities.types.Mapper;
import entities.types.Type;
import entities.types.TypeFacade;
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
            this.columns.put(columns.get(i), i);
            this.columnsNameToClass.put(columns.get(i).getName(),columns.get(i));
        }
        this.rows = new ArrayList<>();
        this.addRow();
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
        List<Type> newRow = this.columns.keySet().stream()
                .map(column -> Mapper.typeNameToType(column.getType()))
                .collect(Collectors.toList());
        this.rows.add(newRow);
    }

    public void updateRow(Integer index, HashMap<String, Type> row){
        var currRow = this.rows.get(index);
        row.forEach((key, value) -> {
            var column = this.columnsNameToClass.get(key);
            var i = this.columns.get(column);
            currRow.set(i, value);
        });
        this.rows.set(index, currRow);
    }

    public void deleteRow(Integer index) {
        this.rows.remove(index.intValue());
    }

    public void sortByColumn(String columnName) {
        var column = this.columnsNameToClass.get(columnName);
        var index = this.columns.get(column);
        rows.sort(Comparator.comparing(o -> o.get(index)));
        Collections.sort(rows, new Comparator<List<Type>>() {
            @Override
            public int compare(List<Type> o1, List<Type> o2) {
                TypeFacade<o1.get(index).getClass()> t = new
                var first = o1.get(index).
            }
        });
    }
}
