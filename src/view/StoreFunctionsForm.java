package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

public class StoreFunctionsForm extends Application  {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Store");
        GridPane gridPane = createRegistrationFormPane();
        addUIControls(gridPane);
        Scene scene = new Scene(gridPane, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private GridPane createRegistrationFormPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40, 40, 40, 40));
        gridPane.setHgap(20);
        gridPane.setVgap(15);
        return gridPane;
    }
    private Button buildButton(GridPane gridPane, String text, int row, int col, int height, int width, boolean def){
        Button btn= new Button(text);
        btn.setPrefHeight(height);
        btn.setDefaultButton(def);
        btn.setPrefWidth(width);
        gridPane.add(btn, col, row);
        return btn;
    }
    private void addUIControls(GridPane gridPane) {
        Label headerLabel = new Label("Store");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 1,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
        Button SearchBook = buildButton(gridPane, "Search for Book", 2, 1, 40, 200, true);
        Button buyBook = buildButton(gridPane, "Buy Book", 3, 1, 40, 200, true);
        Button shoppingCart = buildButton(gridPane, "Shopping Cart", 4, 1, 40, 200, true);
        Button checkout = buildButton(gridPane, "Checkout", 5, 1, 40, 200, true);
        Button addBook = buildButton(gridPane, "Add Book to Store", 2, 2, 40, 200, true);
        Button placeConfirmOrder = buildButton(gridPane, "Place/Confirm Order", 3, 2, 40, 200, true);
        Button promoteUser = buildButton(gridPane, "Promote User", 4, 2, 40, 200, true);
        Button generateReports = buildButton(gridPane, "Reports", 5, 2, 40, 200, true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
