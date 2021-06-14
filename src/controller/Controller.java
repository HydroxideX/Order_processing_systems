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
        boolean SELL = true;
        float price = 0 ;
        for (Book_Order order : proxy.get_cart()) {
            try {
                order.setCopies(-1 * order.getCopies() );
                store.modify_existing_book(order.getTitle(), order.getISBN(),  order.getCopies());
                store.confirm_sales(order);
                result.insert_record(order.getTitle(), true, store.get_available_book_with_isbn(order.getISBN()),-1 *  order.getCopies());
                price+=order.get_total_price();
            } catch (SQLException e) {
                SELL = false;
                result.insert_record(order.getTitle(), false, store.get_available_book_with_isbn(order.getISBN()), 0);
            }
        }
        if (SELL) {
            store.commit_transaction();
            proxy.get_cart().clear();
        } else {
            store.rollback_transaction();
        }
        result.set_price(-1*price);
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
            return proxy.add_book_to_cart_existing(book, copies);
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

    public boolean place_order(Book_Order order) throws SQLException {
        if (!proxy.is_manager()) return false;
        return store.place_order(order);
    }

    public void confirm_order(Book_Order order) throws SQLException {
        if (!proxy.is_manager()) return;
        store.confirm_order(order);
    }

    public boolean Promote(String username) throws SQLException {
        if (!proxy.is_manager()) return false;
        return store.set_manager(username);
    }

    public ArrayList<Pair<String, Integer>> get_sales() throws SQLException {
        if (!proxy.is_manager()) return null;
        return store.get_sales();
    }

    public String get_username() {
        return proxy.get_curr_username();
    }

    public ArrayList<Pair<String, Integer>> get_top_users() throws SQLException {
        if (!proxy.is_manager()) return null;
        return store.get_top_users(5);
    }

    public ArrayList<Pair<String, Integer>> get_top_books() throws SQLException {
        if (!proxy.is_manager()) return null;
        return store.get_top_books(10);
    }

    public boolean remove_book(String ISBN, int copies) {
        return proxy.remove_book(ISBN, copies);
    }

    public int get_available_book_with_title(String title) throws SQLException {
        store.wrap(title);
        return store.get_available_book_with_title(title);
    }

    public int get_available_book_with_ISBN(String ISBN) throws SQLException {
        store.wrap(ISBN);
        return store.get_available_book_with_isbn(ISBN);
    }

    public boolean confirm_sales(Book_Order order) throws SQLException {
        return store.confirm_sales(order);
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
        store.wrap(title);
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

    public void commit_transaction() throws SQLException {
        store.commit_transaction();
    }

    public void rollback_transaction() throws SQLException {
        store.rollback_transaction();
    }

    public String get_ISBN(String title_string) throws SQLException {
        return store.get_ISBN(title_string);
    }

    public boolean is_cart_empty() {
        return proxy.get_cart().size() == 0;
    }

    public float get_price(String title_string) throws SQLException {
        return store.get_price(title_string);
    }
}
