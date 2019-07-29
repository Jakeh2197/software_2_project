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
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.WindowEvent;
import scheduler.controller.dbHelper;
import scheduler.model.AppointmentDetails;
import scheduler.model.CustomerDetail;
import scheduler.model.upcomingAppointments;
import scheduler.model.upcomingAppointments.App;
import static scheduler.view.controller.LoginScreenController.helper;

/**
 * FXML Controller class
 *
 * @author Jake
 */
public class MainScreenController implements Initializable {
    
    @FXML
    private Button logoutButton;
    @FXML
    private TableView upcomingAppointmentsTable;
    @FXML
    private TableColumn<App, String> customerColumn;
    @FXML
    private TableColumn<App, String> appointmentTypeColumn;
    @FXML
    private TableColumn<App, String> dateColumn;
    @FXML
    private TableColumn<App, String> timeColumn;   
    @FXML
    private Button customersButton;
    @FXML
    private Button appointmentsButton;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                
        //populate upcoming appointments table with applicable data
        upcomingAppointmentsTable.setItems(upcomingAppointments.appointments);
        customerColumn.setCellValueFactory(new PropertyValueFactory("customerName"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory("appType"));
        dateColumn.setCellValueFactory(new PropertyValueFactory("appDate"));
        timeColumn.setCellValueFactory(new PropertyValueFactory("appTime"));
        upcomingAppointmentsTable.getColumns().setAll(customerColumn, appointmentTypeColumn, dateColumn, timeColumn);
        
    }    
    
    @FXML
    private void logoutButtonHandler(ActionEvent event) throws IOException, SQLException {
        
        upcomingAppointmentsTable.getItems().clear();
        helper.closeConnection();
        
        Parent root = FXMLLoader.load(getClass().
                getResource("../LoginScreen.fxml")); 
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
        
        Stage closeStage = (Stage) logoutButton.getScene().getWindow();
        closeStage.close();
        
    }
    
    @FXML
    private void customersButtonHandler(ActionEvent event) throws IOException, ClassNotFoundException {
        
        helper.retrieveCustomerDetails();
        
        Parent root = FXMLLoader.load(getClass().
                getResource("../CustomersScreen.fxml")); 
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
        
        /*
            This lambda expression allows me to easily clear the customer table
            on the next screen whenever the window is closed. This blocks the
            program from populating the table with duplicate entries. 
        */
        stage.setOnCloseRequest((WindowEvent we) -> {
            CustomerDetail.customerDetails.clear();
        });
                
    }
    
    @FXML
    private void appointmentsButtonHandler(ActionEvent event) throws IOException {
        
        helper.retrieveAppointmentDetails();
        
        Parent root = FXMLLoader.load(getClass().
                getResource("../AppointmentsScreen.fxml")); 
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
        
        /*
            This lambda expression allows me to easily clear the appointment table
            on the next screen whenever the window is closed. This blocks the
            program from populating the table with duplicate entries. 
        */
        stage.setOnCloseRequest((WindowEvent we) -> {
            AppointmentDetails.appDetails.clear();
        });
        
    }

}
