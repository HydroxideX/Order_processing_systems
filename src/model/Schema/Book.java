package model.Schema;
import utils.regex_matcher;
public class Book {
    private String ISBN =  null;
    private String author =  null;
    private String title =  null;
    private String publisher_name =  null;
    private String category = null;
    private int year = -1;
    private int threshold = -1;
    private int copies_available = -1;
    private float selling_price = -1;

    public Book(String ISBN, String author, String title, String publisher_name,
                String category, int year, int threshold, int copies_available,
                float selling_price) {
        this.ISBN = ISBN;
        this.author = author;
        this.title = title;
        this.publisher_name = publisher_name;
        this.category = category;
        this.year = year;
        this.threshold = threshold;
        this.copies_available = copies_available;
        this.selling_price = selling_price;
    }

    public boolean is_valid() {
        regex_matcher rm = new regex_matcher();
        return rm.check_varchar(ISBN) & rm.check_varchar(author) & rm.check_varchar(title) &
                rm.check_varchar(publisher_name) & rm.check_category(category) & rm.check_valid_year(year) &
                threshold != -1 & copies_available != -1 & selling_price != -1;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher_name() {
        return publisher_name;
    }

    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getCopies_available() {
        return copies_available;
    }

    public void setCopies_available(int copies_available) {
        this.copies_available = copies_available;
    }

    public float getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(float selling_price) {
        this.selling_price = selling_price;
    }
}
