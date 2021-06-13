package model.Schema;

import utils.regex_matcher;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Book_Order {
    private String ISBN = "";
    private String title = "";
    private String user_name = "";
    private Integer copies;
    private java.sql.Date date_ordered;
    private float price = 0;

    public Book_Order(String ISBN, String title, String user_name, Integer copies, java.sql.Date date_ordered, float price) {
        this.ISBN = ISBN;
        this.title = title;
        this.user_name = user_name;
        this.copies = copies;
        this.date_ordered = date_ordered;
        this.price = price;
        if (this.date_ordered == null) {
            java.util.Date utilDate = new java.util.Date();
            this.date_ordered = new java.sql.Date(utilDate.getTime());
        }
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
        assert copies > 0;
        this.copies = copies;
    }

    public java.sql.Date getDate_ordered() {
        return date_ordered;
    }

    public void setDate_ordered(java.sql.Date date_ordered) {
        this.date_ordered = date_ordered;
    }

    public float get_total_price() {
        return price * copies;
    }

}
