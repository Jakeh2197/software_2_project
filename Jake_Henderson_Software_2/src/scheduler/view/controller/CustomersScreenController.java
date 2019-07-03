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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Jake
 */
public class CustomersScreenController implements Initializable {

    @FXML
    private TableView<?> upcomingAppointmentsTable;
    @FXML
    private TableColumn<?, ?> customerNameColumn;
    @FXML
    private TableColumn<?, ?> addressColumn;
    @FXML
    private TableColumn<?, ?> customerIdColumn;
    @FXML
    private Button addCustomerButton;
    @FXML
    private Button deleteCustomerButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addCustomerButtonHandlerr(ActionEvent event) {
    }

    @FXML
    private void deleteCustomerButtonHandler(ActionEvent event) {
    }
    
}
