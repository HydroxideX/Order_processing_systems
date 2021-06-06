package model;

import model.Schema.Book;
import model.Schema.User;

import java.sql.SQLException;

public class User_Proxy {
    private User current_user;
    private Store_functionality_implementation store;

    User_Proxy() throws SQLException {
        store = new Store_functionality_implementation();
        store.init();
    }

    boolean login(String user_name, String password) throws SQLException {
        User user = store.get_user(user_name, password);
        if (user != null) {
            current_user = user;
            return true;
        }
        return false;
    }

    vector<>search(Book book) {
        return;
    }

}
