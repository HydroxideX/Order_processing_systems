package view;

import com.mysql.cj.protocol.Resultset;
import controller.Controller;
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
import model.Schema.Book_Order;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;


public class CartAdditionForm extends Application {
    private Controller controller = null;
    private ArrayList<Book_Order> cart = null;
    {
        try {
            controller = Controller.get_instance();
            cart = controller.get_userProxy().get_cart();
        } catch (SQLException e) {
            System.out.println("Failed to load Controller;");
        }
    }
    ComponentsBuilder componentsBuilder = new ComponentsBuilder();
    public TableView<Object[]> table = new TableView<>();
    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        table.setEditable(true);
        Scene scene = new Scene(new Group());
        stage.setTitle("Shopping Cart");
        table.setPrefWidth(1150);
        table.setPrefHeight(550);
        stage.setWidth(1200);
        stage.setHeight(700);
        stage.setResizable(false);
        Button search =new Button("Add to Cart");
        Button add_to_cart =new Button("Remove From Cart");
        TextField title = new TextField();
        title.setMinWidth(480);
        TextField copies = new TextField();
        copies.setMinWidth(480);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        HBox hBox=new HBox();
        Button back = new Button();
        Button logout = new Button("Logout");
        HBox topBar = componentsBuilder.buildTopHBox(back, logout, stage);
        HBox searchBar = new HBox();
        hBox.setSpacing(100);
        searchBar.setSpacing(5);
        searchBar.getChildren().addAll(title, copies, search, add_to_cart);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(topBar, searchBar, table);
        ((Group)scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
        /*
        add_to_cart.setOnAction(e->{
            try {
                //method to transfer result set to 2d array of Objects
                table.getColumns().clear();
                Resultset temp =(Resultset)object;
                Object[][] x= convertResultSetTOArray(temp);
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
            } catch (Exception e) {
                System.out.println("Error displaying Table");
            }
        });
        */

    }


}


/*

 */