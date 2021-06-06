package model.Scema;

import java.util.Date;

public class Book_Order {
    private String ISBN = "";
    private String title = "";
    private String user_name = "";
    private int copies;
    private Date date_ordered;

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

    public Date getDate_ordered() {
        return date_ordered;
    }

    public void setDate_ordered(Date date_ordered) {
        this.date_ordered = date_ordered;
    }
}
