package model;

import model.Schema.Book;
import utils.regex_matcher;
import java.util.ArrayList;

public class SearchBookQuery {
    private String ISBN =  null;
    private String author =  null;
    private String title =  null;
    private String publisher_name =  null;
    private String category = null;
    private Integer year_lower = null;
    private Integer year_upper = null;
    private Integer copies_needed = null;
    private Float selling_price_lower = null;
    private Float selling_price_upper = null;

    private ArrayList <Book> result_rows = new ArrayList<>();

    public void add_to_result(Book book) {
        result_rows.add(book);
    }

    public ArrayList<Book> get_result_rows(){
        return result_rows;
    }

    private boolean has_valid_value(){
        regex_matcher rm = new regex_matcher();
        return rm.check_varchar(ISBN) | rm.check_varchar(author) | rm.check_varchar(title) |
                rm.check_varchar(publisher_name) | rm.check_category(category) | rm.check_valid_year(year_lower)
                | rm.check_valid_year(year_upper) |
                copies_needed != null | selling_price_lower != null | getSelling_price_upper() != null;
    }

    public String build_where_statement(){
        if(!this.has_valid_value()) return "";
        String where_stmt = " WHERE ";
        regex_matcher rm = new regex_matcher();
        if(rm.check_varchar(getISBN())) {
            where_stmt += " ISBN = '" + getISBN() + "' AND";
        }
        if(rm.check_varchar(getTitle())) {
            where_stmt += " TITLE = '" + getTitle() + "' AND";
        }
        if(rm.check_varchar(getCategory())) {
            where_stmt += " CATEGORY = '" + getCategory() + "' AND";
        }
        if(rm.check_varchar(getAuthor())) {
            where_stmt += " AUTHOR = '" + getAuthor() + "' AND";
        }
        if(rm.check_varchar(getPublisher_name())) {
            where_stmt += " PUBLISHER_NAME = '" + getPublisher_name() + "' AND";
        }
        if(getYear_lower() != null) {
            where_stmt += " PUBLICATION_YEAR >= '" + getYear_lower() + "' AND";
        }
        if(getYear_upper() !=  null) {
            where_stmt += " PUBLICATION_YEAR <= '" + getYear_upper() + "' AND";
        }
        if(getCopies_needed() !=  null) {
            where_stmt += " copies >= '" + getCopies_needed() + "' AND";
        }
        if(getSelling_price_upper() !=  null) {
            where_stmt += " selling_price <= '" + getSelling_price_upper()+ "' AND";
        }
        if(getSelling_price_lower() != null) {
            where_stmt += " selling_price >= '" + getSelling_price_lower()+ "' AND";
        }
        where_stmt = where_stmt.substring(0,where_stmt.length()-3);
        return where_stmt;
    }

    public String getISBN() {
        return ISBN;
    }

    public SearchBookQuery setISBN(String ISBN) {
        this.ISBN = ISBN;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public SearchBookQuery setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SearchBookQuery setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPublisher_name() {
        return publisher_name;
    }

    public SearchBookQuery setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public SearchBookQuery setCategory(String category) {
        this.category = category;
        return this;
    }

    public Integer getYear_lower() {
        return year_lower;
    }

    public SearchBookQuery setYear_lower(Integer year_lower) {
        this.year_lower = year_lower;
        return this;
    }

    public Integer getYear_upper() {
        return year_upper;
    }

    public SearchBookQuery setYear_upper(Integer year_upper) {
        this.year_upper = year_upper;
        return this;
    }

    public Integer getCopies_needed() {
        return copies_needed;
    }

    public SearchBookQuery setCopies_needed(Integer copies_needed) {
        this.copies_needed = copies_needed;
        return this;
    }

    public Float getSelling_price_lower() {
        return selling_price_lower;
    }

    public SearchBookQuery setSelling_price_lower(Float selling_price_lower) {
        this.selling_price_lower = selling_price_lower;
        return this;
    }

    public Float getSelling_price_upper() {
        return selling_price_upper;
    }

    public SearchBookQuery setSelling_price_upper(Float selling_price_upper) {
        this.selling_price_upper = selling_price_upper;
        return this;
    }
}
