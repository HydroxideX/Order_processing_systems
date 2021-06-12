package view;

import controller.Controller;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class ComponentsBuilder {
    public Button buildButton(GridPane gridPane, String text, int row, int col, int height, int width, boolean def){
        Button btn= new Button(text);
        btn.setPrefHeight(height);
        btn.setDefaultButton(def);
        btn.setPrefWidth(width);
        gridPane.add(btn, col, row);
        return btn;
    }

    public Button buildButton(HBox hBox, String text, int height, int width, boolean def){
        Button btn= new Button(text);
        btn.setPrefHeight(height);
        btn.setDefaultButton(def);
        btn.setPrefWidth(width);
        hBox.getChildren().add(btn);
        return btn;
    }

    public HBox buildTopHBox(Button back, Button logout, Stage primaryStage){
        HBox hBox = new HBox();
        HBox h1 = buildTopHBox(logout,primaryStage);
        hBox.setSpacing(10);
        Image image = null;
        try {
            image = new Image(new FileInputStream("Resources/back.png"));
        } catch (FileNotFoundException e) {
            System.out.println("Can't Read Button Image");
        }
        back.setGraphic(new ImageView(image));
        back.setPadding(new Insets (10,10,10,10));
        back.setFocusTraversable(false);
        hBox.getChildren().addAll(back, h1);
        back.setOnAction(event -> {
            StoreFunctionsForm gui=new StoreFunctionsForm();
            try {
                gui.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return hBox;
    }

    public HBox buildTopHBox(Button logout, Stage primaryStage){
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        logout.setPadding(new Insets (10,10,10,10));
        logout.setFocusTraversable(false);
        hBox.getChildren().addAll(logout);
        logout.setOnAction(event -> {
            try {
                Controller.get_instance().get_userProxy().logout();
                Controller.get_instance().commit_transaction();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            LoginForm gui=new LoginForm();
            try {
                gui.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return hBox;
    }

    public Label addLabel(GridPane gridPane, String s, int row, int col){
        Label label = new Label(s);
        gridPane.add(label, col, row);
        return label;
    }

    public TextField addTextField(GridPane gridPane, int height, int row, int col){
        TextField txtfld = new TextField();
        txtfld.setPrefHeight(height);
        gridPane.add(txtfld, col, row);
        return txtfld;
    }
    public PasswordField addPasswordField(GridPane gridPane, int height, int row, int col){
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(height);
        gridPane.add(passwordField, col, row);
        return passwordField;
    }

    public Button build_center_button(GridPane gridPane, String s, int height, int width, int col, int row, int colspan, int rowspan){
        Button btn = new Button(s);
        btn .setPrefHeight(height);
        btn .setDefaultButton(true);
        btn .setPrefWidth(width);
        gridPane.add(btn , col, row, colspan, rowspan);
        GridPane.setHalignment(btn , HPos.CENTER);
        GridPane.setMargin(btn, new Insets(20, 0,20,0));
        return btn;
    }

    public GridPane createFormPane(boolean center) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40, 40, 40, 40));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        if(!center) {
            ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
            columnOneConstraints.setHalignment(HPos.RIGHT);
            ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
            columnTwoConstrains.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
        }
        return gridPane;
    }

    public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
