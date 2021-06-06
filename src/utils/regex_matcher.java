package utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regex_matcher {
    String [] category_values = {"Science, Art, religion ,History, Geography"};

    public boolean check_varchar(String s) {
        return s.length() > 0 && s.length() <= 30;
    }

    public boolean check_category(String category) {
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
        String date_regex = "^\\d{2}-\\d{2}-\\d{4}$";
        return match_string(s, date_regex);
    }

    public boolean check_int(String s) {
        String int_regex = "^([+]?\\d+)$";
        return match_string(s, int_regex);
    }

    public boolean check_float(String s) {
        String float_regex = "^([+]?\\d+\\.?\\d*)$";
        return match_string(s, float_regex);
    }
}
