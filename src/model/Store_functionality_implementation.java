package model;

import model.Schema.Book;
import model.Schema.Book_Order;
import model.Schema.User;
import utils.DatabaseCreds;

import java.sql.*;

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
    public String add_new_book(Book book) throws SQLException {
         if(!book.is_valid()) return "ERROR: values missing for add new book";
        String sql = "INSERT INTO BOOK VALUES ('" + book.getISBN() + "', '" + book.getAuthor()
                     + "', '" + book.getTitle() +  "', '" + book.getPublisher_name() + "', '"
                     +book.getYear()  + "', '" + book.getCategory()  + "', '" + book.getSelling_price()
                     + "', '" + book.getThreshold() + "', '" + book.getCopies_available()  + "')";
         PreparedStatement statement = conn.prepareStatement(sql);
        msg = build_message_update(statement.executeUpdate());
        conn.commit();
        statement.close();
        return msg;
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

    @Override
    public String place_order(Book_Order order) throws SQLException {
        if(!order.is_valid()) return "ERROR: values missing for add place order";
        String sql ="INSERT INTO BOOK_ORDER VALUES ('" + order.getISBN() + "', '" + order.getTitle() +
                "', '" + order.getDate_ordered() + "', '" + order.getUser_name()+ "', '" + order.getCopies() +"');";
        PreparedStatement statement = conn.prepareStatement(sql);
        msg = build_message_update(statement.executeUpdate());
        conn.commit();
        statement.close();
        return msg;
    }

    @Override
    public String confirm_order(Book_Order order) throws SQLException {
        if(!order.is_valid()) return "ERROR: values missing for confirm order";
        String sql = "DELETE FROM book_order WHERE ISBN = '" +order.getISBN() +"' AND TITLE = '"+
                order.getTitle()+"' AND DATE_ORDERED = '"+order.getDate_ordered() +"' AND USER_NAME = '"+
                order.getUser_name()+"'  AND COPIES = '" + order.getCopies() + "'  LIMIT 1;";
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
