package service;

import entities.Column;
import entities.DataBase;
import entities.Table;
import entities.types.Type;
import entities.types.TypeName;
import helpers.StorageHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    public static String getNewUniqueName() {
        var names = getAllDataBasesNames();
        var standardNames = names.stream()
                .filter(DBService::isStandardName)
                .sorted()
                .collect(Collectors.toList());
        if (standardNames.isEmpty()) {
            return "Database1";
        }
        String lastName = standardNames.get(standardNames.size() - 1);
        int newIndex = Integer.parseInt(lastName.substring(8)) + 1;
        return "Database" + newIndex;
    }

    private static Boolean isStandardName(String name) {
        Pattern pattern = Pattern.compile("Database\\d+");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static String getNewUniqueTableName(String databaseName) {
        var names = getTablesByDB(databaseName);
        var standardNames = names.stream()
                .filter(DBService::isStandardTableName)
                .sorted()
                .collect(Collectors.toList());
        System.out.println(standardNames);
        if (standardNames.isEmpty()) {
            return "Table1";
        }
        String lastName = standardNames.get(standardNames.size() - 1);
        int newIndex = Integer.parseInt(lastName.substring(5)) + 1;
        return "Table" + newIndex;
    }

    private static Boolean isStandardTableName(String name) {
        Pattern pattern = Pattern.compile("Table\\d+");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static void renameTable(String oldName, String newName) {
        Table table = getTable(oldName);
        table.setName(newName);
        StorageHelper.deleteTableFile(oldName);
        StorageHelper.serializeTable(table);
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
    public static Table createTable(String name, List<List<Type>> rows, List<Column> columns) {
        Table newTable = new Table(name, rows, columns);
        currentDB.addTable(newTable);

        StorageHelper.serializeTable(newTable);
        return newTable;
    }
    public static void deleteTable(String name) {
        currentDB.deleteTable(name);
        StorageHelper.deleteTableFile(name);
    }

    public static void addEmptyRow(String tableName) {
        Table t = DBService.getTable(tableName);
        t.addRow();
        StorageHelper.serializeTable(t);
    }

    public static void deleteRow(String tableName, int rowIndex) {
        Table t = DBService.getTable(tableName);
        t.deleteRow(rowIndex);
        StorageHelper.serializeTable(t);
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
        String cell = t.setCellByRowAndColumn(rowIndex, columnIndex, value).getData();
        StorageHelper.serializeTable(t);
        return cell;
    }

    public static void deleteDB() {
        StorageHelper.deleteDBDir(getCurrentDBName());
        currentDB = null;
    }

    public static void deleteDB(String name) {
        StorageHelper.deleteDBDir(name);
    }

    public static String sortByColumn(String tableName, Integer columnIndex) {
        Table t = currentDB.getTable(tableName);
        t.sortByColumnIndex(columnIndex);
        return t.getName();
    }

    public static Table storeFileType(String tableName, Integer columnIndex, Integer row,  File file) throws IOException {
        Table table = currentDB.getTable(tableName);
        String fileName = tableName + "_file_col_" + columnIndex + "_row" + row.toString();
        File savedFile = StorageHelper.saveTxtFile(file, fileName, getCurrentDBName());
        table.setCellByRowAndColumn(row, columnIndex, savedFile);
        StorageHelper.serializeTable(table);
        return table;
    }

    public static String readFileContent(String tableName, Integer columnIndex, Integer row) {
        Table table = currentDB.getTable(tableName);
        String fileName = tableName + "_file_col_" + columnIndex + "_row" + row.toString();
//        File savedFile = StorageHelper.saveTxtFile(file, fileName, getCurrentDBName());
//        table.setCellByRowAndColumn(row, columnIndex, savedFile);
//        return table;
        return null;
    }

    public static boolean isFileColumn(String tableName, Integer columnIndex) {
        Table table = currentDB.getTable(tableName);
        return table.getColumns().get(columnIndex).getType().equals(TypeName.ENUM);
    }

    public static Table subtract(List<String> tableNames) {
        Table currentSubtractionResult = currentDB.getTable(tableNames.iterator().next());
        int counter = 0;
        for (String tableName : tableNames) {
            if (counter == 0) {
                counter++;
                continue;
            }
            currentSubtractionResult =
                Table.subtract(currentSubtractionResult, currentDB.getTable(tableName));
        }
        return currentSubtractionResult;
    }
}
