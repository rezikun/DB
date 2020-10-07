package helpers;

import entities.DataBase;
import entities.Table;
import service.DBService;

import java.io.*;

public final class StorageHelper {
    public static final String storagePath = System.getProperty("user.dir") + "/storage/";

    public static void createDBDir(String name) {
        if (!dbIsUnique(name)) {
            throw new RuntimeException("Such db already exists");
        }
        File storage = new File(storagePath + name);
        boolean created = storage.mkdir();
        if (!created) {
            throw new RuntimeException("Couldn’t create specified directory");
        }

        File txtFileDir = new File(storagePath + name + "/files");
        created = txtFileDir.mkdir();
        if (!created) {
            throw new RuntimeException("Couldn’t create specified directory");
        }
    }

    public static boolean dbIsUnique(String name) {
        File[] files = new File(storagePath).listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory() && file.getName().equals(name)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String saveTxtFile(File file) {
        String path = storagePath + DBService.getCurrentDBName() + "/files/";
        File txt = new File(path + file.getName());
        return txt.getAbsolutePath();
    }

    public static void serializeTable(Table table) {
        try(ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("storage/" + DBService.getCurrentDBName() + "/" + table.getName() + ".dat")))
        {
            oos.writeObject(table);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static Table deserializeTable(String path) {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path)))
        {
            return (Table) ois.readObject();
        }
        catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static DataBase getDataBase(String name) {
        String path = storagePath + name;
        File[] files = new File(path).listFiles();
        if (files != null) {
            DataBase db = new DataBase(name);
            var tables = db.getTables();
            for (File file : files) {
                if (!file.isDirectory()) {
                    var table = deserializeTable(file.getPath());
                    tables.put(table.getName(), table);
                }
            }
            db.setTables(tables);
            return db;
        } else {
            throw new RuntimeException("There is no tables to access");
        }
    }

    public static void deleteTableFile(String name) {
        String path = storagePath + DBService.getCurrentDBName() + "/" + name + ".dat";
        File file = new File(path);
        if (!file.delete()) {
            throw new RuntimeException("Error while deleting file");
        }
    }

    public static void deleteDBDir(String name) {
        String path = storagePath + name;
        File file = new File(path);
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (!f.delete()) {
                    throw new RuntimeException("Error while deleting " + f.getName() + " file");
                }
            }
        }
        if (!file.delete()) {
            throw new RuntimeException("Error while deleting database " + file.getName() + " dir");
        }
    }
}
