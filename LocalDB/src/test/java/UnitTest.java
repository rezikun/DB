import entities.Column;
import entities.DataBase;
import entities.Table;
import entities.types.*;
import helpers.StorageHelper;
import org.junit.*;
import service.DBService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class UnitTest {

    private static final String dBName = "Test";

    private static DataBase testDB;

    @BeforeClass
    public static void createDB() {
        if (!StorageHelper.dbIsUnique(dBName)) {
            StorageHelper.deleteDBDir(dBName);
        }
        testDB = DBService.createDB(dBName);
    }

    @AfterClass
    public static void deleteBD() {
        StorageHelper.deleteDBDir(dBName);
    }

    @Test
    public void createTableTest() {
        var columns = new ArrayList<Column>();
        columns.add(new Column("string", TypeName.STRING));
        columns.add(new Column("char", TypeName.CHAR));
        columns.add(new Column("int", TypeName.INT));
        columns.add(new Column("real", TypeName.REAL));
        columns.add(new Column("text", TypeName.TEXT));
        columns.add(new Column("interval", TypeName.INT_INTERVAL));
        Table newTable = DBService.createTable("All types", columns);
        assertThat("All types", is(newTable.getName()));
        assertThat(newTable.getColumns().size(), is(6));
        assertThat(newTable.getRows().size(), is(1));
    }

    @Test
    public void seedTableTest() {
        var columns = new ArrayList<Column>();
        columns.add(new Column("string", TypeName.STRING));
        columns.add(new Column("char", TypeName.CHAR));
        Table testTable = DBService.createTable("test", columns);
        var row = new HashMap<String, Type>();
        StringType a = new StringType();
        a.setData("New data");
        row.put("string", a);
        CharType b = new CharType();
        b.setData('A');
        row.put("char", b);
        testTable.updateRow(0, row);

        assertThat(testTable.getRows().size(), is(1));
        assertThat(testTable.getColumns().get(columns.get(0)), is(0));
        assertThat(testTable.getRows().get(0), is(List.of(a, b)));
    }

    @Test
    public void sortTableTest() {
        var columns = new ArrayList<Column>();
        columns.add(new Column("string", TypeName.STRING));
        columns.add(new Column("int", TypeName.INT));
        Table testTable = DBService.createTable("test", columns);
        var row = new HashMap<String, Type>();
        IntType a = new IntType(10);
        row.put("int", a);
        var row1 = DBService.addRowToTable(testTable, row);
        IntType b = new IntType(4);
        row.put("int", b);
        var row2 = DBService.addRowToTable(testTable, row);
        IntType c = new IntType(8);
        row.put("int", c);
        var row3 = DBService.addRowToTable(testTable, row);
        var row0 = testTable.getRows().get(0);

        assertThat(a.getData(), is(testTable.getCellByRowAndColumn(1, "int").getData()));
        assertThat(testTable.getRows(), is(List.of(row0, row1, row2, row3)));

        testTable.sortByColumn("int");

        assertThat(testTable.getRows(), is(List.of(row0, row2, row3, row1)));
    }

    @Test
    public void deserializeTableTest() {
        var table = DBService.createTable("Table", List.of(new Column("string", TypeName.STRING)));
        var row = new HashMap<String, Type>();
        StringType a = new StringType();
        a.setData("New data");
        row.put("string", a);
        DBService.updateRow(table, 0, row);

        var db = DBService.getDBByName(dBName);

        assertEquals(dBName, db.getName());
        assertEquals(1, db.getTables().size());
        assertNotNull(db.getTables().get("Table"));
        assertEquals(table.getColumns(), db.getTables().get("Table").getColumns());
        assertEquals(a.getData(), db.getTables().get("Table").getRows().get(0).get(0).getData());
    }
}
