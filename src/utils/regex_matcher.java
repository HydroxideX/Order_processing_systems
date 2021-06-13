package utils;


import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regex_matcher {
    String[] category_values = {"science", "art", "religion", "history", "geography"};

    public boolean check_varchar(String s) {
        return s != null && s.length() > 0 && s.length() <= 30;
    }

    public boolean check_category(String category) {
        if (category == null) return false;
        for (String category_value : category_values) {
            if (category_value.compareTo(category) == 0) return true;
        }
        return false;
    }

    public boolean match_string(String s, String regex) {
        Pattern p = Pattern.compile(regex);//. represents single character
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public boolean check_year(String s) {
        if (s == null) return false;
        String year_regex = "^\\d{4}";
        return match_string(s, year_regex);
    }

    public boolean check_date(String s) {
        if (s == null) return false;
        String date_regex = "^\\d{4}-\\d{2}-\\d{2}$";
        return match_string(s, date_regex);
    }

    public boolean check_int(String s) {
        if (s == null) return false;
        String int_regex = "^([+]?\\d+)$";
        return match_string(s, int_regex);
    }

    public boolean check_float(String s) {
        if (s == null) return false;
        String float_regex = "^([+]?\\d+(\\.\\d+)?)$";
        return match_string(s, float_regex);
    }

    public boolean isWrapped(String str) {
        if (str.length() <= 1) return false;
        return str.charAt(0) == '\"' && str.charAt(str.length() - 1) == '\"';
    }

    public boolean is_empty(String str) {
        return str.matches(" *");
    }

    public boolean check_valid_year(Integer year) {
        if (year == null) return false;
        return year > 0 && year <= Calendar.getInstance().get(Calendar.YEAR);
    }

    public boolean check_credit_card(String s) {
        if (s == null) return false;
        String credit_card_regex = "^\\d{16}$";
        return match_string(s, credit_card_regex);
    }

    public boolean check_expiry_date(String s) {
        if (s == null) return false;
        String expiry_date_regex = "^((0[0-9])|(1[0-2]))\\/\\d{2}$";
        return match_string(s, expiry_date_regex);
    }
}
