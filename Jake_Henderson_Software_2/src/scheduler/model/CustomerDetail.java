package scheduler.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

    public class CustomerDetail {
        
        public static ObservableList<Details> customerDetails = FXCollections.observableArrayList();
        
        public static class Details {
        
            private StringProperty customerName = new SimpleStringProperty();
            private StringProperty customerAddress = new SimpleStringProperty();
            private IntegerProperty customerId = new SimpleIntegerProperty();
            
            private Details(String customerName, String customerAddress, int customerId) {
                setCustomerName(customerName);
                setCustomerAddress(customerAddress);
                setCustomerId(customerId);
            }
                    
            public String getCustomerName() {
                return customerName.get();
            }
            public void setCustomerName(String customerName) {
                this.customerName.set(customerName);
            }
            public String getCustomerAddress() {
                return customerAddress.get();
            }
            public void setCustomerAddress(String customerAddress) {
                this.customerAddress.set(customerAddress);
            }
            public int getCustomerId() {
                return customerId.get();
            }
            public void setCustomerId(int customerId) {
                this.customerId.set(customerId);
            }
        }
        
        public static void addCustomerDetails(String customerName, String customerAddress, int customerId) {
            Details detail = new Details(customerName, customerAddress, customerId);
            customerDetails.add(detail);
        }
        
    }