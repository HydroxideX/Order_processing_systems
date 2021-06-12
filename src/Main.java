import utils.DatabaseCreds;

import java.sql.*;


public class Main {
    public static void testInsert() throws SQLException {
        DatabaseCreds databaseCreds = new DatabaseCreds();
        databaseCreds.read_credentials();
        Connection connection = DriverManager.getConnection(databaseCreds.getUrl(), databaseCreds.getUsername(), databaseCreds.getPassword());
        System.out.println("Connected to the database");
        String sql = "update book  set copies = 0  ";
        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.setString(1, "14");
//        statement.setString(2, "sosfso");
//        statement.setInt(3, -1);

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

        String sql = "select * from book";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {

            String author = rs.getString("author");
            System.out.println(author);
            String supplierID = rs.getString("title");
            System.out.println(supplierID);
        }
        statement.close();
        connection.close();
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        try {
         //  testInsert();
          testSelect();
        } catch (SQLException e) {
            System.out.println("Cant connect to database");
            e.printStackTrace();
        }

    }

}
