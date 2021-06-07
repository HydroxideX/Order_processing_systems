package tests;

import static org.junit.Assert.*;

import model.*;
import org.junit.Test;

import java.sql.SQLException;

public class User_test {
    @Test
    public void test_login() throws SQLException {
        User_Proxy user_proxy = new User_Proxy();
        assert (user_proxy.login("zayadi", "12345"));
    }

    @Test
    public void test_add_book_to_cart() throws SQLException {
        User_Proxy user_proxy = new User_Proxy();
        user_proxy.login("zayadi", "12345");
        BookBuilder builder = BookBuilder.getInstance();
        builder.setTitle("how to kill myself?");
        builder.setISBN("1");
        assert (user_proxy.add_book_to_cart(builder.build(), 5));
        assert (user_proxy.get_cart().size() == 1);
        assert (user_proxy.get_cart().get(0).getISBN() == "1");
        assert (user_proxy.get_cart().get(0).getCopies() == 5);
    }

    @Test
    public void test_add_book_to_cart_overflow() throws SQLException {
        User_Proxy user_proxy = new User_Proxy();
        user_proxy.login("zayadi", "12345");
        BookBuilder builder = BookBuilder.getInstance();
        builder.setTitle("how to kill myself?");
        builder.setISBN("1");
        assert (!user_proxy.add_book_to_cart(builder.build(), 100));
        assert (user_proxy.get_cart().size() == 0);
    }

    @Test
    public void test_add_book_to_cart_overflow_on_steps() throws SQLException {
        User_Proxy user_proxy = new User_Proxy();
        user_proxy.login("zayadi", "12345");
        BookBuilder builder = BookBuilder.getInstance();
        builder.setTitle("how to kill myself?");
        builder.setISBN("1");
        assert (user_proxy.add_book_to_cart(builder.build(), 5));
        assert (user_proxy.get_cart().size() == 1);
        assert (!user_proxy.add_book_to_cart(builder.build(), 1));
        assert (user_proxy.get_cart().size() == 1);
    }

    @Test
    public void test_remove_book_not_all() throws SQLException {
        User_Proxy user_proxy = new User_Proxy();
        user_proxy.login("zayadi", "12345");
        BookBuilder builder = BookBuilder.getInstance();
        builder.setTitle("how to kill myself?");
        builder.setISBN("1");
        assert (user_proxy.add_book_to_cart(builder.build(), 4));
        assert (user_proxy.get_cart().size() == 1);
        user_proxy.remove_book("1", 3);
        assert (user_proxy.get_cart().size() == 1);
        assert (user_proxy.get_cart().get(0).getCopies() == 1);
    }

    @Test
    public void test_remove_book_all() throws SQLException {
        User_Proxy user_proxy = new User_Proxy();
        user_proxy.login("zayadi", "12345");
        BookBuilder builder = BookBuilder.getInstance();
        builder.setTitle("how to kill myself?");
        builder.setISBN("1");
        assert (user_proxy.add_book_to_cart(builder.build(), 4));
        assert (user_proxy.get_cart().size() == 1);
        user_proxy.remove_book("1", 4);
        assert (user_proxy.get_cart().size() == 0);
    }

    @Test
    public void test_checkout() throws SQLException {
        User_Proxy user_proxy = new User_Proxy();
        user_proxy.login("zayadi", "12345");
        BookBuilder builder = BookBuilder.getInstance();
        builder.setTitle("how to kill myself?");
        builder.setISBN("1");
        assert (user_proxy.add_book_to_cart(builder.build(), 3));
        assert (user_proxy.get_cart().size() == 1);
        CheckOutResult result = user_proxy.Checkout();
        Store_functionality_implementation store = new Store_functionality_implementation();
        store.init();
        int cnt = store.get_available_book("1");
        System.out.println(cnt);
        assert (cnt == 2 );
    }
    @Test
    public void test_promote() throws SQLException {
        User_Proxy user_proxy = new User_Proxy();
        user_proxy.login("zayadi", "12345");
        assert (user_proxy.Promote("\"zozo@gmail.com\""));
    }
    




}
