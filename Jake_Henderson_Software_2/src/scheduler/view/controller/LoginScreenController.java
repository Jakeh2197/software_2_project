/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.view.controller;

import java.io.IOException;
import java.net.URL;
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
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    @FXML
    private void loginButtonHandler(ActionEvent event) throws IOException {
        
        if (userNameTextField.getText().equals("Test") && passwordField.getText().equals("Test")) {
                Parent root = FXMLLoader.load(getClass().
                    getResource("MainScreen.fxml")); 
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
            alert.setContentText("Username or Password is incorrect");
            alert.showAndWait();
        }
        
    }
    
}
