package model;

import javafx.util.Pair;
import model.Schema.Book;
import model.Schema.Book_Order;
import model.Schema.User;
import org.omg.CORBA.INTERNAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class User_Proxy {
    private User current_user;
    private ArrayList<Book_Order> cart;

    public User_Proxy() throws SQLException {
        cart = new ArrayList<>();
    }

    public ArrayList<Book_Order> get_cart() {
        return cart;
    }

    public void set_user(User user) {
        this.current_user = user;
        cart.clear();
    }

    public String get_curr_username() {
        return current_user.getUser_name();
    }


    public int get_num_of_ordered_copies(Book book) {
        int copies = 0;
        for (Book_Order order : cart) {
            if (order.getISBN().equals(book.getISBN())) {
                copies = order.getCopies();
                break;
            }
        }
        return copies;
    }

    public boolean add_book_to_cart_new_order(Book book, int copies) throws SQLException {
        OrderBuilder builder = new OrderBuilder();
        builder.setCopies(copies);
        builder.setISBN(book.getISBN());
        builder.setTitle(book.getTitle());
        builder.setUser_name(current_user.getUser_name());
        cart.add(builder.build());
        return true;
    }

    public boolean add_book_to_cart_existing(Book book, int copies) {
        for (Book_Order order : cart) {
            if (order.getISBN().equals(book.getISBN())) {
                order.setCopies(order.getCopies() + copies);
                return true;
            }
        }
        return false;
    }


    public boolean remove_book(String ISBN, int copies) {
        for (Book_Order order : cart) {
            if (ISBN.equals(order.getISBN())) {
                if (order.getCopies() >= copies) {
                    if (order.getCopies() == copies) {
                        cart.remove(order);
                    } else {
                        order.setCopies(order.getCopies() - copies);
                    }
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }


    public boolean logout() {
        if (current_user == null) return false;
        current_user = null;
        cart.clear();
        return true;
    }

    public boolean is_manager() {
        if (current_user == null) {
            return false;
        }
        return current_user.is_manager();
    }



}
