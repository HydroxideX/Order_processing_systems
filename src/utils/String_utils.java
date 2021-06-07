package utils;

public class String_utils {
    public int String_to_int(String s){
        return Integer.parseInt(s);
    }

    public float String_to_float(String s) {
        return Float.parseFloat(s);
    }

    public java.sql.Date String_to_date(String s){
        return java.sql.Date.valueOf(s);
    }

    public String convert_to_lower(String s) {
        return s.toLowerCase();
    }
}
