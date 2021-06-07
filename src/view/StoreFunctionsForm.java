package view;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class StoreFunctionsForm extends Application  {
    ComponentsBuilder componentsBuilder = new ComponentsBuilder();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Store");
        GridPane gridPane = componentsBuilder.createFormPane(true);
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10,10,10,10));
        Button logout = new Button("Logout");
        HBox hBox = componentsBuilder.buildTopHBox(logout);
        vBox.getChildren().addAll(hBox, gridPane);
        addUIControls(gridPane);
        Scene scene = new Scene(vBox, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addUIControls(GridPane gridPane) {
        Label headerLabel = new Label("Store");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 1,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
        Button SearchBook = componentsBuilder.buildButton(gridPane, "Search for Book", 2, 1, 40, 200, true);
        Button buyBook = componentsBuilder.buildButton(gridPane, "Buy Book", 3, 1, 40, 200, true);
        Button shoppingCart = componentsBuilder.buildButton(gridPane, "Shopping Cart", 4, 1, 40, 200, true);
        Button checkout = componentsBuilder.buildButton(gridPane, "Checkout", 5, 1, 40, 200, true);
        Button addBook = componentsBuilder.buildButton(gridPane, "Add Book to Store", 2, 2, 40, 200, true);
        Button placeConfirmOrder = componentsBuilder.buildButton(gridPane, "Place/Confirm Order", 3, 2, 40, 200, true);
        Button promoteUser = componentsBuilder.buildButton(gridPane, "Promote User", 4, 2, 40, 200, true);
        Button generateReports = componentsBuilder.buildButton(gridPane, "Reports", 5, 2, 40, 200, true);
        boolean is_manager = false;
        if(!is_manager){
            addBook.setDisable(true);
            placeConfirmOrder.setDisable(true);
            promoteUser.setDisable(true);
            generateReports.setDisable(true);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
