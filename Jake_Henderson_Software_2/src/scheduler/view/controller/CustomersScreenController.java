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
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if(CustomerDetail.customerDetails != null) {
            customerDetailsTable.setItems(CustomerDetail.customerDetails);
            customerNameColumn.setCellValueFactory(new PropertyValueFactory("customerName"));
            addressColumn.setCellValueFactory(new PropertyValueFactory("customerAddress"));
            customerIdColumn.setCellValueFactory(new PropertyValueFactory("CustomerId"));
            customerDetailsTable.getColumns().setAll(customerNameColumn, addressColumn, customerIdColumn);
        }

    }

    @FXML
    private void addCustomerButtonHandler(ActionEvent event) {
    }

    @FXML
    private void deleteCustomerButtonHandler(ActionEvent event) {
    }
    
}