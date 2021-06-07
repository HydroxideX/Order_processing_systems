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
    private Store_functionality_implementation store;
    private ArrayList<Book_Order> cart;

    public User_Proxy() throws SQLException {
        store = new Store_functionality_implementation();
        cart = new ArrayList<>();
        store.init();
    }

    public ArrayList<Book_Order> get_cart() {
        return cart;
    }

    public boolean login(String user_name, String password) throws SQLException {
        User user = store.get_user(user_name, password);
        if (user != null) {
            current_user = user;
            cart.clear();
            return true;
        }
        return false;
    }


    public void search(SearchBookQuery sq) throws SQLException {
        store.search_for_book(sq);
    }

    public boolean add_book_to_cart(Book book, int copies) throws SQLException {
        OrderBuilder builder = new OrderBuilder();
        int all_copies = copies;
        for (Book_Order order : cart) {
            if (order.getISBN().equals(book.getISBN())) {
                all_copies += order.getCopies();
                break;
            }
        }
        if (store.get_available_book(book.getISBN()) < all_copies) {
            return false;
        }
        if (all_copies == copies) {
            builder.setCopies(copies);
            builder.setISBN(book.getISBN());
            builder.setTitle(book.getTitle());
            builder.setUser_name(current_user.getUser_name());
            cart.add(builder.build());

        } else {
            for (Book_Order order : cart) {
                if (order.getISBN().equals(book.getISBN())) {
                    order.setCopies(order.getCopies() + copies);
                    break;
                }
            }
        }
        return true;
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

    public CheckOutResult Checkout() throws SQLException {
        CheckOutResult result = new CheckOutResult();
        for (Book_Order order : cart) {
            try {
                store.modify_existing_book(order.getTitle(), order.getISBN(), -1 * order.getCopies());
                store.place_order(order);
                result.insert_record(order.getTitle(), true, store.get_available_book(order.getISBN()));
            } catch (SQLException e) {
                result.insert_record(order.getTitle(), false, store.get_available_book(order.getISBN()));
                e.printStackTrace();
            }
        }
        return result;
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

    public void add_book_to_store(Book book) throws SQLException {
        if (!is_manager()) return;
        store.add_new_book(book);
    }

    public void modify_book(Book book, int quantity) throws SQLException {
        if (!is_manager()) return;
        store.modify_existing_book(book, quantity);
    }

    public void place_order(Book_Order order) throws SQLException {
        if (!is_manager()) return;
        store.place_order(order);
    }

    public void confirm_order(Book_Order order) throws SQLException {
        if (!is_manager()) return;
        store.confirm_order(order);
    }

    public boolean Promote(String email) throws SQLException {
        if (!is_manager()) return false;
        return store.set_manager(email);
    }

    public ArrayList<Pair<String, Integer>> get_sales() throws SQLException {
        if (!is_manager()) return null;
        return store.get_sales();
    }

    public ArrayList<Pair<String, Integer>> get_top_users() throws SQLException {
        if (!is_manager()) return null;
        return store.get_top_users(5);
    }

    public ArrayList<Pair<String, Integer>> get_top_books() throws SQLException {
        if (!is_manager()) return null;
        return store.get_top_books(10);
    }


}
