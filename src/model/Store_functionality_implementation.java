package model;

import model.Schema.Book;
import model.Schema.Book_Order;
import model.Schema.User;
import utils.DatabaseCreds;

import java.sql.*;

public class Store_functionality_implementation implements Store_functionality {
    private DatabaseCreds databaseCreds;
    private Connection conn;

    public void init() throws SQLException {
        databaseCreds = new DatabaseCreds();
        databaseCreds.read_credentials();
        conn = DriverManager.getConnection(databaseCreds.getUrl(), databaseCreds.getUsername(), databaseCreds.getPassword());
        conn.setAutoCommit(false);
    }

    @Override
    public String add_new_book(Book book) throws SQLException {
        String msg = "";
        System.out.println("Connected to the database");
        String sql = "INSERT INTO book (ISBN, author, title, publisher_name, category, year, threshold, copies_available, selling_price) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, book.getISBN());
        statement.setString(2, book.getAuthor());
        statement.setString(3, book.getTitle());
        statement.setString(4, book.getPublisher_name());
        statement.setString(5, book.getCategory());
        statement.setInt(6, book.getYear());
        statement.setInt(7, book.getThreshold());
        statement.setInt(8, book.getThreshold());
        statement.setFloat(9, book.getSelling_price());
        int rows = statement.executeUpdate();
        if (rows > 0) {
            msg = "a row has been updated";
        } else {
            msg = "Book already Exists";
        }
        conn.commit();
        return msg;
    }

    @Override
    public String modify_existing_book(Book book, int quantity) throws SQLException {
        String msg = "";
        System.out.println("Connected to the database");
        String sql = "UPDATE BOOK SET COPIES = COPIES + ? WHERE ISBN = ? AND TITLE = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, quantity);
        statement.setString(2, book.getISBN());
        statement.setString(3, book.getTitle());
        int rows = statement.executeUpdate();
        if (rows > 0) {
            msg = "a row has been updated";
        } else {
            msg = "Book doesn't Exists or update failed";
        }
        conn.commit();
        return msg;
    }

    @Override
    public String place_order(Book_Order order) throws SQLException {
        String msg = "";
        System.out.println("Connected to the database");
        String sql = "INSERT INTO book (ISBN, author, title, publisher_name, category, year, threshold, copies_available, selling_price) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, order.getISBN());
        statement.setString(2, order.getTitle());
        statement.setDate(3, (Date) order.getDate_ordered());
        statement.setString(4, order.getUser_name());
        statement.setInt(5, order.getCopies());
        int rows = statement.executeUpdate();
        if (rows > 0) {
            msg = "a row has been updated";
        } else {
            msg = "Book already Exists";
        }
        conn.commit();
        return msg;
    }

    @Override
    public void search_for_book(Book book) {

    }

    @Override
    public void confirm_order(Book_Order order) {

    }

    @Override
    public User get_user(String user_name, String password) throws SQLException {
        String sql = "select * from ONLINE_USER  (user_name, password) values (?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, user_name);
        statement.setString(2, password);
        DatabaseCreds databaseCreds = new DatabaseCreds();
        databaseCreds.read_credentials();
        System.out.println("Connected to the database");
        ResultSet rs = statement.executeQuery();
        User user = null;
        while (rs.next()){
            if(user!=null){
                throw new SQLException("more than one user has same user_name !!!");
            }
            user = new User();
            user.setUser_name(rs.getString("USER_NAME"));
            user.setEmail(rs.getString("EMAIL_ADDRESS"));
            user.setIs_manager(rs.getBoolean("MANAGER BOOL"));
        }
        return user;
    }
}
