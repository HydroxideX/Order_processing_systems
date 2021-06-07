package model;
import model.Schema.Book;
import model.Schema.Book_Order;
import model.Schema.User;

import java.sql.SQLException;

public interface Store_functionality {
    public void init() throws SQLException;
    public String add_new_book(Book book) throws SQLException;
    public String modify_existing_book(Book book, int quantity) throws SQLException;
    public String place_order(Book_Order order) throws SQLException;
    public String confirm_order(Book_Order order) throws SQLException;
    public void search_for_book(SearchBookQuery sq) throws SQLException;
    public User get_user(String user_name , String password) throws SQLException;
}
