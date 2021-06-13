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

public class UserPromotionForm extends Application {
    ComponentsBuilder componentsBuilder = new ComponentsBuilder();
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("User Promotion");
        GridPane gridPane = componentsBuilder.createFormPane(false);
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10,10,10,10));
        Button back = new Button();
        Button logout = new Button("Logout");
        HBox hBox = componentsBuilder.buildTopHBox(back, logout, primaryStage);
        vBox.getChildren().addAll(hBox, gridPane);
        addUIControls(gridPane);
        Scene scene = new Scene(vBox, 800, 500);
        componentsBuilder.init_stage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addUIControls(GridPane gridPane) {
        Label headerLabel = new Label("Promote User");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
        Label username_label = componentsBuilder.addLabel(gridPane, "Enter Username", 2, 0);
        TextField username = componentsBuilder.addTextField(gridPane,40,2,1);
        Button submitButton = componentsBuilder.build_center_button(gridPane,"Promote", 40, 100, 0, 4, 2, 1);

        submitButton.setOnAction(event -> {
            Controller controller = null;
            try {
                controller = Controller.get_instance();
                regex_matcher rm = new regex_matcher();
                if (rm.check_varchar(username.getText())) {
                    controller.Promote(username.getText());
                    controller.commit_transaction();
                    componentsBuilder.showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Success", "User Promoted Successfully");
                } else {
                    componentsBuilder.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "Please enter a username");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        });
    }

}

