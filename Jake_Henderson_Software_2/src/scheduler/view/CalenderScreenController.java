/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import scheduler.model.CalenderDetails;
import scheduler.model.CalenderDetails.Details;

/**
 * FXML Controller class
 *
 * @author Jake
 */
public class CalenderScreenController implements Initializable {

    @FXML
    private TableView<Details> CalenderTable;
    @FXML
    private TableColumn<Details, String> appointmentDateColumn;
    @FXML
    private TableColumn<Details, String> appointmentTimeColumn;
    @FXML
    private TableColumn<Details, String> appointmentTypeColumn;
    @FXML
    private TableColumn<Details, String> employeeColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        CalenderTable.setItems(CalenderDetails.calenderDetails);
        appointmentDateColumn.setCellValueFactory(new PropertyValueFactory("appointmentDate"));
        appointmentTimeColumn.setCellValueFactory(new PropertyValueFactory("appointmentTime"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory("appointmentType"));
        employeeColumn.setCellValueFactory(new PropertyValueFactory("employee"));
        CalenderTable.getColumns().setAll(appointmentDateColumn, appointmentTimeColumn, appointmentTypeColumn, employeeColumn);
        
    }    
    
}
