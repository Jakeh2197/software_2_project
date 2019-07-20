/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.view.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private TextField startTimeTextField;
    @FXML
    private TextField endTimeTextField;
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
    private void saveAppointmentButtonHandler(ActionEvent event) throws SQLException {
        
        String location = locationTextField.getText();
        String title = titleTextField.getText();
        String type = typeTextField.getText();
        String contact = contactTextField.getText();
        String description = descriptionTextArea.getText();
        String startTime = startTimeTextField.getText();
        String endTime = endTimeTextField.getText();
        String date = dateTextField.getText();
        Details customer = customerTable.getSelectionModel().getSelectedItem();
        String customerName = customer.getCustomerName();
        EmployeeName employee = employeeTable.getSelectionModel().getSelectedItem();
        String employeeName = employee.getName();
        
        dbHelper.addAppointment(customerName, employeeName, title, description, location, contact, type, date, startTime, endTime);
        
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) {
    }
    
}
