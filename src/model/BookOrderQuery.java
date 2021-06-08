package model;

import model.Schema.Book;
import model.Schema.Book_Order;

import java.sql.Date;
import java.util.ArrayList;

public class BookOrderQuery {
    private String ISBN = "";
    private String title = "";
    private String user_name = "";
    private Integer copies;
    private java.sql.Date date_ordered;
    private ArrayList <Book_Order> result_rows = new ArrayList<>();

    public void add_to_result(Book_Order order) {
        result_rows.add(order);
    }

    public ArrayList<Book_Order> get_result_rows(){
        return result_rows;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public Date getDate_ordered() {
        return date_ordered;
    }

    public void setDate_ordered(Date date_ordered) {
        this.date_ordered = date_ordered;
    }

}
