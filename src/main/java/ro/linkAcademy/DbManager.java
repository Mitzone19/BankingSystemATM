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
    public void insertContDB(String iban, double balanta, String tipDeCont) throws SQLException {
        executeQuery("Insert into contBancar values(null,"+iban+","+balanta+","+tipDeCont+");");
    }

    public void insertPersonDB(String nume, String prenume, String cnp,String parola) throws SQLException {
        executeQuery("Insert into Person values(null,"+nume+","+prenume+","+cnp+","+parola+");");
    }
    public void insertContPersonDB(String cnp, String iban) throws SQLException {
        executeQuery("Insert into PersonConturi values(null,"+cnp+","+iban+");");
    }
}
