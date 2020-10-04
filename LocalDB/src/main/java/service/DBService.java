package service;

import entities.Cell;
import entities.Column;
import entities.DataBase;
import entities.Table;
import entities.types.Type;
import helpers.StorageHelper;

import java.util.HashMap;
import java.util.List;

public class DBService {
    private static DataBase currentDB;

    public static String getCurrentDBName() {
        return currentDB.getName();
    }

    public static void main(String[] args) {
        //works
//        createDB("Test1");
//        createTable("Users", List.of("Nick", "Olia"));

        //works
//        DataBase db = getDBByName("Test1");
//        System.out.println(db.getName());
//        System.out.println(db.getTables().get(0));
    }

    public static void createDB(String name) {
        DataBase db = new DataBase(name);
        currentDB = db;

        StorageHelper.createDBDir(name);
    }

    public static DataBase getDBByName(String name) {
        DataBase db = StorageHelper.getDataBase(name);
        currentDB = db;
        return db;
    }

    public static void createTable(String name, List<Column> columns) {
        Table newTable = new Table(name, columns);
        currentDB.addTable(newTable);

        StorageHelper.serializeTable(newTable);
    }

    public static void deleteTable(String name) {
        currentDB.deleteTable(name);
        StorageHelper.deleteTableFile(name);
    }

    public static void addRowToTable(Table table, HashMap<String, Type> row) {
        table.addRow();
        table.updateRow(currentDB.getTables().size() - 1, row);
    }
}
