package mcgill.shredit.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBService {

    private static final String DB_USERNAME = "";
    private static final String DB_PASSWORD = "";
    private static final String DB_SERVER_NAME = "";
    private static final String DB_PORT = "";

    public ResultSet query(String query) throws SQLException {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs;
        try {
            con = getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                String coffeeName = rs.getString("COF_NAME");
                int supplierID = rs.getInt("SUP_ID");
                float price = rs.getFloat("PRICE");
                int sales = rs.getInt("SALES");
                int total = rs.getInt("TOTAL");
                System.out.println(coffeeName + "\t" + supplierID +
                        "\t" + price + "\t" + sales +
                        "\t" + total);
            }
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

        con = DriverManager.getConnection(
                    "jdbc:" +
                            "postgresql" +
                            "://" +
                            DB_SERVER_NAME +
                            ":" +
                            DB_PORT +
                            "/",
                    connectionProps);
        return con;
    }
}
