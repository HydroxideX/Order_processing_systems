package tests;

import static org.junit.Assert.*;

import model.*;
import org.junit.Test;
import java.sql.SQLException;

public class store_functionality_test {
    Store_functionality sf = new Store_functionality_implementation();
    @Test
    public void testAdd(){
        try {
            sf.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BookBuilder bb = new BookBuilder().setISBN("ARgR")
                .setAuthor("hag").setCategory("science")
                .setCopies_available(15).setSelling_price((float)1656.2)
                .setPublisher_name("yahia").setThreshold(10).setTitle("annoyingorange2").setYear(1996);
        try {
            assertEquals("row changed.",sf.add_new_book(bb.build()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testModify() {
        try {
            sf.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BookBuilder bb = new BookBuilder().setISBN("ARR")
                .setAuthor("ha").setCategory("science")
                .setCopies_available(15).setSelling_price((float)1656.2)
                .setPublisher_name("yahia").setThreshold(15).setTitle("annoyingorange").setYear(1996);
        try {
            assertEquals("row changed.",sf.modify_existing_book(bb.build(), 10));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPlaceOrder() {
        try {
            sf.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            java.sql.Date date1 = java.sql.Date.valueOf("2001-10-1");
            OrderBuilder ob =  new OrderBuilder().setISBN("ARR")
                    .setCopies(15).setTitle("annoyingorange")
                    .setDate_ordered(date1)
                    .setUser_name("yahia");
            assertEquals("row changed.",sf.place_order(ob.build()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConfirmOrder() {
        try {
            sf.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            java.sql.Date date1 = java.sql.Date.valueOf("2001-10-1");
            OrderBuilder ob = new OrderBuilder().setISBN("ARR")
                    .setCopies(15).setTitle("annoyingorange")
                    .setDate_ordered(date1)
                    .setUser_name("yahia");
            assertEquals("row changed.",sf.confirm_order(ob.build()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearchBook(){
        try {
            sf.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SearchBookQuery sq = new SearchBookQuery().setISBN("ARR")
                .setAuthor("ha")
                .setCopies_needed(10).setSelling_price_upper((float)1696.2)
                .setTitle("annoyingorange").setYear_lower(1996).setYear_upper(1997);
        try {
            sf.search_for_book(sq);
            System.out.println("hello");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}