package Controller;

import BusinessLogic.Admin;
import BusinessLogic.EmptyFieldException;
import BusinessLogic.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class AdminController extends Controller implements Initializable {
    Admin newU = new Admin();
    public TextField usernameboks;
    public TextField passwordboks;
    public TextField firstname;
    public TextField lastname;
    public TextField address;
    public TextField zip;
    public TextField email;
    public TextField number;


    @FXML
    public ComboBox<String> usertype;
    ObservableList<String> types = FXCollections.observableArrayList("Client", "Contractor", "Admin");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usertype.setItems(types);
    }

    public void createProfile() throws EmptyFieldException {
        int level = -1;
        try {
            if (usertype.getValue() != null) {
                String type = usertype.getSelectionModel().getSelectedItem().toString();
                if (type.equals("Client")) {
                    level = 2;
                } else if (type.equals("Contractor")) {
                    level = 1;
                } else if (type.equals("Admin")) {
                    level = 0;
                }
            } else {
                throw new EmptyFieldException();
            }
            if (usernameboks.getText().equals("") || passwordboks.getText().equals("") || level == -1 || firstname.getText().equals("") || lastname.getText().equals("") || address.getText().equals("") || zip.getText().equals("") || email.getText().equals("") || number.getText().equals("")) {
                System.out.println("fejl");
                throw new EmptyFieldException();
            } else {
                newU.addProfile(usernameboks.getText(), passwordboks.getText(), level, firstname.getText(), lastname.getText(), address.getText(), Integer.parseInt(zip.getText()), email.getText(), Integer.parseInt(number.getText()));
                System.out.println(firstname.getText());
            }
        } catch (EmptyFieldException e) {
            e.printStackTrace();
        }
    }

}
