package utils;


import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regex_matcher {
    String [] category_values = {"science", "art", "religion" ,"history", "geography"};

    public boolean check_varchar(String s) {
        return s != null && s.length() > 0 && s.length() <= 30;
    }

    public boolean check_category(String category) {
        if(category == null) return false;
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
        String year_regex = "^\\d{4}";
        return match_string(s, year_regex);
    }

    public boolean check_date(String s) {
        String date_regex = "^\\d{4}-\\d{2}-\\d{2}$";
        return match_string(s, date_regex);
    }

    public boolean check_int(String s) {
        String int_regex = "^([+]?\\d+)$";
        return match_string(s, int_regex);
    }

    public boolean check_float(String s) {
        String float_regex = "^([+]?\\d+(\\.\\d+)?)$";
        return match_string(s, float_regex);
    }

    public boolean check_valid_year(int year) {
        return year>0 && year <= Calendar.getInstance().get(Calendar.YEAR);
    }

    public boolean check_credit_card(String s) {
        String credit_card_regex = "^\\d{16}$";
        return match_string(s, credit_card_regex);
    }

    public boolean check_expiry_date(String s){
        String expiry_date_regex = "^((0[0-9])|(1[0-2]))\\/\\d{2}$";
        return match_string(s, expiry_date_regex);
    }
}
