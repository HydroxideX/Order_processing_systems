package model.Schema;

import utils.regex_matcher;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Book_Order {
    private String ISBN = "";
    private String title = "";
    private String user_name = "";
    private int copies;
    private java.sql.Date date_ordered;

    public Book_Order(String ISBN, String title, String user_name, int copies, java.sql.Date date_ordered) {
        this.ISBN = ISBN;
        this.title = title;
        this.user_name = user_name;
        this.copies = copies;
        this.date_ordered = date_ordered;
        if (this.date_ordered == null) {
            java.util.Date utilDate = new java.util.Date();
            this.date_ordered = new java.sql.Date(utilDate.getTime());
        }
    }

    public boolean is_valid() {
        regex_matcher rm = new regex_matcher();
        return rm.check_varchar(ISBN) & rm.check_varchar(title) &
                rm.check_varchar(user_name)
                & copies != -1 & date_ordered != null;
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

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public java.sql.Date getDate_ordered() {
        return date_ordered;
    }

    public void setDate_ordered(java.sql.Date date_ordered) {
        this.date_ordered = date_ordered;
    }
}
