package controller;

import javafx.util.Pair;
import model.*;
import model.Schema.Book;
import model.Schema.Book_Order;
import model.Schema.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class Controller {
    private static Controller controller;
    private User_Proxy proxy;
    private Store_functionality_implementation store;

    private Controller() throws SQLException {
        proxy = new User_Proxy();
        store = new Store_functionality_implementation();
        store.init();
    }

    public static Controller get_instance() throws SQLException {
        if (controller == null) {
            controller = new Controller();

        }
        return controller;
    }

    public User_Proxy get_userProxy() {
        return proxy;
    }

    public void get_manager_orders(BookOrderQuery oq) throws SQLException {
        store.get_manager_orders(oq);
    }

    public void search(SearchBookQuery sq) throws SQLException {
        store.search_for_book(sq);
    }

    public CheckOutResult Checkout() throws SQLException {
        CheckOutResult result = new CheckOutResult();
        for (Book_Order order : proxy.get_cart()) {
            try {
                store.modify_existing_book(order.getTitle(), order.getISBN(), -1 * order.getCopies());
                store.place_order(order);
                result.insert_record(order.getTitle(), true, store.get_available_book_with_isbn(order.getISBN()), order.getCopies());
            } catch (SQLException e) {
                result.insert_record(order.getTitle(), false, store.get_available_book_with_isbn(order.getISBN()), 0);
                e.printStackTrace();
            }
        }

        return result;
    }

    public boolean add_book_to_cart(Book book, int copies) throws SQLException {
        int all_copies = copies + proxy.get_num_of_ordered_copies(book);
        if (all_copies > store.get_available_book_with_isbn(book.getISBN())) {
            return false;
        }
        if (all_copies == copies) {
            return proxy.add_book_to_cart_new_order(book, all_copies);
        } else {
            return proxy.add_book_to_cart_existing(book, all_copies);
        }
    }

    public void add_book_to_store(Book book) throws SQLException {
        if (!proxy.is_manager()) return;
        store.add_new_book(book);
    }


    public boolean login(String user_name, String password) throws SQLException {
        User user = store.get_user(user_name, password);
        if (user != null) {
            proxy.set_user(user);
            return true;
        }
        return false;
    }


    public void modify_book(Book book, int quantity) throws SQLException {
        if (!proxy.is_manager()) return;
        store.modify_existing_book(book, quantity);
    }

    public void place_order(Book_Order order) throws SQLException {
        if (!proxy.is_manager()) return;
        store.place_order(order);
    }

    public void confirm_order(Book_Order order) throws SQLException {
        if (!proxy.is_manager()) return;
        store.confirm_order(order);
    }

    public boolean Promote(String email) throws SQLException {
        if (!proxy.is_manager()) return false;
        return store.set_manager(email);
    }

    public ArrayList<Pair<String, Integer>> get_sales() throws SQLException {
        if (!proxy.is_manager()) return null;
        return store.get_sales();
    }

    public ArrayList<Pair<String, Integer>> get_top_users() throws SQLException {
        if (!proxy.is_manager()) return null;
        return store.get_top_users(5);
    }

    public ArrayList<Pair<String, Integer>> get_top_books() throws SQLException {
        if (!proxy.is_manager()) return null;
        return store.get_top_books(10);
    }

    public void remove_book(String ISBN, int copies) {

        proxy.remove_book(ISBN, copies);
    }

    public int get_available_book_with_title(String title) throws SQLException {
        title = "\"" + title + "\"";
        return store.get_available_book_with_title(title);
    }

    public int get_available_book_with_ISBN(String ISBN) throws SQLException {
        return store.get_available_book_with_isbn(ISBN);
    }

    public boolean add_new_book(Book book) {
        try {
            return store.add_new_book(book);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assert false;
        return false;
    }

    public boolean has_publisher_name(String name) {
        try {
            return store.has_publisher_name(name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
        // to stop the execution of addition
    }

    public boolean has_book_with_title(String title) {
        title = "\"" + title + "\"";
        try {
            return store.has_book_with_title(title);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
        // to stop the execution of addition

    }

    public boolean has_book_with_ISBN(String ISBN) {
        ISBN = "\"" + ISBN + "\"";
        try {
            return store.has_book_with_ISBN(ISBN);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
        // to stop the execution of addition
    }
}
