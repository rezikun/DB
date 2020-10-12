package com.example.db.services;

import com.example.db.entities.Cell;
import com.example.db.entities.Column;
import com.example.db.entities.DataBase;
import com.example.db.entities.Table;
import com.example.db.entities.types.Type;
import com.example.db.helpers.StorageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class DBService {
    private DataBase currentDB;

    @Autowired
    public DBService(DataBase dataBase) {
        this.currentDB = dataBase;
    }

    public List<String> getAllDataBasesNames() {
        return StorageHelper.getAllDataBasesNames()
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<String> getTablesByDB(String name) {
        var db = getDBByName(name);
        return new ArrayList<>(db.getTables().keySet())
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public String getCurrentDBName() {
        return currentDB.getName();
    }

    public DataBase createDB(String name) {
        DataBase db = new DataBase(name);
        currentDB = db;

        StorageHelper.createDBDir(name);
        return db;
    }

    public DataBase getDBByName(String name) {
        DataBase db = StorageHelper.getDataBase(name);
        currentDB = db;
        return db;
    }

    public Table getTable(String name) {
        return currentDB.getTable(name);
    }

    public Table createTable(String name, List<Column> columns) {
        Table newTable = new Table(name, columns);
        currentDB.addTable(newTable);

        StorageHelper.serializeTable(newTable, currentDB.getName());
        return newTable;
    }

    public void deleteTable(String name) {
        currentDB.deleteTable(name);
        StorageHelper.deleteTableFile(name, currentDB.getName());
    }

    public List<Type> updateRow(Table table, Integer index, HashMap<String, Type> row) {
        var updated = table.updateRow(index, row);
        StorageHelper.serializeTable(table, currentDB.getName());
        return updated;
    }

    public List<Type> addRowToTable(Table table, HashMap<String, Type> row) {
        table.addRow();
        var updated = table.updateRow(currentDB.getTable(table.getName()).getRows().size() - 1, row);
        StorageHelper.serializeTable(table, currentDB.getName());
        return updated;
    }

    public List<Type> addRowToTable(Table table, Map<String, String> row) {
        table.addRow();
        HashMap<String, Type> newRow = new HashMap<>();
        row.forEach((colName, value) -> newRow.put(colName, table.getCellByString(colName, value)));
        var updated = table.updateRow(currentDB.getTable(table.getName()).getRows().size() - 1, newRow);
        StorageHelper.serializeTable(table, currentDB.getName());
        return updated;
    }

    public String setCellInTable(String tableName, Integer rowIndex, Integer columnIndex, Object value) {
        Table t = currentDB.getTable(tableName);
        return t.setCellByRowAndColumn(rowIndex, columnIndex, value).getData();
    }

    public void deleteDB() {
        StorageHelper.deleteDBDir(getCurrentDBName());
        currentDB = null;
    }

    public void deleteDB(String name) {
        StorageHelper.deleteDBDir(name);
    }

    public Table sortByColumn(String tableName, String columnName) {
        Table t = currentDB.getTable(tableName);
        return t.sortByColumn(columnName);
    }
}
