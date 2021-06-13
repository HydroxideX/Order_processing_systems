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
import utils.regex_matcher;

import java.sql.SQLException;

public class CheckoutForm extends Application {
    ComponentsBuilder componentsBuilder = new ComponentsBuilder();
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Checkout Form");
        GridPane gridPane = componentsBuilder.createFormPane(false);
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10,10,10,10));
        Button back = new Button();
        Button logout = new Button("Logout");
        HBox hBox = componentsBuilder.buildTopHBox(back, logout, primaryStage);
        vBox.getChildren().addAll(hBox, gridPane);
        addUIControls(gridPane);
        Scene scene = new Scene(vBox, 800, 640);
        componentsBuilder.init_stage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addUIControls(GridPane gridPane) {
        Label headerLabel = new Label("Checkout");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
        Label credit_card_label = componentsBuilder.addLabel(gridPane, "Credit Card:", 2, 0);
        TextField credit_card = componentsBuilder.addTextField(gridPane, 40, 2, 1);
        Label expiry_date_label = componentsBuilder.addLabel(gridPane, "Expiry Date", 3, 0);
        TextField expiry_date= componentsBuilder.addTextField(gridPane, 40, 3, 1);
        Button checkout = componentsBuilder.build_center_button(gridPane, "Checkout Order", 40, 100, 0,4,2, 1);
        checkout.setOnAction(event -> {
            regex_matcher rm = new regex_matcher();
            if (!rm.check_credit_card(credit_card.getText())) {
                componentsBuilder.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "invalid Credit Card Value!");
            }
            else if(! rm.check_expiry_date(expiry_date.getText())) {
                componentsBuilder.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "invalid Expiry Date Value!");
            } else {

            }


        });
    }

}

