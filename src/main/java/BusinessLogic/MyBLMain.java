package BusinessLogic;

import Database.MyDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MyBLMain {

    Security sc = new Security();

    public int validate(String user, String pass){
        return Login.handleLogin(user, sc.hashpw(pass));
    }

    public void setScene(Stage oStage, String path) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            oStage.setScene(new Scene(root));
            oStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public VBox getTimeline(){
        ResultSet rs = MyDatabase.dbInstance().query("SELECT * FROM Timeline;");
        VBox vb = new VBox();
        try {
            while (rs.next()) {
                vb.getChildren().add(new Label(rs.getString("DateAndTime") +"[ " + rs.getString("firstName") + ": " +  rs.getString("Description") + " ]"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return vb;
    }

    public ComboBox getProjects(){
        ResultSet rs = MyDatabase.dbInstance().query("SELECT * FROM Projects;");
        ObservableList<Object> options = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                options.add(new MyProject(rs.getInt("project_ID"), rs.getString("projectName")));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        ComboBox cb = new ComboBox(options);
        return cb;
    }
}
