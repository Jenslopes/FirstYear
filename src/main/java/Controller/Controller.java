package Controller;

import BusinessLogic.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class Controller {
    ////////////////////////////////////// Overordnet
    MyBLMain BL = new MyBLMain();
    User u = new User();
    Timeline TL = new Timeline();
    MyProject op = new MyProject();
    MyCalender mc = new MyCalender();
    private String projectName = MyProject.getMyProjectName();
    private String calendarDate = MyCalender.currentDate();
    //////////////////////////////////////LOGIN
    @FXML
    private TextField user;
    @FXML
    private PasswordField pass;
    //////////////////////////////////////// Admin, Client, Contractor filerne.
    //CHANGELOGIN FIELDS//
    @FXML
    private TextField pass1;
    @FXML
    private TextField pass2;
    //PROJEKT SEND MESSAGE FIELD
    @FXML
    private TextField textfield;
    public ScrollPane timeline;
    //PROJEKT TAB
    public TextArea infoText;
    public ComboBox cb;

    public Button refreshConv;
    public Button addnote;
    public DatePicker calender;
    public TextField notetodate;

    //BLMAIN
    public void userLogin() {
        u.setOwnUsername(user.getText());
        switch (BL.validate(new User(user.getText(), pass.getText()))) {
            case -1:
                InfoBox.info("Wrong Password or Username!");
                break;
            case 0:
                u = new Admin();
                u.setLevel("Admin");
                BL.setScene((Stage) user.getScene().getWindow(), "/Admin.fxml");
                break;
            case 1:
                u = new Contractor();
                u.setLevel("Contractor");
                BL.setScene((Stage) user.getScene().getWindow(), "/Contractor.fxml");
                break;
            case 2:
                u = new Client();
                u.setLevel("Client");
                BL.setScene((Stage) user.getScene().getWindow(), "/Client.fxml");
                break;
        }
    }
    //ADMINCONTROLLER
    public void userChangeLogin() {
        u.changeLogin(u.getOwnUsername(), pass1.getText(), pass2.getText());
    }
    //USERCLASS
    public void sendMessage() {
        String message = textfield.getText();
        u.addToTimeline(new Message(projectName, calendarDate, message, User.getOwnUsername()));
        timeline.setContent(TL.getTimeline(projectName));
        textfield.setText("");
    }

    public void addToDate() {
        String date = calender.getEditor().getText();
        String message = notetodate.getText();
        System.out.println(date + message + projectName);
        u.addToTimeline(new Message(projectName, date, message, User.getOwnUsername()));
        notetodate.setText("");
    }
    //BLMAIN OG HER
    public void openProject() {
        if (cb.getValue() == null) {
            InfoBox.info("Vælg et projekt!");
        } else {
            BL.setScene((Stage) pass1.getScene().getWindow(), "/Project.fxml");
        }
    }
    //HER
    public void pjBack(){
        BL.setScene((Stage)timeline.getScene().getWindow(), "/"+ User.getLevel() + ".fxml");
    }
    //HER
    public void onEnterLogin(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            userLogin();
        }
    }

    public void logOut() throws IOException {
        Stage st = (Stage)pass1.getScene().getWindow();
        st.close();
        Parent root = FXMLLoader.load(getClass().getResource("/Controller.fxml"));
        st.setTitle("Byggegruppen KBH");
        st.setOnCloseRequest(e -> st.close());
        st.setScene(new Scene(root));
        st.show();
    }

    public void cancelLogin(){
        Stage mainStage = (Stage) user.getScene().getWindow();
        mainStage.close();
    }
}
