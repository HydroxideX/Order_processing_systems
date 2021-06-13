package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.SearchBookQuery;
import utils.TableTransferUtil;


public class SearchBookTableView extends Application {
    TableTransferUtil tableTransferUtil = new TableTransferUtil();
    ComponentsBuilder componentsBuilder = new ComponentsBuilder();
    public TableView<Object[]> table = new TableView<>();
    public static void main (String[] args) {
        launch(args);
    }
    private SearchBookQuery sq;
    SearchBookTableView(SearchBookQuery sq) {
        this.sq = sq;
    }

    @Override
    public void start(Stage stage) {
        table.setEditable(true);
        Scene scene = new Scene(new Group());
        stage.setTitle("Search Result");
        table.setPrefWidth(1150);
        table.setPrefHeight(550);
        componentsBuilder.init_stage(stage);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        Button back = new Button();
        Button logout = new Button("Logout");
        HBox topBar = componentsBuilder.buildTopHBox(back, logout, stage);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(topBar, table);
        ((Group)scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
        try {
            table.getColumns().clear();
            Object[][] x= tableTransferUtil.convertBookArrayListTo2DArray(sq.get_result_rows());
            tableTransferUtil.updateTable(x, table);
        } catch (Exception ex) {
            System.out.println("Error displaying Table");
        }

    }


}