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
    
    public static void addEmployeeName(String name) {
        EmployeeName employeeName = new EmployeeName(name);
        employeeNames.add(employeeName);
    }
    
//    public static void addAppointment(String customerName, String date, String startTime, String type, String location) {
//        Appointment appointment = new Appointment(customerName, date, startTime, type, location);
//        employeeAppointments.add(appointment);
//    }
}
