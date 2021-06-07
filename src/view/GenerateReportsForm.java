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

public class GenerateReportsForm extends Application  {
    ComponentsBuilder componentsBuilder = new ComponentsBuilder();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Reports");
        GridPane gridPane = componentsBuilder.createFormPane(true);
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10,10,10,10));
        Button back = new Button();
        Button logout = new Button("Logout");
        HBox hBox = componentsBuilder.buildTopHBox(back, logout);
        vBox.getChildren().addAll(hBox, gridPane);
        addUIControls(gridPane);
        Scene scene = new Scene(vBox, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void addUIControls(GridPane gridPane) {
        Label headerLabel = new Label("Reports");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 1,0,2,1);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
        GridPane.setHalignment(headerLabel,HPos.CENTER);
        Button sales = componentsBuilder.buildButton(gridPane, "Total Book Sales", 2, 1, 40, 200, true);
        Button topCustomers = componentsBuilder.buildButton(gridPane, "Top Five Customers", 3, 1, 40, 200, true);
        Button topBooks = componentsBuilder.buildButton(gridPane, "Top Ten Books", 4, 1, 40, 200, true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
