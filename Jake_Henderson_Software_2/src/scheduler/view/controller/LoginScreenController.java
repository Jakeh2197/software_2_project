/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.view.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.TimeZone;
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
import scheduler.controller.*;
import scheduler.model.upcomingAppointments;
//import scheduler.model.Appointments;
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
    
    public static dbHelper helper;
    public static TimeZone timeZone = TimeZone.getDefault();
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    
    @FXML
    private void loginButtonHandler(ActionEvent event) throws IOException, ClassNotFoundException, SQLException, ParseException {
        
        helper = new dbHelper();
                        
//        verify username and password fields are not empty
        if (userNameTextField.getText().equals("") || passwordField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error!");
            alert.setContentText("User name and Password are required");
            alert.showAndWait();
        }
//        compare entries to database values
        else {
            
            String userName = userNameTextField.getText(); 
            String userPassword = passwordField.getText();
            userNameTextField.clear();
            passwordField.clear();
            if(helper.connect(userName, userPassword)) {
                
                //create class for, and retrieve upcoming appointments for the user
                upcomingAppointments apps = new upcomingAppointments();              
                try {
                    helper.retrieveUpcomingAppointments();
                } catch (ClassNotFoundException ex) {
                    
                }
                
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
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("User Name or Password not found");
                alert.showAndWait();
            }
        }
    }
}
