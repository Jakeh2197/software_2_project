/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.view.controller;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import scheduler.controller.dbHelper;
import scheduler.model.CustomerDetail;
import scheduler.model.CustomerDetail.Details;
import scheduler.model.EmployeeDetails;
import scheduler.model.EmployeeDetails.EmployeeName;

/**
 * FXML Controller class
 *
 * @author Jake
 */
public class AddAppointmentScreenController implements Initializable {

    @FXML
    private TableView<Details> customerTable;
    @FXML
    private TableColumn<Details, String> customersColumn;
    @FXML
    private TableView<EmployeeName> employeeTable;
    @FXML
    private TableColumn<EmployeeName, String> employeesColumn;
    @FXML
    private TextField locationTextField;
    @FXML
    private TextField dateTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Button saveAppointmentButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField typeTextField;
    @FXML
    private TextField contactTextField;
    @FXML
    private ChoiceBox startTimeChoiceBox;
    @FXML
    private ChoiceBox endTimeChoiceBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        startTimeChoiceBox.getItems().add("9:00 AM");
        startTimeChoiceBox.getItems().add("10:00 AM");
        startTimeChoiceBox.getItems().add("11:00 AM");
        startTimeChoiceBox.getItems().add("12:00 PM");
        startTimeChoiceBox.getItems().add("1:00 PM");
        startTimeChoiceBox.getItems().add("2:00 PM");
        startTimeChoiceBox.getItems().add("3:00 PM");
        startTimeChoiceBox.getItems().add("4:00 PM");
        
        endTimeChoiceBox.getItems().add("10:00 AM");
        endTimeChoiceBox.getItems().add("11:00 AM");
        endTimeChoiceBox.getItems().add("12:00 PM");
        endTimeChoiceBox.getItems().add("1:00 PM");
        endTimeChoiceBox.getItems().add("2:00 PM");
        endTimeChoiceBox.getItems().add("3:00 PM");
        endTimeChoiceBox.getItems().add("4:00 PM");
        endTimeChoiceBox.getItems().add("5:00 PM");
        
        customerTable.setItems(CustomerDetail.customerDetails);
        customersColumn.setCellValueFactory(new PropertyValueFactory("customerName"));
        customerTable.getColumns().setAll(customersColumn);
        
        employeeTable.setItems(EmployeeDetails.employeeNames);
        employeesColumn.setCellValueFactory(new PropertyValueFactory("name"));
        employeeTable.getColumns().setAll(employeesColumn);
        
    }    

    @FXML
    private void saveAppointmentButtonHandler(ActionEvent event) throws SQLException {
        
        if ( locationTextField.getText().trim().isEmpty() ||
               titleTextField.getText().trim().isEmpty() ||
               typeTextField.getText().trim().isEmpty() ||
               contactTextField.getText().trim().isEmpty() ||
               descriptionTextArea.getText().trim().isEmpty() ||
               startTimeChoiceBox.getValue().toString().trim().isEmpty() ||
               endTimeChoiceBox.getValue().toString().trim().isEmpty() ||
               dateTextField.getText().trim().isEmpty()) {
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty data fields present");
            alert.setHeaderText("Error!");
            alert.setContentText("ALL fields must contain necessary data");
            alert.showAndWait();
            
        } else {
            
            //collect user entered data from text fields
            String location = locationTextField.getText();
            String title = titleTextField.getText();
            String type = typeTextField.getText();
            String contact = contactTextField.getText();
            String description = descriptionTextArea.getText();
            String start = (String) startTimeChoiceBox.getValue();
            String end = (String) endTimeChoiceBox.getValue();
            String appDate = dateTextField.getText();
            Details customer = null; 
            String customerName = null;
            try {
                customerTable.getSelectionModel().getSelectedItem();
                customerName = customer.getCustomerName();
            } catch(NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No customer selected");
                alert.setHeaderText("Error!");
                alert.setContentText("Please ensure a customer is selected");
                alert.showAndWait();
            }

            EmployeeName employee = null;
            String employeeName = null;
            try {
                employeeTable.getSelectionModel().getSelectedItem();
                employeeName = employee.getName();
            } catch(NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No employee selected");
                alert.setHeaderText("Error!");
                alert.setContentText("Please ensure an employee is selected");
                alert.showAndWait();
            }

            //create LocalTime object based off user input for appointment start and end time
            LocalTime startTime = null;
            switch (start) {
                case "9:00 AM":
                    startTime = LocalTime.of(9, 0);
                    break;
                case "10:00 AM":
                    startTime = LocalTime.of(10, 0);
                    break;
                case "11:00 AM":
                    startTime = LocalTime.of(11, 0);
                    break;
                case "12:00 PM":
                    startTime = LocalTime.of(12, 0);
                    break;
                case "1:00 PM":
                    startTime = LocalTime.of(13, 0);
                    break;
                case "2:00 PM":
                    startTime = LocalTime.of(14, 0);
                    break;
                case "3:00 PM":
                    startTime = LocalTime.of(15, 0);
                    break;
                case "4:00 PM":
                    startTime = LocalTime.of(16, 0);
                    break;
            }

            LocalTime endTime = null;
            switch (end) {
                case "10:00 AM":
                    endTime = LocalTime.of(10, 0);
                    break;
                case "11:00 AM":
                    endTime = LocalTime.of(11, 0);
                    break;
                case "12:00 PM":
                    endTime = LocalTime.of(12, 0);
                    break;
                case "1:00 PM":
                    endTime = LocalTime.of(13, 0);
                    break;
                case "2:00 PM":
                    endTime = LocalTime.of(14, 0);
                    break;
                case "3:00 PM":
                    endTime = LocalTime.of(15, 0);
                    break;
                case "4:00 PM":
                    endTime = LocalTime.of(16, 0);
                    break;
                case "5:00 PM":
                    endTime = LocalTime.of(17, 0);
                    break;
            }

            //create LocalDate object
            LocalDate date;
            //attempt to assign value to date and insert values into appointment table
            try {
                date = LocalDate.parse(appDate);
                dbHelper.addAppointment(customerName, employeeName, title, description, location, contact, type, date, startTime, endTime);
            } catch(DateTimeParseException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Improper format entered for Date field");
                alert.setHeaderText("Error!");
                alert.setContentText("Please ensure the date you have entered matches the following format: YYYY-MM-DD");
                alert.showAndWait();
            }
        }
                       
        
            
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) {
    }
    
}
