package view;

import controller.Controller;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.SearchBookQuery;

import java.sql.SQLException;

public class StoreFunctionsForm extends Application  {
    ComponentsBuilder componentsBuilder = new ComponentsBuilder();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Store");
        GridPane gridPane = componentsBuilder.createFormPane(true);
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10,10,10,10));
        Button logout = new Button("Logout");
        HBox hBox = componentsBuilder.buildTopHBox(logout, primaryStage);
        vBox.getChildren().addAll(hBox, gridPane);
        addUIControls(gridPane, primaryStage);
        Scene scene = new Scene(vBox, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void addUIControls(GridPane gridPane, Stage primaryStage) throws SQLException {
        Label headerLabel = new Label("Store");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 1,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
        Button searchBook = componentsBuilder.buildButton(gridPane, "Search for Book", 2, 1, 40, 200, true);
        Button shoppingCart = componentsBuilder.buildButton(gridPane, "Shopping Cart", 3, 1, 40, 200, true);
        Button checkout = componentsBuilder.buildButton(gridPane, "Checkout", 4, 1, 40, 200, true);
        Button confirmOrder = componentsBuilder.buildButton(gridPane, "Confirm Order", 5, 1, 40, 200, true);
        Button addBook = componentsBuilder.buildButton(gridPane, "Add Book to Store", 2, 2, 40, 200, true);
        Button placeOrder = componentsBuilder.buildButton(gridPane, "Place Order", 3, 2, 40, 200, true);
        Button promoteUser = componentsBuilder.buildButton(gridPane, "Promote User", 4, 2, 40, 200, true);
        Button generateReports = componentsBuilder.buildButton(gridPane, "Reports", 5, 2, 40, 200, true);
        boolean is_manager = Controller.get_instance().get_userProxy().is_manager();
        if(!is_manager){
            addBook.setDisable(true);
            placeOrder.setDisable(true);
            promoteUser.setDisable(true);
            generateReports.setDisable(true);
            confirmOrder.setDisable(true);
        }


        searchBook.setOnAction(event -> {
            SearchBookForm gui=new SearchBookForm();
            try {
                gui.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        shoppingCart.setOnAction(event -> {
            CartAdditionForm gui=new CartAdditionForm();
            try {
                gui.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        checkout.setOnAction(event -> {
            CheckoutForm gui=new CheckoutForm();
            try {
                gui.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        addBook.setOnAction(event -> {
            BookAdditionForm gui=new BookAdditionForm();
            try {
                gui.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        placeOrder.setOnAction(event -> {
            OrderPlaceConfirmForm gui=new OrderPlaceConfirmForm();
            try {
                gui.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        confirmOrder.setOnAction(event -> {
            ConfirmOrderForm gui=new ConfirmOrderForm();
            try {
                gui.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        promoteUser.setOnAction(event -> {
            UserPromotionForm gui=new UserPromotionForm();
            try {
                gui.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        generateReports.setOnAction(event -> {
            GenerateReportsForm gui=new GenerateReportsForm();
            try {
                gui.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
