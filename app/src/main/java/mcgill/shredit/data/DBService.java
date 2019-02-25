package mcgill.shredit.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBService {

    private static final String DB_USERNAME = "shreditpostgre";
    private static final String DB_PASSWORD = "shreditpostgre";
    private static final String DB_SERVER_URL = "jdbc:postgresql://shreditpostgre.caqrxjkfzeba.us-east-2.rds.amazonaws.com:5432/shreditpostgre";

    public ResultSet query(String query) throws SQLException {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } finally {
            if (con != null) { con.close(); }
            if (stmt != null) { stmt.close(); }
        }
        return rs;
    }

    private Connection getConnection() throws SQLException {
        Connection con;
        Properties connectionProps = new Properties();
        connectionProps.put("user", DB_USERNAME);
        connectionProps.put("password", DB_PASSWORD);

        con = DriverManager.getConnection(DB_SERVER_URL, connectionProps);
        return con;
    }
}
