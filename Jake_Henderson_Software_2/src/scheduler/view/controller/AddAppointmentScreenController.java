/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.view.controller;

import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scheduler.model.CustomerDetail;
import scheduler.model.CustomerDetail.Details;
import scheduler.model.EmployeeDetails;
import scheduler.model.EmployeeDetails.EmployeeName;
import static scheduler.view.controller.LoginScreenController.helper;

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
    private TextField startTimeTextField;
    @FXML
    private TextField endTimeTextField;
    @FXML
    private RadioButton startTimeAmButton;
    @FXML
    private RadioButton startTimePmButton;
    @FXML
    private RadioButton endTimeAmButton;
    @FXML
    private RadioButton endTimePmButton;
    @FXML
    private ToggleGroup startTimeToggle;
    @FXML
    private ToggleGroup endTimeToggle;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
        customerTable.setItems(CustomerDetail.customerDetails);
        customersColumn.setCellValueFactory(new PropertyValueFactory("customerName"));
        customerTable.getColumns().setAll(customersColumn);
        
        employeeTable.setItems(EmployeeDetails.employeeNames);
        employeesColumn.setCellValueFactory(new PropertyValueFactory("name"));
        employeeTable.getColumns().setAll(employeesColumn);
        
        
    }    

    @FXML
    private void saveAppointmentButtonHandler(ActionEvent event) throws SQLException, ParseException {
        
        //collect user entered data from text fields
        String location = locationTextField.getText();
        String title = titleTextField.getText();
        String type = typeTextField.getText();
        String contact = contactTextField.getText();
        String description = descriptionTextArea.getText();
        String start = startTimeTextField.getText();
        String end = endTimeTextField.getText();
        String appDate = dateTextField.getText();
        Details customer = null; 
        String customerName = null;
        
        String startTime = null;
        String adjustedStartTime = null;
        String endTime = null;
        String adjustedEndTime = null;
        DateFormat dtf = new SimpleDateFormat("yyyy-mm-dd hh:mm a");
        DateFormat output = new SimpleDateFormat("yyyy-mm-dd HH:mm");
        dtf.setTimeZone(TimeZone.getDefault());
        output.setTimeZone(TimeZone.getTimeZone("UTC"));
        String open = appDate + " 9:00 AM";
        String close = appDate + " 5:00 PM";
        Date openTime = null;
        Date closeTime = null;
        try {
            openTime = dtf.parse(open);
            closeTime = dtf.parse(close);
        } catch(ParseException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid date format");
            alert.setHeaderText("Error!");
            alert.setContentText("Please ensure data in date field uses the following format: YYYY-MM-DD");
            alert.showAndWait();
        }      
        
        if (location.trim().isEmpty() ||
               title.trim().isEmpty() ||
               type.trim().isEmpty() ||
               contact.trim().isEmpty() ||
               description.trim().isEmpty() ||
               start.trim().isEmpty() ||
               end.trim().isEmpty() ||
               appDate.trim().isEmpty()) {
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty data fields present");
            alert.setHeaderText("Error!");
            alert.setContentText("ALL fields must contain necessary data");
            alert.showAndWait();
            
        } else {
            
            Date startDate = null;
            
            if(startTimeToggle.getSelectedToggle() == startTimeAmButton) {
                startTime = appDate + " " + start + " AM";
                try {
                    startDate = dtf.parse(startTime);
                } catch(ParseException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Date or Time format error");
                    alert.setHeaderText("Error!");
                    alert.setContentText("No valid date or time entered. Please verify data being entered is correct");
                    alert.showAndWait();
                }
                if (startDate.before(openTime)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment start time error");
                    alert.setHeaderText("Error!");
                    alert.setContentText("Start time for appointments must not be before 9:00 AM");
                    alert.showAndWait();
                } else {
                    adjustedStartTime = output.format(startDate);
                }
            } else if(startTimeToggle.getSelectedToggle() == startTimePmButton) {
                startTime = appDate + " " + start + " PM";
                try {
                    startDate = dtf.parse(startTime);
                } catch(ParseException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Date or Time format error");
                    alert.setHeaderText("Error!");
                    alert.setContentText("No valid date or time entered. Please verify data being entered is correct");
                    alert.showAndWait();
                }
                if(startDate.after(closeTime)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment start time error");
                    alert.setHeaderText("Error!");
                    alert.setContentText("Start time for appointments must not be after 5:00 PM");
                    alert.showAndWait();
            alert.showAndWait();
                } else {
                    adjustedStartTime = output.format(startDate);
                }
            }
            
            if(endTimeToggle.getSelectedToggle() == endTimeAmButton) {
                Date endDate = null;
                endTime = appDate + " " + end + " AM";
                try {
                    endDate = dtf.parse(endTime);
                } catch(ParseException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Date or Time format error");
                    alert.setHeaderText("Error!");
                    alert.setContentText("No valid date or time entered. Please verify data being entered is correct");
                    alert.showAndWait();
                }
                if(endDate.before(openTime)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment end time error");
                    alert.setHeaderText("Error!");
                    alert.setContentText("End time for appointments must not be before 9:00 AM");
                    alert.showAndWait();
                } else {
                    adjustedEndTime = output.format(endDate);
                }
            } else if(endTimeToggle.getSelectedToggle() == endTimePmButton) {
                Date endDate = null;
                endTime = appDate + " " + end + " PM";
                try {
                    endDate = dtf.parse(endTime);
                } catch(ParseException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Date or Time format error");
                    alert.setHeaderText("Error!");
                    alert.setContentText("No valid date or time entered. Please verify data being entered is correct");
                    alert.showAndWait();
                }
                if(endDate.after(closeTime)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment end time error");
                    alert.setHeaderText("Error!");
                    alert.setContentText("end time for appointments must not be after 5:00 PM");
                    alert.showAndWait();
                } else {
                    adjustedEndTime = output.format(endDate);
                }

            }
                        
            try {
                customer = customerTable.getSelectionModel().getSelectedItem();
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
                employee = employeeTable.getSelectionModel().getSelectedItem();
                employeeName = employee.getName();
            } catch(NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No employee selected");
                alert.setHeaderText("Error!");
                alert.setContentText("Please ensure an employee is selected");
                alert.showAndWait();
            }




            try {
                helper.addAppointment(customerName, employeeName, title, description, location, contact, type, adjustedStartTime, adjustedEndTime);
            } catch(NullPointerException e) {
                
            }
            
            locationTextField.clear();
            titleTextField.clear();
            typeTextField.clear();
            contactTextField.clear();
            descriptionTextArea.clear();
            startTimeTextField.clear();
            endTimeTextField.clear();
            dateTextField.clear();
            
        }
      
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) {
    }
    
}
