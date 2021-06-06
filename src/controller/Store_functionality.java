package controller;
import model.*;
public interface Store_functionality {
    public void add_new_book(Book book);
    public void modify_existing_book(Book old_book, Book new_book);
    public void place_order(Book_Order order);
    public void search_for_book(Book book);
}
