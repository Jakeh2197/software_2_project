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
public class EmployeeDetails {
    
    public static ObservableList<EmployeeName> employeeNames = FXCollections.observableArrayList();  
    
//    public static ObservableList<Appointment> employeeAppointments = FXCollections.observableArrayList();
    
    public static class EmployeeName {
        
        private StringProperty name = new SimpleStringProperty();
        
        private EmployeeName(String name) {
            setName(name);
        }
        
        public String getName() {
            return name.get();
        }
        
        public void setName(String name) {
            this.name.set(name);
        }
    }
    
//    public static class Appointment {
//        
//        private StringProperty customerName = new SimpleStringProperty();
//        private StringProperty date = new SimpleStringProperty();
//        private StringProperty startTime = new SimpleStringProperty();
//        private StringProperty type = new SimpleStringProperty();
//        private StringProperty location = new SimpleStringProperty();
//        
//        Appointment(String customerName, String date, String startTime, String type, String location) {
//            setCustomerName(customerName);
//            setDate(date);
//            setStartTime(startTime);
//            setType(type);
//            setLocation(location);
//        }
//
//        public String getCustomerName() {
//            return customerName.get();
//        }
//
//        public void setCustomerName(String customerName) {
//            this.customerName.set(customerName);
//        }
//
//        public String getDate() {
//            return date.get();
//        }
//
//        public void setDate(String date) {
//            this.date.set(date);
//        }
//
//        public String getStartTime() {
//            return startTime.get();
//        }
//
//        public void setStartTime(String startTime) {
//            this.startTime.set(startTime);
//        }
//
//        public String getType() {
//            return type.get();
//        }
//
//        public void setType(String type) {
//            this.type.set(type);
//        }
//        
//        public String getLoction() {
//            return location.get();
//        }
//
//        public void setLocation(String location) {
//            this.location.set(location);
//        }
//        
//    }
    
    public static void addEmployeeName(String name) {
        EmployeeName employeeName = new EmployeeName(name);
        employeeNames.add(employeeName);
    }
    
//    public static void addAppointment(String customerName, String date, String startTime, String type, String location) {
//        Appointment appointment = new Appointment(customerName, date, startTime, type, location);
//        employeeAppointments.add(appointment);
//    }
}
