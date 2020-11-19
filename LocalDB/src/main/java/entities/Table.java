package entities;

import entities.types.EnumType;
import entities.types.Mapper;
import entities.types.Type;
import entities.types.TypeName;
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

    public Table(String name, List<List<Type>> rows, List<Column> columns) {
        this.rows = rows;
        this.name = name;
        this.columnsNameToClass = new HashMap<>();
        this.columns = new HashMap<>();
        for (int i = 0; i < columns.size(); i++) {
            System.out.println(columns.get(i) + " " + i);
            this.columns.put(columns.get(i), i);
            this.columnsNameToClass.put(columns.get(i).getName(),columns.get(i));
        }
    }

    public List<String> getColumnsName() { //sorted
        return this.columns.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(entry -> entry.getKey().getName())
                .collect(Collectors.toList());
    }

    public List<Column> getColumns() { // sorted
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

        Column newColumn = new Column(newColumnName, oldColumn.getType(), null);
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
                .map(column -> {
                    if (column.getType() == TypeName.ENUM) {
                        return new EnumType(column.getEnumString());
                    }
                    return Mapper.typeNameToType(column.getType());})
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

    public void sortByColumn(String columnName) {
        var column = this.columnsNameToClass.get(columnName);
        var index = this.columns.get(column);
        rows.sort(Comparator.comparing(o -> o.get(index)));
    }

    public void sortByColumnIndex(Integer columnIndex) {
        rows.sort(Comparator.comparing(o -> o.get(columnIndex)));
    }

    public Type getCellByRowAndColumn(Integer rowIndex, String columnName) {
        Integer columnIndex = this.columns.get(this.columnsNameToClass.get(columnName));
        return this.rows.get(rowIndex).get(columnIndex);
    }

    public Type setCellByRowAndColumn(Integer rowIndex, Integer columnIndex, Object value) {
        Type newValue = this.rows.get(rowIndex).get(columnIndex).setData(value);
        // Type newValue = Mapper.typeNameToType(oldValue.getName()).setData(value);
        this.rows.get(rowIndex).set(columnIndex, newValue);
        return newValue;
    }

    public static Table subtract(Table left, Table right) {
        if (left.columnSize() != right.columnSize()) {
            throw new IllegalArgumentException("Dimensions mismatch.");
        }
        var leftColumns = left.getColumns();
        var rightColumns = right.getColumns();
        for (int i = 0; i < leftColumns.size(); ++i) {
            if (leftColumns.get(i).getType() != rightColumns.get(i).getType()) {
                throw new IllegalArgumentException("Types mismatch.");
            }
        }
        int minRowsNumber = Math.min(left.rows.size(), right.rows.size());
        List<List<Type>> subtractionRows = new ArrayList<>();
        for (int i = 0; i < minRowsNumber; ++i) {
            subtractionRows.add(new ArrayList<>());
            boolean exactlyTheSame = true;
            for (int j = 0; j < leftColumns.size(); ++j) {
                if (!left.rows.get(i).get(j).equals(right.rows.get(i).get(j))) {
                    exactlyTheSame = false;
                }
                subtractionRows.get(subtractionRows.size() - 1).add(left.rows.get(i).get(j));
            }
            if (exactlyTheSame) {
                subtractionRows.remove(subtractionRows.size() - 1);
            }
        }
        for (int i = minRowsNumber; i < left.rows.size(); ++i) {
            subtractionRows.add(new ArrayList<>());
            for (int j = 0; j < leftColumns.size(); ++j) {
                subtractionRows.get(subtractionRows.size() - 1).add(left.rows.get(i).get(j));
            }
        }
        String tableName = left.name + " subtract " + right.name;
        return new Table(tableName, subtractionRows, leftColumns);
    }

    private int columnSize() {
        return columns == null ? 0 : columns.size();
    }
}
