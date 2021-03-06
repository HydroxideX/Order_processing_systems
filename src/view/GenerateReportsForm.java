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
import javafx.util.Pair;
import utils.ReportGenerator;

import java.sql.SQLException;
import java.util.ArrayList;

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
        HBox hBox = componentsBuilder.buildTopHBox(back, logout, primaryStage);
        vBox.getChildren().addAll(hBox, gridPane);
        addUIControls(gridPane);
        Scene scene = new Scene(vBox, 800, 500);
        componentsBuilder.init_stage(primaryStage);
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
        sales.setOnAction(event -> {
            Controller controller = null;
            try {
                controller = Controller.get_instance();
                ArrayList<Pair<String, Integer>> sales_array = controller.get_sales();
                ReportGenerator reportGenerator = new ReportGenerator();
                reportGenerator.generateReport("sales_report.html", "sales", "Sales Report", "Book title and Copies Sold", sales_array);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        });

        topCustomers.setOnAction( event -> {
            Controller controller = null;
            try {
                controller = Controller.get_instance();
                ArrayList<Pair<String, Integer>> topCustomers_array = controller.get_top_users();
                ReportGenerator reportGenerator = new ReportGenerator();
                reportGenerator.generateReport("customers_report.html", "customers", "Top Customers", "Customer Name and Number Books Bought", topCustomers_array);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        });

        topBooks.setOnAction(event -> {
            Controller controller = null;
            try {
                controller = Controller.get_instance();
                ArrayList<Pair<String, Integer>> top_books_array = controller.get_top_books();
                ReportGenerator reportGenerator = new ReportGenerator();
                reportGenerator.generateReport("books_report.html", "Books", "top 10 selling books", "Book title and Number of Copies Sold", top_books_array);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        });
    }

}
