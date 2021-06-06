import utils.DatabaseCreds;

import java.sql.*;


public class Main {
    public static void testInsert() throws SQLException {
        DatabaseCreds databaseCreds = new DatabaseCreds();
        databaseCreds.read_credentials();
        Connection connection = DriverManager.getConnection(databaseCreds.getUrl(), databaseCreds.getUsername(), databaseCreds.getPassword());
        System.out.println("Connected to the database");
        String sql = "INSERT INTO author (name, AUTHOR_ID) values (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "theamazingbook");
        statement.setString(2, "15");
        int rows = statement.executeUpdate();
        if (rows > 0) {
            System.out.println("a row has been updated");
        } else {
            System.out.println("Error");
        }
        statement.close();
        connection.close();
    }

    public static void testSelect() throws SQLException {
        DatabaseCreds databaseCreds = new DatabaseCreds();
        databaseCreds.read_credentials();
        Connection connection = DriverManager.getConnection(databaseCreds.getUrl(), databaseCreds.getUsername(), databaseCreds.getPassword());
        System.out.println("Connected to the database");

        String sql = "select * from author";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            String author = rs.getString("name");
            System.out.println(author);
            String supplierID = rs.getString("author_id");
            System.out.println(supplierID);
        }
        statement.close();
        connection.close();
    }

    public static void main(String[] args) {

        try {
            testInsert();
            testSelect();
        } catch (SQLException e) {
            System.out.println("Cant connect to database");
            e.printStackTrace();
        }
    }
}
