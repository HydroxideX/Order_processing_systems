package model;

import javafx.util.Pair;
import model.Schema.Book;
import model.Schema.Book_Order;
import model.Schema.User;
import utils.DatabaseCreds;

import java.sql.*;
import java.util.ArrayList;

public class Store_functionality_implementation implements Store_functionality {
    private DatabaseCreds databaseCreds;
    private Connection conn;
    private String msg = "";

    public void init() throws SQLException {
        databaseCreds = new DatabaseCreds();
        databaseCreds.read_credentials();
        conn = DriverManager.getConnection(databaseCreds.getUrl(), databaseCreds.getUsername(), databaseCreds.getPassword());
        conn.setAutoCommit(false);
    }

    private String build_message_update(int rows) {
        if (rows > 0) {
            return "row changed.";
        } else {
            return "Error no change applied to table.";
        }
    }

    @Override
    public boolean add_new_book(Book book) throws SQLException {
        if (!book.is_valid()){
            System.out.println("not valid");
            return false;
        }
        String sql = "INSERT INTO BOOK VALUES ('" + book.getISBN() + "', '" + book.getAuthor()
                + "', '" + book.getTitle() + "', '" + book.getPublisher_name() + "', '"
                + book.getYear() + "', '" + book.getCategory() + "', '" + book.getSelling_price()
                + "', '" + book.getThreshold() + "', '" + book.getCopies_available() + "')";
        System.out.println(sql);
        PreparedStatement statement = conn.prepareStatement(sql);
        msg = build_message_update(statement.executeUpdate());
        conn.commit();
        statement.close();
        return true;
    }

    @Override
    public String modify_existing_book(Book book, int quantity_change) throws SQLException {
        String sql = "UPDATE BOOK SET COPIES = COPIES +" + quantity_change + " WHERE ISBN = '"
                + book.getISBN() + "' AND TITLE = '" + book.getTitle() + "';";
        PreparedStatement statement = conn.prepareStatement(sql);
        msg = build_message_update(statement.executeUpdate());
        conn.commit();
        statement.close();

        return msg;
    }

    public String modify_existing_book(String title, String ISBN, int quantity_change) throws SQLException {
        BookBuilder builder = BookBuilder.getInstance();
        builder.setTitle(title);
        builder.setISBN(ISBN);
        return modify_existing_book(builder.build(), quantity_change);
    }

    @Override
    public String place_order(Book_Order order) throws SQLException {
        if (!order.is_valid()) return "ERROR: values missing for add place order";
        String sql = "INSERT INTO BOOK_ORDER VALUES ('" + order.getISBN() + "', '" + order.getTitle() +
                "', '" + order.getDate_ordered() + "', '" + order.getUser_name() + "', " + order.getCopies() + ");";
        System.out.println(sql);
        PreparedStatement statement = conn.prepareStatement(sql);
        msg = build_message_update(statement.executeUpdate());
        conn.commit();
        statement.close();
        return msg;
    }

    @Override
    public String confirm_order(Book_Order order) throws SQLException {
        if (!order.is_valid()) return "ERROR: values missing for confirm order";
        String sql = "DELETE FROM book_order WHERE ISBN = '" + order.getISBN() + "' AND TITLE = '" +
                order.getTitle() + "' AND DATE_ORDERED = '" + order.getDate_ordered() + "' AND USER_NAME = '" +
                order.getUser_name() + "'  AND COPIES = '" + order.getCopies() + "'  LIMIT 1;";
        PreparedStatement statement = conn.prepareStatement(sql);
        msg = build_message_update(statement.executeUpdate());
        conn.commit();
        statement.close();
        return msg;
    }

    @Override
    public void search_for_book(SearchBookQuery sq) throws SQLException {
        System.out.println("Connected to the database");
        String sql = "SELECT * FROM BOOK";
        String where_stmt = sq.build_where_statement();
        sql += where_stmt + ";";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        BookBuilder bb = BookBuilder.getInstance();
        while (rs.next()) {
            bb.setAuthor(rs.getString("author")).setCategory(rs.getString("category"))
                    .setCopies_available(rs.getInt("copies")).setISBN(rs.getString("ISBN"))
                    .setPublisher_name(rs.getString("publisher_name"))
                    .setSelling_price(rs.getFloat("selling_price")).setThreshold(rs.getInt("threshold"))
                    .setTitle(rs.getString("title")).setYear(rs.getInt("publication_year"));
            sq.add_to_result(bb.build());
        }
        conn.commit();
        statement.close();
    }

    @Override
    public int get_available_book_with_isbn(String isbn) throws SQLException {
        return get_available_book("isbn", isbn);
    }

    @Override
    public int get_available_book_with_title(String title) throws SQLException {
        return get_available_book("title", title);
    }

    private int get_available_book(String field, String value) throws SQLException {
        String sql = "select copies as cnt from book   where " + field + " = " + value;
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getInt("cnt");
    }

    @Override
    public boolean set_manager(String email) throws SQLException {
        int cnt = get_num_of_records("ONLINE_USER", "EMAIL_ADDRESS", email);
        System.out.println(cnt);
        if (cnt == 0) {
            return false;
        }
        if (cnt > 1) {
            throw new SQLException("more than one user has the same Email");
        }
        String sql = "update online_user  set  MANAGER =true where EMAIL_ADDRESS = " + email;
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.executeUpdate();
        conn.commit();
        return true;
    }

    @Override
    public User get_user(String user_name, String password) throws SQLException {
        String sql = "select * from ONLINE_USER  where  user_name = ? and  password = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, user_name);
        statement.setString(2, password);
        DatabaseCreds databaseCreds = new DatabaseCreds();
        databaseCreds.read_credentials();
        System.out.println("Connected to the database");
        ResultSet rs = statement.executeQuery();
        User user = null;
        while (rs.next()) {
            if (user != null) {
                throw new SQLException("more than one user has same user_name !!!");
            }
            user = new User();
            user.setUser_name(rs.getString("USER_NAME"));
            user.setEmail(rs.getString("EMAIL_ADDRESS"));
            user.set_manager(rs.getBoolean("MANAGER"));
        }
        return user;
    }


    int get_num_of_records(String table, String field, String value) throws SQLException {
        String sql = "select count(*) as cnt from " + table + "  where " + field + " = " + value;
        System.out.println(sql);
        PreparedStatement statement = conn.prepareStatement(sql);
        conn.commit();
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getInt("cnt");
    }

    public ArrayList<Pair<String, Integer>> get_sales() throws SQLException {
        String sql = "select title ,sum(copies) as sum from book_order where  DATEDIFF( curdate(),DATE_ORDERED) <=30" +
                "groub by title  ";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        ArrayList<Pair<String, Integer>> result = new ArrayList<>();
        while (rs.next()) {
            Pair<String, Integer> p = new Pair<>(rs.getString("title"), rs.getInt("sum"));
            result.add(p);
        }
        return result;
    }

    public ArrayList<Pair<String, Integer>> get_top_users(int cnt) throws SQLException {
        String sql = "select USER_NAME ,sum(copies) as sum from book_order where  DATEDIFF( curdate(),DATE_ORDERED) <=90" +
                "groub by USER_NAME  order by sum desc limit " + cnt;
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        ArrayList<Pair<String, Integer>> result = new ArrayList<>();
        while (rs.next()) {
            Pair<String, Integer> p = new Pair<>(rs.getString("USER_NAME"), rs.getInt("sum"));
            result.add(p);
        }
        return result;
    }

    public ArrayList<Pair<String, Integer>> get_top_books(int cnt) throws SQLException {
        String sql = "select title ,sum(copies) as sum from book_order where  DATEDIFF( curdate(),DATE_ORDERED) <=30" +
                "groub by title  order by sum desc limit" + cnt;
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        ArrayList<Pair<String, Integer>> result = new ArrayList<>();
        while (rs.next()) {
            Pair<String, Integer> p = new Pair<>(rs.getString("title"), rs.getInt("sum"));
            result.add(p);
        }
        return result;
    }

    public boolean has_publisher_name(String name) throws SQLException {
        String sql = "select count(*) as cnt from PUBLISHER where name = " + name;
        System.out.println(sql);
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        ArrayList<Pair<String, Integer>> result = new ArrayList<>();
        rs.next();
        return rs.getInt("cnt") != 0;
    }

    public boolean has_book_with_title(String title) throws SQLException {
        return get_count("book","title",title)!=0;
    }
    public boolean has_book_with_ISBN(String ISBN) throws SQLException {
        return get_count("book","ISBN",ISBN)!=0;
    }

    private int get_count(String table, String field, String value) throws SQLException {
        String sql = "select count(*) as cnt from " + table + "  where " + field + "  =  " + value;
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        ArrayList<Pair<String, Integer>> result = new ArrayList<>();
        rs.next();
        return rs.getInt("cnt");
    }
}
