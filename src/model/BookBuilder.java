package model;

import model.Schema.Book;

public class BookBuilder {
    private String ISBN = null;
    private String author = null;
    private String title = null;
    private String publisher_name = null;
    private String category = null;
    private Integer year = null;
    private Integer threshold = null;
    private Integer copies_available = null;
    private Float selling_price = null;

    public BookBuilder setISBN(String ISBN) {
        this.ISBN = ISBN;
        return this;
    }

    public BookBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public BookBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public BookBuilder setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
        return this;
    }

    public BookBuilder setCategory(String category) {
        this.category = category;
        return this;
    }

    public BookBuilder setYear(Integer year) {
        this.year = year;
        return this;
    }

    public BookBuilder setThreshold(Integer threshold) {
        this.threshold = threshold;
        return this;
    }

    public BookBuilder setCopies_available(Integer copies_available) {
        this.copies_available = copies_available;
        return this;
    }

    public BookBuilder setSelling_price(Float selling_price) {
        this.selling_price = selling_price;
        return this;
    }

    public Book build() {
        return new Book(ISBN, author, title, publisher_name, category, year, threshold, copies_available, selling_price);
    }
}
