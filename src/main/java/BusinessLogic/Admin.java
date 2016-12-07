package BusinessLogic;


import Database.MyDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin extends User {

    public void finishProject() {

    }
    public void createProject(MyProject project) {
        db.updateDB("INSERT INTO Projects values(" + project + ")");
        InfoBox.info("New project created!");
    }
    public void changeProject() {

    }
    public void deleteProject() {

    }
    public void sendToArchive() {

    }
    public void addToCalender() {

    }

    public void addProfile(String username, String password, int level, String firstName, String lastName, String address, int zip, String email, int number ) throws EmptyFieldException {

                db.updateDB("INSERT INTO Users(user_ID, userName, password, level)" + "values(user_ID,'" + username + "'," + "'" + Security.hashpw(password) + "'," + level + ");");
                db.updateDB("INSERT INTO UserInformation(UsersUserID, Name, LastName, Address, Zip, Email, MobilNr)" +
                        "values( LAST_INSERT_ID() " + ",'" + firstName + "','" + lastName + "','" + address + "'," + zip + ",'" + email + "'," + number + ");");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("System message");
                alert.setHeaderText("Succes!");
                alert.setContentText("Profile added");
                alert.showAndWait();
            }

    public ObservableList<String> findUsersForProject(String type)
    {
        String iduser = "";
        ObservableList<String> ID = FXCollections.observableArrayList();
        int level;
        if (type.equals("Contractor"))
        {
            level = 1;
        }
        else if(type.equals("Client"))
        {
            level = 2;
        }
        else{
            level = -1;
        }
        ResultSet usersID = MyDatabase.dbInstance().query("SELECT user_ID FROM Users WHERE level = " + level + ";");
        try {
            while (usersID.next()) {
                iduser =  usersID.getString("user_ID");
                ResultSet temp = MyDatabase.dbInstance().query("SELECT Name FROM UserInformation WHERE UsersUserID = " + usersID.getString("user_ID") + ";");
                while(temp.next()) {
                    iduser += " Name: " + temp.getString("Name");

                }
                ID.add(iduser);
                                //String name = MyDatabase.dbInstance().query("SELECT user_ID FROM Users WHERE level = " + level + ";");
            }
        }
        catch(SQLException e)
            {
                e.printStackTrace();
            }
            return ID;
    }

    public void addUserToProject(String user)
    {
        ResultSet userName = MyDatabase.dbInstance().query("SELECT userName FROM Users WHERE User_ID = " + user + ";");
        try {
            while (userName.next()) {
                String name = userName.getString("userName");

                MyDatabase.dbInstance().updateDB("INSERT INTO UserProjectRelation values(RelationID," + user + ",'" + MyProject.getMyProjectName() + "', '" + name +"');");
            }
        }
            catch(SQLException e)
        {
            e.printStackTrace();
        }
    }


    public void getProjectFromArchive() {

    }
}
