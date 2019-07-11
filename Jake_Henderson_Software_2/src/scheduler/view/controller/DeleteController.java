/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.view.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;



/**
 * FXML Controller class
 *
 * @author Jake
 */
public class DeleteController implements Initializable {

    @FXML
    private Button YesButton;
    @FXML
    private Button NoButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void YesButtonHandler(ActionEvent event) {
        
        CustomersScreenController.setConfirmation(true);
        
        Stage stage = (Stage) YesButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void NoButtonHandler(ActionEvent event) {
        
        CustomersScreenController.setConfirmation(false);
        
        Stage stage = (Stage) NoButton.getScene().getWindow();
        stage.close();
    }
    
}
