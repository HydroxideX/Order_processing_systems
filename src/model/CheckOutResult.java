package model;

import model.Schema.Book;

import java.util.ArrayList;

public class CheckOutResult {
    private ArrayList<String> titles;
    private ArrayList<Boolean> IS_sell;
    private ArrayList<Integer> available;

    CheckOutResult() {
        titles = new ArrayList<>();
        IS_sell = new ArrayList<>();
        available = new ArrayList<>();
    }

    public int get_size() {
        return titles.size();
    }

    void insert_record(String title, boolean sell, int remain) {
        titles.add(title);
        IS_sell.add(sell);
        available.add(remain);
    }

    public String get_title(int index) {
        return titles.get(index);
    }

    public Boolean sell(int index) {
        return IS_sell.get(index);
    }

    public int get_available(int index) {
        return available.get(index);
    }
}
