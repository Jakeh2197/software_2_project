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
import scheduler.controller.dbHelper;
import scheduler.model.CustomerDetail;
import scheduler.model.CustomerDetail.Details;

/**
 * FXML Controller class
 *
 * @author Jake
 */
public class CustomersScreenController implements Initializable {

    @FXML
    private TableView<Details> customerDetailsTable;
    @FXML
    private TableColumn<Details, String> customerNameColumn;
    @FXML
    private TableColumn<Details, String> addressColumn;
    @FXML
    private TableColumn<Details, Integer> customerIdColumn;
    @FXML
    private Button addCustomerButton;
    @FXML
    private Button deleteCustomerButton;
    
    //this will be used to confirm customer deletion
    private static boolean confirmation; 
        
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

            customerDetailsTable.setItems(CustomerDetail.customerDetails);
            customerNameColumn.setCellValueFactory(new PropertyValueFactory("customerName"));
            addressColumn.setCellValueFactory(new PropertyValueFactory("customerAddress"));
            customerIdColumn.setCellValueFactory(new PropertyValueFactory("CustomerId"));
            customerDetailsTable.getColumns().setAll(customerNameColumn, addressColumn, customerIdColumn);
        
    }

    @FXML
    private void addCustomerButtonHandler(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().
                getResource("../AddCustomerScreen.fxml")); 
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
        
    }

    @FXML
    private void deleteCustomerButtonHandler(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        
        Parent root = FXMLLoader.load(getClass().
                getResource("../Delete.fxml")); 
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.showAndWait();
        
        if(this.isConfirmation()) {
            scheduler.model.CustomerDetail.Details details;
            details = customerDetailsTable.getSelectionModel().getSelectedItem();
            dbHelper.deleteCustomer(details.getCustomerName());
        }
        CustomerDetail.customerDetails.clear();
        dbHelper.retrieveCustomerDetails();
        customerDetailsTable.setItems(CustomerDetail.customerDetails);
    }

    public boolean isConfirmation() {
        return confirmation;
    }

    public static void setConfirmation(boolean confimation) {
        CustomersScreenController.confirmation = confimation;
    }
    


}
