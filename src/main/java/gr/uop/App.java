package gr.uop;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private MenuItem openMenuItem, closeMenuItem;

    @Override
    public void start(Stage stage) {

        // Panes and arrangement
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        openMenuItem = new MenuItem("Open");
        closeMenuItem = new MenuItem("Close");
        SeparatorMenuItem separator = new SeparatorMenuItem();
        MenuItem exitMenuItem = new MenuItem("Exit");

        fileMenu.getItems().addAll(openMenuItem, closeMenuItem, separator, exitMenuItem);
        menuBar.getMenus().add(fileMenu);

        SplitPane splitPane = new SplitPane();

        VBox leftControl = new VBox();
        HBox rightControl = new HBox();

        ObservableList<String> items = FXCollections.observableArrayList();

        ListView<String> listOfPerson = new ListView<>(items);

        listOfPerson.setPrefSize(150, 700);
        listOfPerson.setMinWidth(150);

        TitledPane personalData = new TitledPane();
        personalData.setPadding(new Insets(5));
        personalData.setText("Personal data ");
        personalData.setCollapsible(false);

        Label surname = new Label("Surname:");
        Label name = new Label("Name:");

        TextField forSurname = new TextField();
        TextField forName = new TextField();
        forSurname.setMaxWidth(150);
        forName.setMaxWidth(120);

        GridPane personalDataInner = new GridPane();

        personalDataInner.setHgap(5);
        personalDataInner.setVgap(10);

        personalDataInner.add(surname, 0, 0);
        personalDataInner.add(forSurname, 1, 0);
        personalDataInner.add(name, 0, 1);
        personalDataInner.add(forName, 1, 1);

        GridPane.setHalignment(surname, HPos.RIGHT);
        GridPane.setHalignment(name, HPos.RIGHT);
        GridPane.setHalignment(forSurname, HPos.LEFT);
        GridPane.setHalignment(forName, HPos.LEFT);

        personalData.setContent(personalDataInner);
        personalData.setPrefWidth(300);
        personalData.setPrefHeight(350);

        TitledPane addresses = new TitledPane();
        addresses.setPadding(new Insets(5, 5, 5, 0));
        addresses.setText("Addresses ");
        addresses.setCollapsible(false);

        Label home = new Label("Home:");

        TextArea forHome = new TextArea();
        forHome.setPrefWidth(150);
        forHome.setPrefRowCount(5);

        Button edit = new Button("Edit");
        edit.setMaxWidth(Double.MAX_VALUE);

        VBox forHomeAndEdit = new VBox(5);
        forHomeAndEdit.getChildren().addAll(forHome, edit);

        HBox Home = new HBox(5);
        Home.getChildren().addAll(home, forHomeAndEdit);

        addresses.setContent(Home);
        addresses.setPrefWidth(300);
        addresses.setPrefHeight(350);

        leftControl.getChildren().addAll(listOfPerson);
        rightControl.getChildren().addAll(personalData, addresses);

        splitPane.getItems().addAll(leftControl, rightControl);
        splitPane.setDividerPositions(0.2f, 0.8f);

        BorderPane mainPane = new BorderPane();
        mainPane.setTop(menuBar);
        mainPane.setCenter(splitPane);

        var scene = new Scene(mainPane, 700, 350);
        stage.setTitle("PhoneBook");
        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(350);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}