package model;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import model.Schema.Book_Order;

import java.util.Date;

public class OrderBuilder {
    private String ISBN = "";
    private String title = "";
    private String user_name = "";
    private Integer copies;
    private java.sql.Date date_ordered;
    private static OrderBuilder orderBuilder;

    private OrderBuilder() {

    }

    public static OrderBuilder get_instance() {
        if (orderBuilder == null) {
            orderBuilder = new OrderBuilder();
        }
        return orderBuilder;
    }

    public OrderBuilder setISBN(String ISBN) {
        this.ISBN = ISBN;
        return this;
    }

    public OrderBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public OrderBuilder setUser_name(String user_name) {
        this.user_name = user_name;
        return this;
    }

    public OrderBuilder setCopies(Integer copies) {
        this.copies = copies;
        return this;
    }

    public OrderBuilder setDate_ordered(java.sql.Date date_ordered) {
        this.date_ordered = date_ordered;
        return this;
    }

    public Book_Order build() {
        java.util.Date utilDate = new java.util.Date();
        this.date_ordered = new java.sql.Date(utilDate.getTime());
        return new Book_Order(ISBN, title, user_name, copies, date_ordered);
    }
}
