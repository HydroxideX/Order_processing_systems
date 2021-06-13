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
import model.BookBuilder;
import model.Schema.Book;
import model.Schema.Book_Order;
import utils.String_utils;
import utils.TableTransferUtil;
import utils.regex_matcher;

import java.sql.SQLException;
import java.util.ArrayList;


public class CartAdditionForm extends Application {
    private Controller controller = null;
    private ArrayList<Book_Order> cart = null;
    private TableTransferUtil tableTransferUtil = new TableTransferUtil();

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
        Button add_to_cart = new Button("Add to Cart");
        Button remove_from_cart = new Button("Remove From Cart");
        TextField title = new TextField();
        title.setMinWidth(480);
        TextField copies = new TextField();
        copies.setMinWidth(480);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        HBox hBox = new HBox();
        Button back = new Button();
        Button logout = new Button("Logout");
        HBox topBar = componentsBuilder.buildTopHBox(back, logout, stage);
        HBox controlBar = new HBox();
        hBox.setSpacing(100);
        controlBar.setSpacing(5);
        controlBar.getChildren().addAll(title, copies, add_to_cart, remove_from_cart);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(topBar, controlBar, table);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
        add_to_cart.setOnAction(e -> {
            try {
                BookBuilder builder = new BookBuilder();
                String title_string = title.getText();
                String ISBN = controller.get_ISBN(title_string);
                float price = controller.get_price(title_string);
                builder.setTitle(title.getText());
                builder.setISBN(ISBN);
                builder.set_Selling_price(price);
                Book book = builder.build();
                String str = copies.getText();
                regex_matcher matcher = new regex_matcher();
                if (!matcher.check_int(str)) {
                    componentsBuilder.showAlert(Alert.AlertType.ERROR, stage.getScene().getWindow(), "Error!", "Enter number  ");
                    return;
                }
                String_utils utils = new String_utils();
                int cop = utils.String_to_int(str);
                if (cop <= 0) {
                    componentsBuilder.showAlert(Alert.AlertType.ERROR, stage.getScene().getWindow(), "Error!", "number must be positive  ");
                    return;
                }

                boolean add = controller.add_book_to_cart(book, utils.String_to_int(str));
                if (!add) {
                    componentsBuilder.showAlert(Alert.AlertType.ERROR, stage.getScene().getWindow(), "Error!", "BOOK doesn't exit with this copies  ");
                    return;
                }
                presentCartOnTable(cart, table);
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Error displaying Table");
            }
        });
        remove_from_cart.setOnAction(e -> {
            try {
                BookBuilder builder = new BookBuilder();
                String title_string = title.getText();
                String ISBN = controller.get_ISBN(title_string);
                builder.setTitle(title.getText());
                builder.setISBN(ISBN);
                Book book = builder.build();
                String str = copies.getText();
                regex_matcher matcher = new regex_matcher();
                if (!matcher.check_int(str)) {
                    componentsBuilder.showAlert(Alert.AlertType.ERROR, stage.getScene().getWindow(), "Error!", "Enter a positive number  ");
                    return;
                }
                String_utils utils = new String_utils();
                boolean remove_success = controller.remove_book(book.getISBN(), utils.String_to_int(str));
                if(!remove_success){
                    componentsBuilder.showAlert(Alert.AlertType.ERROR, stage.getScene().getWindow(), "Error!", "too many copies to remove");
                }
                presentCartOnTable(cart, table);
            } catch (Exception ex) {
                System.out.println("Error displaying Table");
            }
        });
    }

    private void presentCartOnTable(ArrayList<Book_Order> cart, TableView<Object[]> table){
        table.getColumns().clear();
        Object[][] x = tableTransferUtil.convertOrdersTOArray(cart);
        tableTransferUtil.updateTable(x, table);
    }
}