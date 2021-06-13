package view;

import controller.Controller;
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
import javafx.util.StringConverter;
import model.OrderBuilder;
import utils.String_utils;
import utils.regex_matcher;

import java.sql.SQLException;

public class OrderPlaceConfirmForm extends Application {
    ComponentsBuilder componentsBuilder = new ComponentsBuilder();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Order Place/Confirm Form");
        GridPane gridPane = componentsBuilder.createFormPane(false);
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10,10,10,10));
        Button back = new Button();
        Button logout = new Button("Logout");
        HBox hBox = componentsBuilder.buildTopHBox(back, logout, primaryStage);
        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setSpacing(20);
        vBox.getChildren().addAll(hBox,gridPane,hBox1);
        Button place_order = componentsBuilder.buildButton(hBox1, "Place Order",40, 200, true);
        addUIControls(gridPane, place_order);
        Scene scene = new Scene(vBox, 800, 640);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addUIControls(GridPane gridPane, Button place_order) {
        Label headerLabel = new Label("Order Place/Confirm");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
        Label ISBN_label = componentsBuilder.addLabel(gridPane,"ISBN", 2, 0);
        TextField ISBN = componentsBuilder.addTextField(gridPane, 40, 2, 1);
        Label title_label = componentsBuilder.addLabel(gridPane,"Title", 3, 0);
        TextField title = componentsBuilder.addTextField(gridPane, 40, 3, 1);
        Label copies_label = componentsBuilder.addLabel(gridPane,"Copies", 4, 0);
        TextField copies = componentsBuilder.addTextField(gridPane, 40, 4, 1);
        place_order.setOnAction(event -> {
            Controller controller = null;
            try {
                controller = Controller.get_instance();
                OrderBuilder orderBuilder = new OrderBuilder();
                regex_matcher rm = new regex_matcher();
                String_utils su = new String_utils();
                if(rm.check_varchar(ISBN.getText())) {
                    orderBuilder.setISBN(ISBN.getText());
                }
                if(rm.check_varchar(title.getText())) {
                    orderBuilder.setTitle(title.getText());
                }
                if (rm.check_int(copies.getText())) {
                    orderBuilder.setCopies(su.String_to_int(copies.getText()));
                }
                orderBuilder.setUser_name(controller.get_username());
                orderBuilder.build_date();
                controller.place_order(orderBuilder.build());
                controller.commit_transaction();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        });
    }



    public static void main(String[] args) {
        launch(args);
    }
}

