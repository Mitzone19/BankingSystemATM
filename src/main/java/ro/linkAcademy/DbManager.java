package ro.linkAcademy;

import java.sql.*;

public class DbManager {
    private Connection conn;

    public DbManager(String url, String username, String password) throws SQLException {
        conn = DriverManager.getConnection(url, username, password);
    }

    // Method to execute SELECT queries
    public ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

    // Method to execute INSERT, UPDATE, DELETE queries
    public void executeUpdate(String query) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(query);
    }

    // Add other methods for insert, update, delete operations
    public void insertContDB(String iban, double balanta, String tipDeCont) throws SQLException {
        executeUpdate("INSERT INTO contBancar (iban, balanta, tipDeCont) VALUES ('" + iban + "', " + balanta + ", '" + tipDeCont + "')");
    }

    public void insertPersonDB(String nume, String prenume, String cnp, String parola) throws SQLException {
        executeUpdate("INSERT INTO Person (nume, prenume, cnp, parola) VALUES ('" + nume + "', '" + prenume + "', '" + cnp + "', '" + parola + "')");
    }

    public void insertContPersonDB(String cnp, String iban) throws SQLException {
        executeUpdate("INSERT INTO PersonConturi (cnp, iban) VALUES ('" + cnp + "', '" + iban + "')");
    }
    public void insertTranzactieDB(String contPrincipal, Double suma, String contDestinatar, String tipTranzactie) throws SQLException {
        executeUpdate("INSERT INTO Tranzactii (contPrincipal, suma, contDestinatar, tipTranzactie) VALUES ('" + contPrincipal + "', " + suma + ", '" + contDestinatar + "', '" + tipTranzactie + "')");
    }

}
