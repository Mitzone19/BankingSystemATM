package ro.linkAcademy;

import java.sql.*;

public class DbManager {
    private Connection conn;

    public DbManager(String url, String username, String password) throws SQLException {
        conn = DriverManager.getConnection(url, username, password);
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

    // Add methods for insert, update, delete operations

}
