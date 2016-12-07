package Controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController extends Controller implements Initializable{

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cb.setItems(mp.getProjects());
        cb.setOnAction(e -> {
            infoText.setText(mp.projectInformation(cb.getSelectionModel().getSelectedItem().toString()));
            mp.setProjectName(cb.getSelectionModel().getSelectedItem().toString());
        });
    }
}
