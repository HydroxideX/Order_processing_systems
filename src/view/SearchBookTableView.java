package view;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Schema.Book;
import java.util.ArrayList;
import java.util.Arrays;


public class SearchBookTableView extends Application {
    ComponentsBuilder componentsBuilder = new ComponentsBuilder();
    ArrayList <Book> books;
    public TableView<Object[]> table = new TableView<>();
    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        table.setEditable(true);
        Scene scene = new Scene(new Group());
        stage.setTitle("Search Result");
        table.setPrefWidth(1150);
        table.setPrefHeight(550);
        stage.setWidth(1200);
        stage.setHeight(700);
        stage.setResizable(false);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        Button back = new Button();
        Button logout = new Button("Logout");
        HBox topBar = componentsBuilder.buildTopHBox(back, logout, stage);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(topBar, table);
        ((Group)scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
        try {
            table.getColumns().clear();
            Object[][] x= convertBookArrayListTo2DArray(books);
            UpdateTable(x, table);
        } catch (Exception ex) {
            System.out.println("Error displaying Table");
        }

    }

    static void UpdateTable(Object[][] x, TableView<Object[]> table) {
        ObservableList<Object[]> data = FXCollections.observableArrayList();
        Object[][] y = new Object[x.length][x[0].length];
        for(int i=0;i<x.length;i++)
        {
            for(int j=0;j<x[i].length;j++)
            {
                y[i][j]=x[i][j].toString();
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


    private Object [][] convertBookArrayListTo2DArray(ArrayList<Book> books) {
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
}