package model;

import model.Schema.Book;
import model.Schema.User;

import java.sql.SQLException;
import java.util.Vector;

public class User_Proxy {
    private User current_user;
    private Store_functionality_implementation store;
    private Vector<Book> cart;

    User_Proxy() throws SQLException {
        store = new Store_functionality_implementation();
        store.init();
    }

    boolean login(String user_name, String password) throws SQLException {
        User user = store.get_user(user_name, password);
        if (user != null) {
            current_user = user;
            cart.clear();
            return true;
        }
        return false;
    }

    void search(Book book) {
        return;
    }

    void add_book(Book book) {
        cart.add(book);
    }

    boolean remove_book(Book book) {
        return cart.remove(book);
    }

    boolean Checkout() {
        return false;
    }

    boolean logout() {
        if (current_user == null) return false;
        current_user = null;
        cart.clear();
        return true;
    }
    

}
