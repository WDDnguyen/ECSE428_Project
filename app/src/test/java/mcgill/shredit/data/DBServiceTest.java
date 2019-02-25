package mcgill.shredit.data;

import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

public class DBServiceTest {

    private static String TABLE_NAME = "Users";

    @Test
    public void test() throws SQLException {
        DBService dbs = new DBService();
        ResultSet rs = dbs.query("SELECT * FROM " + TABLE_NAME + ";");
        assertNotNull(rs);
        while(rs.next());
    }
}