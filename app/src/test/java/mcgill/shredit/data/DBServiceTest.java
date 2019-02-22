package mcgill.shredit.data;

import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBServiceTest {

    private static String TABLE_NAME = "Users";

    @Test
    public void test() throws SQLException {
        DBService dbs = new DBService();
        ResultSet rs = dbs.query("SELECT * ");
        while(rs.next());
    }
}