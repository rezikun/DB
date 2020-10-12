package service;

import entities.Cell;
import entities.Column;
import entities.DataBase;
import entities.Table;
import entities.types.Type;
import helpers.StorageHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class DBService {
    private static DataBase currentDB;

    public static List<String> getAllDataBasesNames() {
        return StorageHelper.getAllDataBasesNames()
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public static List<String> getTablesByDB(String name) {
        var db = getDBByName(name);
        return new ArrayList<>(db.getTables().keySet())
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public static String getCurrentDBName() {
        return currentDB.getName();
    }

    public static DataBase createDB(String name) {
        DataBase db = new DataBase(name);
        currentDB = db;

        StorageHelper.createDBDir(name);
        return db;
    }

    public static DataBase getDBByName(String name) {
        DataBase db = StorageHelper.getDataBase(name);
        currentDB = db;
        return db;
    }

    public static Table getTable(String name) {
        return currentDB.getTable(name);
    }

    public static Table createTable(String name, List<Column> columns) {
        Table newTable = new Table(name, columns);
        currentDB.addTable(newTable);

        StorageHelper.serializeTable(newTable);
        return newTable;
    }

    public static void deleteTable(String name) {
        currentDB.deleteTable(name);
        StorageHelper.deleteTableFile(name);
    }

    public static List<Type> updateRow(Table table, Integer index, HashMap<String, Type> row) {
        var updated = table.updateRow(index, row);
        StorageHelper.serializeTable(table);
        return updated;
    }

    public static List<Type> addRowToTable(Table table, HashMap<String, Type> row) {
        table.addRow();
        var updated = table.updateRow(currentDB.getTable(table.getName()).getRows().size() - 1, row);
        StorageHelper.serializeTable(table);
        return updated;
    }

    public static String setCellInTable(String tableName, Integer rowIndex, Integer columnIndex, Object value) {
        Table t = DBService.getTable(tableName);
        return t.setCellByRowAndColumn(rowIndex, columnIndex, value).getData();
    }

    public static void deleteDB() {
        StorageHelper.deleteDBDir(getCurrentDBName());
        currentDB = null;
    }
}
