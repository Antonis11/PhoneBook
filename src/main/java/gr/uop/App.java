package gr.uop;

import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

    private PhoneBook pb = new PhoneBook();

    private MenuItem openMenuItem, closeMenuItem;

    private int clickForSurname = 0, clickName = 0;

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
        for (int i = 0; i < pb.getPersonCount(); i++) {
            Person person = pb.getPerson(i);
            String personData = person.getSurname() + " " + person.getName() + " " +
                    person.getAddress();
            items.add(personData);
        }

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

        forSurname.setDisable(true);
        forName.setDisable(true);
        forHome.setDisable(true);
        edit.setDisable(true);

        listOfPerson.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() != -1) {
                forSurname.setDisable(false);
                forName.setDisable(false);
                forHome.setDisable(false);
                edit.setDisable(false);
                if (clickForSurname > 0 || clickName > 0) {
                    Alert alert = new Alert(AlertType.WARNING);

                    alert.setTitle("Warning");
                    alert.setContentText("Do you want to save the changes?");
                    alert.setHeaderText("The current record has changed");

                    alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.YES) {
                        if (clickForSurname > 0) {
                            Person person = pb.getPerson(oldValue.intValue());
                            pb.setPersonData(oldValue.intValue(), forSurname.getText(), person.getName(),
                                    person.getAddress());
                            String setSurname = forSurname.getText() + " " + person.getName() + " "
                                    + person.getAddress();
                            items.set(oldValue.intValue(), setSurname);
                        } else if (clickName > 0) {
                            Person person = pb.getPerson(oldValue.intValue());
                            pb.setPersonData(oldValue.intValue(), person.getSurname(), forName.getText(),
                                    person.getAddress());
                            String setName = person.getSurname() + " " + forName.getText() + " " +
                                    person.getAddress();
                            items.set(oldValue.intValue(), setName);
                        }
                    } else if (result.get() == ButtonType.NO) {

                    }
                    clickForSurname = 0;
                    clickName = 0;
                }
                if (clickForSurname == 0 && clickName == 0) {
                    if (newValue.intValue() >= 0) {
                        Person person = pb.getPerson(newValue.intValue());
                        forSurname.setText(person.getSurname());
                        forName.setText(person.getName());
                        forHome.setText(person.getAddress());
                        clickForSurname = 0;
                        clickName = 0;
                    }
                }
            }

            closeMenuItem.setOnAction(event -> {
                if (clickForSurname > 0 || clickName > 0) {

                    Alert alert = new Alert(AlertType.CONFIRMATION);

                    alert.setTitle("Close Confirmation");
                    alert.setContentText("Do you want to save the changes?");
                    alert.setHeaderText("The current record has changed");

                    alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.YES) {
                        if (clickForSurname > 0) {
                            Person person = pb.getPerson(newValue.intValue());
                            pb.setPersonData(newValue.intValue(), forSurname.getText(), person.getName(),
                                    person.getAddress());
                            String setSurname = forSurname.getText() + " " + person.getName() + " "
                                    + person.getAddress();
                            items.set(newValue.intValue(), setSurname);
                        } else if (clickName > 0) {
                            Person person = pb.getPerson(newValue.intValue());
                            pb.setPersonData(newValue.intValue(), person.getSurname(), forName.getText(),
                                    person.getAddress());
                            String setName = person.getSurname() + " " + forName.getText() + " " +
                                    person.getAddress();
                            items.set(newValue.intValue(), setName);
                        }
                        pb.close();
                        listOfPerson.getItems().clear();
                        forSurname.setText("");
                        forName.setText("");
                        forHome.setText("");
                        forSurname.setDisable(true);
                        forName.setDisable(true);
                        forHome.setDisable(true);
                        edit.setDisable(true);
                    } else if (result.get() == ButtonType.NO) {
                        forSurname.setText("");
                        forName.setText("");
                        forHome.setText("");
                        forSurname.setDisable(true);
                        forName.setDisable(true);
                        forHome.setDisable(true);
                        edit.setDisable(true);
                        pb.close();
                        listOfPerson.getItems().clear();
                        if (clickForSurname > 0) {
                            Person person = pb.getPerson(oldValue.intValue());
                            pb.setPersonData(oldValue.intValue(), forSurname.getText(), person.getName(),
                                    person.getAddress());
                            String setSurname = forSurname.getText() + " " + person.getName() + " "
                                    + person.getAddress();
                            items.set(oldValue.intValue(), setSurname);
                        } else if (clickName > 0) {
                            Person person = pb.getPerson(oldValue.intValue());
                            pb.setPersonData(oldValue.intValue(), person.getSurname(), forName.getText(),
                                    person.getAddress());
                            String setName = person.getSurname() + " " + forName.getText() + " " +
                                    person.getAddress();
                            items.set(oldValue.intValue(), setName);
                        }
                    } else if (result.get() == ButtonType.CANCEL) {

                    }
                    clickForSurname = 0;
                    clickName = 0;
                }
            });

            exitMenuItem.setOnAction(event -> {
                if (clickForSurname > 0 || clickName > 0) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);

                    alert.setTitle("Exit Confirmation");
                    alert.setContentText("Do you want to save the changes?");
                    alert.setHeaderText("The current record has changed");

                    alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.YES) {
                        if (clickForSurname > 0) {
                            Person person = pb.getPerson(newValue.intValue());
                            pb.setPersonData(newValue.intValue(), forSurname.getText(), person.getName(),
                                    person.getAddress());
                            String setSurname = forSurname.getText() + " " + person.getName() + " "
                                    + person.getAddress();
                            items.set(newValue.intValue(), setSurname);
                        } else if (clickName > 0) {
                            Person person = pb.getPerson(newValue.intValue());
                            pb.setPersonData(newValue.intValue(), person.getSurname(), forName.getText(),
                                    person.getAddress());
                            String setName = person.getSurname() + " " + forName.getText() + " " +
                                    person.getAddress();
                            items.set(newValue.intValue(), setName);
                        }
                        pb.close();
                        listOfPerson.getItems().clear();
                        forSurname.setText("");
                        forName.setText("");
                        forHome.setText("");
                        forSurname.setDisable(true);
                        forName.setDisable(true);
                        forHome.setDisable(true);
                        edit.setDisable(true);
                    } else if (result.get() == ButtonType.NO) {
                        forSurname.setText("");
                        forName.setText("");
                        forHome.setText("");
                        forSurname.setDisable(true);
                        forName.setDisable(true);
                        forHome.setDisable(true);
                        edit.setDisable(true);
                        pb.close();
                        listOfPerson.getItems().clear();
                        if (clickForSurname > 0) {
                            Person person = pb.getPerson(oldValue.intValue());
                            pb.setPersonData(oldValue.intValue(), forSurname.getText(), person.getName(),
                                    person.getAddress());
                            String setSurname = forSurname.getText() + " " + person.getName() + " "
                                    + person.getAddress();
                            items.set(oldValue.intValue(), setSurname);
                        } else if (clickName > 0) {
                            Person person = pb.getPerson(oldValue.intValue());
                            pb.setPersonData(oldValue.intValue(), person.getSurname(), forName.getText(),
                                    person.getAddress());
                            String setName = person.getSurname() + " " + forName.getText() + " " +
                                    person.getAddress();
                            items.set(oldValue.intValue(), setName);
                        }
                        forSurname.setText("");
                        forName.setText("");
                        forHome.setText("");
                        forSurname.setDisable(true);
                        forName.setDisable(true);
                        forHome.setDisable(true);
                        edit.setDisable(true);
                    } else if (result.get() == ButtonType.CANCEL) {

                    }
                    clickForSurname = 0;
                    clickName = 0;
                }
            });

            stage.setOnCloseRequest(event -> {
                if (clickForSurname > 0 || clickName > 0) {
                    Alert closingAlert = new Alert(AlertType.CONFIRMATION);

                    closingAlert.setTitle("Closing Confirmation");
                    closingAlert.setHeaderText("Do you want to save the changes?");
                    closingAlert.setContentText("The current record has changed");

                    closingAlert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                    Optional<ButtonType> result = closingAlert.showAndWait();

                    if (result.get() == ButtonType.YES) {
                        if (clickForSurname > 0) {
                            Person person = pb.getPerson(newValue.intValue());
                            pb.setPersonData(newValue.intValue(), forSurname.getText(), person.getName(),
                                    person.getAddress());
                            String setSurname = forSurname.getText() + " " + person.getName() + " "
                                    + person.getAddress();
                            items.set(newValue.intValue(), setSurname);
                        } else if (clickName > 0) {
                            Person person = pb.getPerson(newValue.intValue());
                            pb.setPersonData(newValue.intValue(), person.getSurname(), forName.getText(),
                                    person.getAddress());
                            String setName = person.getSurname() + " " + forName.getText() + " " +
                                    person.getAddress();
                            items.set(newValue.intValue(), setName);
                        }
                        pb.close();
                        listOfPerson.getItems().clear();
                        forSurname.setText("");
                        forName.setText("");
                        forHome.setText("");
                        forSurname.setDisable(true);
                        forName.setDisable(true);
                        forHome.setDisable(true);
                        edit.setDisable(true);
                    } else if (result.get() == ButtonType.NO) {
                        forSurname.setText("");
                        forName.setText("");
                        forHome.setText("");
                        forSurname.setDisable(true);
                        forName.setDisable(true);
                        forHome.setDisable(true);
                        edit.setDisable(true);
                        pb.close();
                        listOfPerson.getItems().clear();
                        if (clickForSurname > 0) {
                            Person person = pb.getPerson(oldValue.intValue());
                            pb.setPersonData(oldValue.intValue(), forSurname.getText(), person.getName(),
                                    person.getAddress());
                            String setSurname = forSurname.getText() + " " + person.getName() + " "
                                    + person.getAddress();
                            items.set(oldValue.intValue(), setSurname);
                        } else if (clickName > 0) {
                            Person person = pb.getPerson(oldValue.intValue());
                            pb.setPersonData(oldValue.intValue(), person.getSurname(), forName.getText(),
                                    person.getAddress());
                            String setName = person.getSurname() + " " + forName.getText() + " " +
                                    person.getAddress();
                            items.set(oldValue.intValue(), setName);
                        }
                    } else if (result.get() == ButtonType.CANCEL) {
                        event.consume();
                    }
                    clickForSurname = 0;
                    clickName = 0;
                }
            });
        });

        forSurname.textProperty().addListener((observable, oldValue, newValue) -> {
            clickForSurname++;
        });

        forName.textProperty().addListener((observable, oldValue, newValue) -> {
            clickName++;
        });

        openMenuItem.setDisable(true);

        edit.setOnAction(event -> {
            AddressDialog dialog = new AddressDialog(stage, forHome.getText());
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.get() == ButtonType.OK) {
                forHome.setText(dialog.formatAddress());
            } else if (result.get() == ButtonType.CANCEL) {

            }
        });

        forHome.setOnMouseClicked(event -> {
            AddressDialog dialog = new AddressDialog(stage, forHome.getText());
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.get() == ButtonType.OK) {
                forHome.setText(dialog.formatAddress());
            } else if (result.get() == ButtonType.CANCEL) {

            }
        });

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