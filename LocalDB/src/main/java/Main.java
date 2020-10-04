import entities.Column;
import entities.DataBase;
import entities.Table;
import entities.types.IntType;
import entities.types.StringType;
import entities.types.Type;
import entities.types.TypeName;
import helpers.StorageHelper;
import service.DBService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // create db with tables
//        DBService.createDB("Test5");
//        DBService.createTable("Users", List.of(new Column("name", TypeName.STRING)));
//        var columns = new ArrayList<Column>();
//        columns.add(new Column("string", TypeName.STRING));
//        columns.add(new Column("char", TypeName.CHAR));
//        columns.add(new Column("int", TypeName.INT));
//        columns.add(new Column("real", TypeName.REAL));
//        columns.add(new Column("text", TypeName.TEXT));
//        columns.add(new Column("interval", TypeName.INT_INTERVAL));
//        DBService.createTable("All types", columns);

        // deserialize db
        System.out.println("DESERIALIZE DB");
        DataBase db = DBService.getDBByName("Test5");
        System.out.println(db.getName());
        db.getTables().forEach((key, value) -> {
            System.out.println("Table " + key);
            System.out.println("Columns: ");
        value.getColumns().keySet().forEach(column -> System.out.println(column.getName() + " " + column.getType()));});

        // edit table schema
        System.out.println("EDIT TABLE SCHEMA");
        Table table = db.getTable("Users");
        // add new column
        Column column = new Column("number", TypeName.INT);
        table.addColumn(column);
        table.getColumns().keySet().forEach(c -> System.out.println(c.getName() + " " + c.getType()));
        StorageHelper.serializeTable(table);
        // edit table name
        table.updateColumnName("number", "phone");
        table.getColumns().keySet().forEach(c -> System.out.println(c.getName() + " " + c.getType()));
        StorageHelper.serializeTable(table);
        // delete column throws exception
//        table.deleteColumn("number");
//        table.getColumns().keySet().forEach(c -> System.out.println(c.getName() + " " + c.getType()));

        // delete table
        // DBService.deleteTable("Users");

        Table tableAllTypes = db.getTable("All types");
        // edit row
        System.out.println("EDIT ROW");
        tableAllTypes.getColumns().keySet().forEach(c -> System.out.printf("'%15s' %n", c.getName()));
        System.out.println("BEFORE");
        tableAllTypes.getRows().forEach(Main::printRow);
        var row = new HashMap<String, Type>();
        StringType a = new StringType();
        a.setData("Data");
        row.put("string", a);
        tableAllTypes.updateRow(0, row);
        System.out.println("\nAFTER");
        tableAllTypes.getRows().forEach(Main::printRow);

        // delete row
        System.out.println("\nDELETE ROW");
        tableAllTypes.deleteRow(0);
        tableAllTypes.getRows().forEach(Main::printRow);

        // sort by field
        System.out.println("\nSORT");
        IntType b = new IntType();
        b.setData(10);
        row.put("int", b);
        DBService.addRowToTable(tableAllTypes, row);
        b.setData(4);
        row.put("int", b);
        DBService.addRowToTable(tableAllTypes, row);
        b.setData(8);
        row.put("int", b);
        DBService.addRowToTable(tableAllTypes, row);

        System.out.println("\nBEFORE");
        tableAllTypes.getRows().forEach(Main::printRow);

        tableAllTypes.sortByColumn("int");

        System.out.println("\nAFTER");
        tableAllTypes.getRows().forEach(Main::printRow);
    }

    private static void printRow(List<Type> row) {
        row.forEach(cell -> System.out.print(cell.getData() + " "));
    }
}
