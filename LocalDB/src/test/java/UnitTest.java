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
        columns.add(new Column("enum", TypeName.ENUM, List.of("BOB", "JOKE")));
        columns.add(new Column("email", TypeName.EMAIL));
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
        assertThat(testTable.getColumns().get(0).getName(), is("string"));
        assertThat(testTable.getRows().get(0), is(List.of("New data", "A")));
    }

    @Test
    public void subtractTablesTest() {
        var columns = new ArrayList<Column>();
        columns.add(new Column("string", TypeName.STRING));
        List<List<Type>> rows1 = new ArrayList<>();
        rows1.add(List.of(new StringType("asd")));
        rows1.add(List.of(new StringType("btw")));
        List<List<Type>> rows2 = new ArrayList<>();
        rows2.add(List.of(new StringType("asd")));
        Table testTable1 = DBService.createTable("table1", rows1, columns);
        Table testTable2 = DBService.createTable("table2", rows2, columns);
        Table result = DBService.subtract(List.of("table1", "table2"));
        assertThat(result.getRows(), is(List.of(List.of("btw"))));
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
        assertEquals(3, db.getTables().size());
        assertNotNull(db.getTables().get("Table"));
        assertEquals(table.getColumns(), db.getTables().get("Table").getColumns());
        assertEquals(a.getData(), db.getTables().get("Table").getRows().get(0).get(0));
    }
}
