package model;
import model.Schema.Book;
import model.Schema.Book_Order;
import model.Schema.User;

import java.sql.SQLException;

public interface Store_functionality {
    public void init() throws SQLException;
    public boolean add_new_book(Book book) throws SQLException;
    public String modify_existing_book(Book book, int quantity) throws SQLException;
    public boolean place_order(Book_Order order) throws SQLException;

    boolean confirm_sales(Book_Order order) throws SQLException;
    public String confirm_order(Book_Order order) throws SQLException;
    public void search_for_book(SearchBookQuery sq) throws SQLException;
    public User get_user(String user_name , String password) throws SQLException;
    public void get_manager_orders(BookOrderQuery oq) throws SQLException;
    int get_available_book_with_isbn(String isbn) throws SQLException;
    int get_available_book_with_title(String title) throws SQLException;
    public boolean set_manager(String email) throws SQLException;
}
