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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import static scheduler.view.controller.LoginScreenController.helper;

/**
 * FXML Controller class
 *
 * @author Jake
 */
public class AddCustomerScreenController implements Initializable {
    
    @FXML
    private TextField customerNameTextField;
    @FXML
    private TextField addressOneTextField;
    @FXML
    private TextField addressTwoTextField;
    @FXML
    private TextField postalCodeTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private Button addCustomerButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ToggleGroup cityGroup;
    @FXML
    private ToggleGroup countryGroup;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField countryTextField;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//         TODO
    }    
    
    @FXML
    private void addCustomerButtonHandler(ActionEvent event) throws IOException, SQLException {
               
        //varriables used to create new customer
        String customerName = customerNameTextField.getText();
        String addressOne = addressOneTextField.getText();
        String addressTwo = addressTwoTextField.getText();
        String postalCode = postalCodeTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String city = cityTextField.getText();
        String country = countryTextField.getText();

        helper.addCustomer(city, addressOne, addressTwo, postalCode, phoneNumber, customerName, country);

    }
    
    @FXML
    private void cancelButtonHandler(ActionEvent event) throws IOException {
        
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
        
}
