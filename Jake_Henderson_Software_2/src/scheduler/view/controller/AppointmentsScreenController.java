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
public class AppointmentsScreenController implements Initializable {

    @FXML
    private TableView<?> appointmentDetailsTable;
    @FXML
    private TableColumn<?, ?> customerNameColumn;
    @FXML
    private TableColumn<?, ?> employeeNameColumn;
    @FXML
    private TableColumn<?, ?> locationColumn;
    @FXML
    private TableColumn<?, ?> dateColumn;
    @FXML
    private TableColumn<?, ?> timeColumn;
    @FXML
    private Button addAppointmentButton;
    @FXML
    private Button deleteAppointmentButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addAppointmentButtonHandler(ActionEvent event) {
    }

    @FXML
    private void deleteAppointmentButtonHandler(ActionEvent event) {
    }
    
}
