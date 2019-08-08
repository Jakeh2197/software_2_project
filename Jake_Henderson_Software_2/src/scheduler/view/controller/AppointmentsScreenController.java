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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scheduler.controller.dbHelper;
import scheduler.model.AppointmentDetails;
import scheduler.model.AppointmentDetails.AppDetails;
import scheduler.model.CustomerDetail;
import scheduler.model.EmployeeDetails;
import static scheduler.view.controller.LoginScreenController.helper;

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
    private void addAppointmentButtonHandler(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        
        helper.retrieveCustomerDetails();
        helper.retrieveEmployeeDetails();
        
        Parent root = FXMLLoader.load(getClass().
                getResource("../AddAppointmentScreen.fxml")); 
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
        
        stage.setOnCloseRequest((WindowEvent e) -> {
            CustomerDetail.customerDetails.clear();
            EmployeeDetails.employeeNames.clear();
        });
        
        
    }

    @FXML
    private void deleteAppointmentButtonHandler(ActionEvent event) throws IOException, SQLException {
        
        Parent root = FXMLLoader.load(getClass().
                getResource("../Delete.fxml")); 
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.showAndWait();
        
        if(DeleteController.isConfirmation()) {
            scheduler.model.AppointmentDetails.AppDetails details;
            details = appointmentDetailsTable.getSelectionModel().getSelectedItem();
            helper.deleteAppointment(details.getAppointmentId());
        }
        AppointmentDetails.appDetails.clear();
        helper.retrieveAppointmentDetails();
        appointmentDetailsTable.setItems(AppointmentDetails.appDetails);
    }
        
}
