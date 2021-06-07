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

import java.sql.SQLException;

public class LoginForm extends Application {
    ComponentsBuilder componentsBuilder = new ComponentsBuilder();
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setWidth(1200);
        primaryStage.setHeight(700);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Login Form");
        GridPane gridPane = componentsBuilder.createFormPane(false);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(gridPane);
        addUIControls(gridPane,primaryStage);
        Scene scene = new Scene(vBox, 800, 640);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addUIControls(GridPane gridPane,Stage primaryStage) {
        Label headerLabel = new Label("Log In");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
        Label username_label = componentsBuilder.addLabel(gridPane,"Username ", 2, 0);
        TextField username = componentsBuilder.addTextField(gridPane, 40, 2, 1);
        Label password_label = componentsBuilder.addLabel(gridPane,"Password ", 3, 0);
        PasswordField password = componentsBuilder.addPasswordField(gridPane, 40, 3, 1);
        Button Login = componentsBuilder.build_center_button(gridPane, "Login", 40, 100, 0, 4, 2, 1);
        Login.setOnAction(event -> {
            try {
                boolean can = Controller.get_instance().login(username.getText(),password.getText());
                if(can){
                    System.out.println("go");
                    StoreFunctionsForm gui=new StoreFunctionsForm();
                    gui.start(primaryStage);
                }else{
                    username.setText("");
                    password.setText("");
                    componentsBuilder.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "invalid user data");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}

