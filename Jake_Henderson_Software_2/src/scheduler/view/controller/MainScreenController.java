/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.view.controller;

import java.io.IOException;
import java.net.URL;
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
import scheduler.model.Appointments;
import scheduler.model.Appointments.App;

/**
 * FXML Controller class
 *
 * @author Jake
 */
public class MainScreenController implements Initializable {
    
    
    @FXML
    private Button logoutButton;
    @FXML
    private Button appointmentsButton;
    @FXML
    private Button customersButton;
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
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                
        //populate upcoming appointments table with applicable data
        upcomingAppointmentsTable.setItems(Appointments.appointments);
        customerColumn.setCellValueFactory(new PropertyValueFactory("customerName"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory("appType"));
        dateColumn.setCellValueFactory(new PropertyValueFactory("appDate"));
        timeColumn.setCellValueFactory(new PropertyValueFactory("appTime"));
        upcomingAppointmentsTable.getColumns().setAll(customerColumn, appointmentTypeColumn, dateColumn, timeColumn);
        
    }    
    
    @FXML
    private void logoutButtonHandler(ActionEvent event) throws IOException {
        
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
    private void appointmentsButtonHandler(ActionEvent event) {
        
    }
    
    @FXML
    private void customersButtonHandler(ActionEvent event) {
        
    }

}
