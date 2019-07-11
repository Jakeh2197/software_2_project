/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jake
 */
public class AppointmentDetails {
    
    public static ObservableList<AppDetails> appDetails = FXCollections.observableArrayList();
    
    public static class AppDetails {
        
        private StringProperty customerName = new SimpleStringProperty();
        private StringProperty employeeName = new SimpleStringProperty();
        private StringProperty location = new SimpleStringProperty();
        private StringProperty date = new SimpleStringProperty();
        private StringProperty time = new SimpleStringProperty();
        
        public AppDetails(String customerName, String employeeName, String location, String date, String time) {
            setCustomerName(customerName);
            setEmployeeName(employeeName);
            setLocation(location);
            setDate(date);
            setTime(time);
        }
        
        public String getCustomerName() {
            return customerName.get();
        }
        
        public void setCustomerName(String customerName) {
            this.customerName.set(customerName);
        }
        
        public String getEmployeeName() {
            return employeeName.get();
        }
        
        public void setEmployeeName(String employeeName) {
            this.employeeName.set(employeeName);
        }
        
        public String getLocation() {
            return location.get();
        }
        
        public void setLocation(String location) {
            this.location.set(location);
        }
        
        public String getDate() {
            return date.get();
        }
        
        public void setDate(String date) {
            this.date.set(date);
        }
        
        public String getTime() {
            return time.get();
        }
        
        public void setTime(String time) {
            this.time.set(time);
        }
    }
    
            
    public static void addAppointmentDetails(String customerName, String employeeName, String location, String date, String time) {
        AppDetails details = new AppDetails(customerName, employeeName, location, date, time);
        appDetails.add(details);
    }
}
