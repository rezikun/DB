package helpers;

import entities.DataBase;
import entities.Table;
import service.DBService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static java.nio.file.StandardCopyOption.*;

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

    public static File saveTxtFile(File file, String fileName, String dbName) throws IOException {
        String path = storagePath + dbName + "/files/" + fileName + ".txt";
        Path toFile = Files.copy(Path.of(file.getAbsolutePath()), Path.of(path), REPLACE_EXISTING);
        return new File(toFile.toString());
    }

    public static String readTxtFile(String path) {
        if (path.isEmpty()) {
            return "No file";
        }
        try {
            return Files.readString(Paths.get(path), StandardCharsets.US_ASCII);
        } catch (Exception e) {
            throw new RuntimeException("Reading file failed");
        }
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
                    var table = deserializeTable(file.getAbsolutePath());
                    System.out.println(table);
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

    public static List<String> getAllDataBasesNames() {
        List<String> names = new ArrayList<>();
        File[] dbs = new File(storagePath).listFiles();
        if (dbs != null) {
            for (File file : dbs) {
                if (file.isDirectory()) {
                    names.add(file.getName());
                }
            }
        }
        return names;
    }
}
