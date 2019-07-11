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
import javafx.scene.control.cell.PropertyValueFactory;
import scheduler.model.AppointmentDetails;
import scheduler.model.AppointmentDetails.AppDetails;

/**
 * FXML Controller class
 *
 * @author Jake
 */
public class AppointmentsScreenController implements Initializable {

    @FXML
    private TableView<AppDetails> appointmentDetailsTable;
    @FXML
    private TableColumn<AppDetails, String> customerNameColumn;
    @FXML
    private TableColumn<AppDetails, String> employeeNameColumn;
    @FXML
    private TableColumn<AppDetails, String> locationColumn;
    @FXML
    private TableColumn<AppDetails, String> dateColumn;
    @FXML
    private TableColumn<AppDetails, String> timeColumn;
    @FXML
    private Button addAppointmentButton;
    @FXML
    private Button deleteAppointmentButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        appointmentDetailsTable.setItems(AppointmentDetails.appDetails);
        customerNameColumn.setCellValueFactory(new PropertyValueFactory("customerName"));
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory("employeeName"));
        locationColumn.setCellValueFactory(new PropertyValueFactory("location"));
        dateColumn.setCellValueFactory(new PropertyValueFactory("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory("time"));
        appointmentDetailsTable.getColumns().setAll(customerNameColumn, employeeNameColumn, 
                locationColumn, dateColumn, timeColumn);
        
    }    

    @FXML
    private void addAppointmentButtonHandler(ActionEvent event) {
    }

    @FXML
    private void deleteAppointmentButtonHandler(ActionEvent event) {
    }
    
}
