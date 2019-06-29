/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.view.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static scheduler.Launcher.helper;
import scheduler.controller.*;

/**
 * FXML Controller class
 *
 * @author Jake
 */
public class LoginScreenController implements Initializable {

    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    
    private String userName;
    private String userPassword;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    @FXML
    private void loginButtonHandler(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        
        //verify username and password fields are not empty
        if (userNameTextField.getText().equals("") || passwordField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error!");
            alert.setContentText("User name and Password are required");
            alert.showAndWait();
        }
        //compare entries to database values;
        else {
            userName = userNameTextField.getText();
            userPassword = passwordField.getText();
            if(dbHelper.connect(userName, userPassword)) {
                Parent root = FXMLLoader.load(getClass().
                    getResource("../MainScreen.fxml")); 
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Scheduler");
                stage.setScene(scene);
                stage.show();

                Stage closeStage = (Stage) loginButton.getScene().getWindow();
                closeStage.close();
            }
        }
        
        //gather appointment data for this user
        try {
            dbHelper.retrieveUpcomingAppointments();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
}
