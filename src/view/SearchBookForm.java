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
import model.SearchBookQuery;
import utils.String_utils;
import utils.regex_matcher;

import java.sql.SQLException;

public class SearchBookForm extends Application {
    ComponentsBuilder componentsBuilder = new ComponentsBuilder();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Search Book Form");
        GridPane gridPane = componentsBuilder.createFormPane(false);
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        Button back = new Button();
        Button logout = new Button("Logout");
        HBox hBox = componentsBuilder.buildTopHBox(back, logout, primaryStage);
        vBox.getChildren().addAll(hBox, gridPane);
        addUIControls(gridPane, primaryStage);
        Scene scene = new Scene(vBox, 800, 640);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addUIControls(GridPane gridPane, Stage primaryStage) {
        Label headerLabel = new Label("Search Books");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));
        Label ISBN_label = componentsBuilder.addLabel(gridPane, "ISBN", 2, 0);
        TextField ISBN = componentsBuilder.addTextField(gridPane, 40, 2, 1);
        Label title_label = componentsBuilder.addLabel(gridPane, "Title", 3, 0);
        TextField title = componentsBuilder.addTextField(gridPane, 40, 3, 1);
        Label author_label = componentsBuilder.addLabel(gridPane, "Author", 4, 0);
        TextField author = componentsBuilder.addTextField(gridPane, 40, 4, 1);
        Label publisher_label = componentsBuilder.addLabel(gridPane, "Publisher", 5, 0);
        TextField publisher = componentsBuilder.addTextField(gridPane, 40, 5, 1);
        Label category_label = componentsBuilder.addLabel(gridPane, "Category", 6, 0);
        TextField category = componentsBuilder.addTextField(gridPane, 40, 6, 1);
        Label year_lower_label = componentsBuilder.addLabel(gridPane, "Before Year", 7, 0);
        TextField year_lower = componentsBuilder.addTextField(gridPane, 40, 7, 1);
        Label year_upper_label = componentsBuilder.addLabel(gridPane, "After Year", 8, 0);
        TextField year_upper = componentsBuilder.addTextField(gridPane, 40, 8, 1);
        Label copies_label = componentsBuilder.addLabel(gridPane, "Copies", 9, 0);
        TextField copies = componentsBuilder.addTextField(gridPane, 40, 9, 1);
        Label price_lower_label = componentsBuilder.addLabel(gridPane, "Lower Price", 10, 0);
        TextField price_lower = componentsBuilder.addTextField(gridPane, 40, 10, 1);
        Label price_upper_label = componentsBuilder.addLabel(gridPane, "Upper Price", 11, 0);
        TextField price_upper = componentsBuilder.addTextField(gridPane, 40, 11, 1);
        Button search = componentsBuilder.build_center_button(gridPane, "Search", 40, 100, 0, 12, 2, 1);

        search.setOnAction(event -> {
            regex_matcher rm = new regex_matcher();
            String_utils su =new String_utils();
            SearchBookQuery sq = new SearchBookQuery();
            if(rm.check_varchar(ISBN.getText())) {
                sq.setISBN(ISBN.getText());
            }
            if(rm.check_varchar(title.getText())) {
                sq.setTitle(title.getText());
            }
            if(rm.check_varchar(author.getText())) {
                sq.setAuthor(author.getText());
            }
            if(rm.check_varchar(publisher.getText())) {
                sq.setPublisher_name(publisher.getText());
            }
            if(rm.check_category(category.getText().toLowerCase())) {
                sq.setCategory(category.getText().toLowerCase());
            }
            if(rm.check_int(year_lower.getText())) {
                sq.setYear_lower(su.String_to_int(year_lower.getText()));
            }
            if(rm.check_int(year_upper.getText())) {
                sq.setYear_upper(su.String_to_int(year_upper.getText()));
            }
            if(rm.check_int(copies.getText())) {
                sq.setCopies_needed(su.String_to_int(copies.getText()));
            }
            if(rm.check_float(price_lower.getText())) {
                sq.setSelling_price_lower(su.String_to_float(price_lower.getText()));
            }
            if(rm.check_float(price_upper.getText())) {
                sq.setSelling_price_upper(su.String_to_float(price_upper.getText()));
            }
            Controller controller = null;
            try {
                controller = Controller.get_instance();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                controller.search(sq);
                SearchBookTableView gui = new SearchBookTableView(sq);
                try {
                    gui.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                System.out.println("SQL database Error");
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

