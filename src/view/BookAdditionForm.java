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
import model.BookBuilder;
import utils.String_utils;
import utils.regex_matcher;

import java.sql.SQLException;

public class BookAdditionForm extends Application {
    ComponentsBuilder componentsBuilder = new ComponentsBuilder();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Book Addition Form");
        GridPane gridPane = componentsBuilder.createFormPane(false);
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
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
        Label headerLabel = new Label("Add Books");
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
        Label year_label = componentsBuilder.addLabel(gridPane, "Year", 7, 0);
        TextField year = componentsBuilder.addTextField(gridPane, 40, 7, 1);
        Label threshold_label = componentsBuilder.addLabel(gridPane, "Threshold", 8, 0);
        TextField threshold = componentsBuilder.addTextField(gridPane, 40, 8, 1);
        Label copies_label = componentsBuilder.addLabel(gridPane, "Copies", 9, 0);
        TextField copies = componentsBuilder.addTextField(gridPane, 40, 9, 1);
        Label price_label = componentsBuilder.addLabel(gridPane, "Price", 10, 0);
        TextField price = componentsBuilder.addTextField(gridPane, 40, 10, 1);
        Button addBook = componentsBuilder.build_center_button(gridPane, "Add", 40, 100, 0, 11, 2, 1);
        addBook.setOnAction(event -> {
            regex_matcher matcher = new regex_matcher();
            String_utils string_utils = new String_utils();
            boolean can = (matcher.check_varchar(ISBN.getText()) && matcher.check_varchar(title.getText())
                    && matcher.check_category(category.getText().toLowerCase()) && matcher.check_float(price.getText())
                    && matcher.check_int(threshold.getText()));
            if (!can) {
                componentsBuilder.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "invalid Book data");
                return;
            }
            Controller controller = null;
            try {
                controller = Controller.get_instance();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            assert controller != null;
            if (controller.has_book_with_title(title.getText())) {
                componentsBuilder.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "this title already exist");
                return;
            }
            if (controller.has_book_with_ISBN(ISBN.getText())) {
                componentsBuilder.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "this ISBN already exist");
                return;
            }
            if (!matcher.is_empty(publisher.getText())) {
                if (!controller.has_publisher_name(publisher.getText())) {
                    componentsBuilder.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "there is no puplisher name with " + publisher.getText());
                    return;
                }
            }
            BookBuilder builder = new BookBuilder();
            builder.setISBN(ISBN.getText());
            builder.setTitle(title.getText());
            builder.setCategory(category.getText().toLowerCase());
            builder.setSelling_price(string_utils.String_to_float(price.getText()));
            builder.setThreshold(string_utils.String_to_int(threshold.getText()));

            if (matcher.check_varchar(author.getText())) {
                builder.setAuthor(author.getText());
            }
            if (matcher.check_int(copies.getText())) {
                builder.setCopies_available(string_utils.String_to_int(copies.getText()));
            }
            if (matcher.check_year(year.getText())) {
                builder.setYear(string_utils.String_to_int((year.getText())));
            }
            if (matcher.check_varchar(publisher.getText())) {
                builder.setPublisher_name(publisher.getText());
            }
            boolean add = false;
            try {
                add = controller.add_new_book(builder.build());
                controller.commit_transaction();
            } catch (SQLException e) {

            }
            if (add) {
                componentsBuilder.showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "success!", "book id added successfully");
            } else {
                componentsBuilder.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error!", "unknown error in database");
            }
        });

    }

}

