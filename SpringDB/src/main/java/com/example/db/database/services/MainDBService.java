package com.example.db.database.services;

import com.example.db.database.entities.Column;
import com.example.db.database.entities.DataBase;
import com.example.db.database.entities.Table;
import com.example.db.database.entities.types.Mapper;
import com.example.db.database.entities.types.Type;
import com.example.db.database.helpers.StorageHelper;
import com.example.db.exceptions.NotOpenDatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

// only this service can be used to interact with database
// this service has information about current db downloaded
// TODO: make it remote interface for rmi
@Service
public class MainDBService {
    private DataBase currentDB;

    @Autowired
    public MainDBService(DataBase dataBase) {
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

    public Optional<DataBase> createDB(String name) {
        DataBase db = new DataBase(name);
        currentDB = db;
        try {
            StorageHelper.createDBDir(name);
        } catch (RuntimeException ex) {
            return Optional.empty();
        }

        return Optional.of(db);
    }

    public DataBase getDBByName(String name) {
        DataBase db = StorageHelper.getDataBase(name);
        currentDB = db;
        return db;
    }

    public Table getTable(String name) throws NotOpenDatabaseException {
        if (currentDB.getName().isEmpty()) {
            throw new NotOpenDatabaseException();
        }
        return currentDB.getTable(name);
    }

    public Table createTable(String name, List<Column> columns) throws NotOpenDatabaseException {
        Table newTable = new Table(name, columns);
        currentDB.addTable(newTable);

        if (currentDB.getName().isEmpty()) {
            throw new NotOpenDatabaseException();
        }
        StorageHelper.serializeTable(newTable, currentDB.getName());
        return newTable;
    }

    public void deleteTable(String name) throws NotOpenDatabaseException {
        if (currentDB.getName().isEmpty()) {
            throw new NotOpenDatabaseException();
        }
        currentDB.deleteTable(name);
        StorageHelper.deleteTableFile(name, currentDB.getName());
    }

    public List<Type> updateRow(Table table, Integer index, HashMap<String, Type> row) {
        var updated = table.updateRow(index, row);
        StorageHelper.serializeTable(table, currentDB.getName());
        return updated;
    }

    public List<Type> updateRow(Table table, Integer index, Map<String, String> row) throws NotOpenDatabaseException {
        if (currentDB.getName().isEmpty()) {
            throw new NotOpenDatabaseException();
        }
        HashMap<String, Type> newRow = new HashMap<>();
        row.forEach((colName, value) -> newRow.put(colName, table.getCellByString(colName, value)));
        var updated = table.updateRow(index, newRow);
        StorageHelper.serializeTable(table, currentDB.getName());
        return updated;
    }

    public List<Type> addRowToTable(Table table, HashMap<String, Type> row) {
        table.addRow();
        var updated = table.updateRow(currentDB.getTable(table.getName()).getRows().size() - 1, row);
        StorageHelper.serializeTable(table, currentDB.getName());
        return updated;
    }

    public List<Type> addRowToTable(Table table, Map<String, String> row) throws NotOpenDatabaseException {
        if (currentDB.getName().isEmpty()) {
            throw new NotOpenDatabaseException();
        }
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

    public Table sortByColumn(String tableName, String columnName) throws NotOpenDatabaseException {
        if (currentDB.getName().isEmpty()) {
            throw new NotOpenDatabaseException();
        }
        Table t = currentDB.getTable(tableName);
        return t.sortByColumn(columnName);
    }

    public Table addColumnToTable(String tableName, String columnName, String columnType) {
        Table table = this.currentDB.getTable(tableName);
        table.addColumn(new Column(columnName, Mapper.toType(columnType)));
        return table;
    }

    public Table storeFileType(String tableName, String columnName, Integer row,  byte[] file) {
        Table table = currentDB.getTable(tableName);
        Integer col = table.getIndexByColumnName(columnName);
        String fileName = tableName + "_file_col_" + columnName + "_row" + row.toString();
        File savedFile = StorageHelper.saveTxtFile(file, fileName, getCurrentDBName());
        table.setCellByRowAndColumn(row, col, savedFile);
        return table;
    }

    public void save() {

    }
}
