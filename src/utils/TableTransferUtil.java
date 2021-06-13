package utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import model.Schema.Book;
import model.Schema.Book_Order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class TableTransferUtil {
    public Object [][] convertOrdersTOArray(ArrayList<Book_Order> orders) {
        String[][] table = new String[orders.size()+1][6];
        table[0][0] = "ID";
        table[0][1] = "ISBN";
        table[0][2] = "Title";
        table[0][3] = "User";
        table[0][4] = "Date";
        table[0][5] = "Quantity";
        int j = 0;
        for(int i = 0; i < orders.size(); i++){
            j = 0;
            table[i + 1][j++] = String.valueOf(i+1);
            table[i+1][j++] = orders.get(i).getISBN();
            table[i+1][j++] = orders.get(i).getTitle();
            table[i+1][j++] = orders.get(i).getUser_name();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = dateFormat.format(orders.get(i).getDate_ordered());
            table[i+1][j++] = strDate;
            table[i+1][j++] = String.valueOf(orders.get(i).getCopies());
        }
        return table;
    }

    public Object [][] convertBookArrayListTo2DArray(ArrayList<Book> books) {
        String[][] table = new String[books.size()+1][9];
        table[0][0] = "ISBN";
        table[0][1] = "Title";
        table[0][2] = "Author";
        table[0][3] = "Publisher";
        table[0][4] = "Category";
        table[0][5] = "Year";
        table[0][6] = "Selling Price";
        table[0][7] = "Threshold";
        table[0][8] = "Quantity";
        for(int i = 0; i < books.size(); i++){
            table[i+1][0] = books.get(i).getISBN();
            table[i+1][1] = books.get(i).getTitle();
            table[i+1][2] = books.get(i).getAuthor();
            table[i+1][3] = books.get(i).getPublisher_name();
            table[i+1][4] = books.get(i).getCategory();
            table[i+1][5] = String.valueOf(books.get(i).getYear());
            table[i+1][6] = String.valueOf(books.get(i).getSelling_price());
            table[i+1][7] = String.valueOf(books.get(i).getThreshold());
            table[i+1][8] = String.valueOf(books.get(i).getCopies_available());
        }
        return table;
    }

    public void updateTable(Object[][] x, TableView<Object[]> table) {
        ObservableList<Object[]> data = FXCollections.observableArrayList();
        Object[][] y = new Object[x.length][x[0].length];
        for(int i=0;i<x.length;i++)
        {
            for(int j=0;j<x[i].length;j++)
            {
                if(x[i][j] == null) y[i][j] = " ";
                else y[i][j]=x[i][j].toString();
            }
        }
        data.addAll(Arrays.asList(y));
        data.remove(0);
        for (int i = 0; i < x[0].length; i++)
        {
            TableColumn tc = new TableColumn(x[0][i].toString());
            tc.setMinWidth(150);
            final int colNo = i;
            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object[], Object>, SimpleStringProperty>() {
                @Override
                public SimpleStringProperty call(TableColumn.CellDataFeatures<Object[], Object> p) {
                    return new SimpleStringProperty((String) p.getValue()[colNo]);
                }
            });
            table.getColumns().add(tc);

        }
        table.setItems(data);
    }
}
