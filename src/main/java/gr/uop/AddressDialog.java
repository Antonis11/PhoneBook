package gr.uop;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Window;

public class AddressDialog extends Dialog<ButtonType> {

    private Label streetLabel = new Label("Street/Number:");
    private Label postalCodeLabel = new Label("Postal Code:");
    private Label cityLabel = new Label("City:");

    private TextField streetTextField = new TextField();
    private TextField postalCodeTextField = new TextField();
    private TextField cityTextField = new TextField();

    public AddressDialog(@SuppressWarnings("exports") Window owner, String forHome) {
        String[] lines = forHome.split("\n");
        String[] cityAndZip = lines[1].split(" ", 2);

        streetTextField.setText("" + lines[0]);
        postalCodeTextField.setText("" + cityAndZip[0]);
        cityTextField.setText("" + cityAndZip[1]);

        GridPane address = new GridPane();

        address.setHgap(5);
        address.setVgap(5);

        address.add(streetLabel, 0, 0);
        address.add(streetTextField, 1, 0);
        address.add(postalCodeLabel, 0, 1);
        address.add(postalCodeTextField, 1, 1);
        address.add(cityLabel, 0, 2);
        address.add(cityTextField, 1, 2);

        getDialogPane().setContent(address);
        getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        setTitle("Address");

    }

    public void setAddress(String address) {
        streetTextField.setText("");
        postalCodeTextField.setText("");
        cityTextField.setText("");
    }

    public String formatAddress() {
        String street = streetTextField.getText();
        String postalCode = postalCodeTextField.getText();
        String city = cityTextField.getText();

        return street + "\n" + postalCode + " " + city;
    }
}
