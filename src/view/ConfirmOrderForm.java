package view;
import controller.Controller;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.BookOrderQuery;
import model.OrderBuilder;
import model.Schema.Book_Order;
import utils.regex_matcher;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ConfirmOrderForm extends Application {
    Object[][] x;
    private Controller controller = null;
    {
        try {
            controller = Controller.get_instance();
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
        stage.setTitle("Confirm Order");
        table.setPrefWidth(1150);
        table.setPrefHeight(550);
        stage.setWidth(1200);
        stage.setHeight(700);
        stage.setResizable(false);
        Button confirm =new Button("Confirm Order");
        Label row_id_label = new Label("Row ID");
        row_id_label.setMinHeight(20);
        TextField row_id = new TextField();
        row_id.setMinWidth(950);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        HBox hBox=new HBox();
        Button back = new Button();
        Button logout = new Button("Logout");
        HBox topBar = componentsBuilder.buildTopHBox(back, logout, stage);
        HBox controlBar = new HBox();
        hBox.setSpacing(100);
        controlBar.setSpacing(15);
        controlBar.getChildren().addAll(row_id_label, row_id, confirm);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(topBar, controlBar, table);
        ((Group)scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
        BookOrderQuery oq = new BookOrderQuery();
        try {
            controller.get_manager_orders(oq);
            controller.commit_transaction();
        } catch (SQLException e) {
            System.out.println("SQL Error");
        }
        ArrayList<Book_Order> orders = oq.get_result_rows();
        table.getColumns().clear();
        x = convertOrdersTOArray(orders);
        SearchBookTableView.UpdateTable(x, table);
        confirm.setOnAction(e->{
            try {
                regex_matcher rm = new regex_matcher();
                String id = row_id.getText();
                if(rm.check_int(id) && Integer.parseInt(id) > 0 && Integer.parseInt(id) < x.length) {
                    int r = Integer.parseInt(id);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsed = format.parse((String)x[r][4]);
                    java.sql.Date sql = new java.sql.Date(parsed.getTime());
                    OrderBuilder ob = new OrderBuilder().setUser_name((String)x[r][3])
                            .setCopies(Integer.valueOf(String.valueOf(x[r][5])))
                            .setISBN((String)x[r][1]).setTitle((String)x[r][2]).setDate_ordered(sql);
                    controller.confirm_order(ob.build());
                    controller.commit_transaction();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Row ID");
                    alert.show();
                    return;
                }
                BookOrderQuery oq_temp = new BookOrderQuery();
                controller.get_manager_orders(oq_temp);
                controller.commit_transaction();
                ArrayList<Book_Order> orders_temp = oq_temp.get_result_rows();
                table.getColumns().clear();
                 x = convertOrdersTOArray(orders_temp);
                SearchBookTableView.UpdateTable(x, table);
            } catch (Exception ex) {
                System.out.println("Error displaying Table");
            }
        });
    }

    private Object [][] convertOrdersTOArray(ArrayList<Book_Order> orders) {
        return getObjectsTableFromOrders(orders, 1);
    }

    static Object[][] getObjectsTableFromOrders(ArrayList<Book_Order> orders, int x) {
        String[][] table = new String[orders.size()+1][5 + x];
        if(x == 1) {
            table[0][0] = "ID";
            table[0][1] = "ISBN";
            table[0][2] = "Title";
            table[0][3] = "User";
            table[0][4] = "Date";
            table[0][5] = "Quantity";
        } else {
            table[0][0] = "ISBN";
            table[0][1] = "Title";
            table[0][2] = "User";
            table[0][3] = "Date";
            table[0][4] = "Quantity";
        }
        int j = 0;
        for(int i = 0; i < orders.size(); i++){
            j = 0;
            if(x == 1) {
                table[i + 1][j++] = String.valueOf(i+1);
            }
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
}