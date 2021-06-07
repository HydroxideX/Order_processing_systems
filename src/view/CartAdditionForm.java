package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;


public class CartAdditionForm extends Application {
    ComponentsBuilder componentsBuilder = new ComponentsBuilder();
    public TableView<Object[]> table = new TableView<>();
    public static void main (String[] args) {
        launch(args);
    }

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
        Button search =new Button("Add to Cart");
        Button add_to_cart =new Button("Remove From Cart");
        TextField title = new TextField();
        title.setMinWidth(480);
        TextField copies = new TextField();
        copies.setMinWidth(480);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        HBox hBox=new HBox();
        Button back = new Button();
        Button logout = new Button("Logout");
        HBox topBar = componentsBuilder.buildTopHBox(back, logout, stage);
        HBox searchBar = new HBox();
        hBox.setSpacing(100);
        searchBar.setSpacing(5);
        searchBar.getChildren().addAll(title, copies, search, add_to_cart);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(topBar, searchBar, table);
        ((Group)scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
    }
}