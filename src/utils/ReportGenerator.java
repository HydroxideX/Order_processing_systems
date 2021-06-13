package utils;
import javafx.util.Pair;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;

public class ReportGenerator
{

    private int digits(int no){
        int digit = 0;
        while(no > 0){
            no /= 10;
            digit++;
        }
        return digit;
    }

    public void generateReport(String filename, String title_data, String header_data, String subheader, ArrayList<Pair<String, Integer>> array) {
        File htmlTemplateFile = new File("src/reports/template.html");
        String htmlString = null;
        try {
            htmlString = FileUtils.readFileToString(htmlTemplateFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String body = "";
        if(array != null) {
            for (int i = 0; i < array.size(); i++) {
                body += i + 1 + "- ";
                int spaces = 70 - digits(i+1) - 1;
                body += array.get(i).getKey();
                spaces -= array.get(i).getKey().length();
                for (int j = 0; j < spaces; j++) {
                    body += "&nbsp;";
                }
                body += Math.abs(array.get(i).getValue());
                body += "<br>";
                body += "<br>";
            }
        }
        htmlString = htmlString.replace("$title", title_data);
        htmlString = htmlString.replace("$header", header_data);
        htmlString = htmlString.replace("$subheader", subheader);
        htmlString = htmlString.replace("$body", body);
        File newHtmlFile = new File("src/reportsGenerated/"+filename);
        try {
            FileUtils.writeStringToFile(newHtmlFile, htmlString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
