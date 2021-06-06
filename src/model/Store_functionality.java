package controller;
import model.Schema.Book;
import model.Schema.Book_Order;

import java.sql.SQLException;

public interface Store_functionality {
    public String add_new_book(Book book) throws SQLException;
    public String modify_existing_book(Book book, int quantity) throws SQLException;
    public void place_order(Book_Order order);
    public void search_for_book(Book book);
    public void confirm_order(Book_Order order);
}
