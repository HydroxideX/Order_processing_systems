package utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class String_Converter {
    public int String_to_year(String s){
        return Integer.parseInt(s);
    }

    public float String_to_float(String s) {
        return Float.parseFloat(s);
    }

    public Date String_to_date(String s){
        Date date1= null;
        try {
            date1 = new SimpleDateFormat("dd-MM-yyyy").parse(s);
        } catch (ParseException e) {
            System.out.println("Date parsing failed in utils/String_converter");
        }
        return date1;
    }
}
