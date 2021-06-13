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
import utils.TableTransferUtil;
import utils.regex_matcher;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ConfirmOrderForm extends Application {
    TableTransferUtil tableTransferUtil = new TableTransferUtil();
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
        componentsBuilder.init_stage(stage);
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
        x = tableTransferUtil.convertOrdersTOArray(orders);
        tableTransferUtil.updateTable(x, table);
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
                 x = tableTransferUtil.convertOrdersTOArray(orders_temp);
                tableTransferUtil.updateTable(x, table);
            } catch (Exception ex) {
                System.out.println("Error displaying Table");
            }
        });
    }


}