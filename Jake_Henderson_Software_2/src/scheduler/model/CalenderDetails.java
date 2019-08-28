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
public class CalenderDetails {
    
    public static ObservableList<Details> calenderDetails = FXCollections.observableArrayList();

    public static class Details {
        
        private StringProperty appointmentDate = new SimpleStringProperty();
        private StringProperty appointmentTime = new SimpleStringProperty();
        private StringProperty appointmentType = new SimpleStringProperty();
        private StringProperty employee = new SimpleStringProperty();
        
        public Details (String appointmentDate, String appointmentTime, String appointmentType, String employee) {
            setAppointmentDate(appointmentDate);
            setAppointmentTime(appointmentTime);
            setAppointmentType(appointmentType);
            setEmployee(employee);
        }
        
        public String getAppointmentDate() {
            return appointmentDate.get();
        }
        public void setAppointmentDate(String appointmentDate) {
            this.appointmentDate.set(appointmentDate);
        }
        public String getAppointmentTime() {
            return appointmentTime.get();
        }
        public void setAppointmentTime(String appointmentTime) {
            this.appointmentTime.set(appointmentTime);
        }
        public String getAppointmentType() {
            return appointmentDate.get();
        }
        public void setAppointmentType(String appointmentType) {
            this.appointmentType.set(appointmentType);
        }
        public String getEmployee() {
            return employee.get();
        }
        public void setEmployee(String employee) {
            this.employee.set(employee);
        }
    }
    
    public static void addCalenderDetails(String appointmentDate, String appointmentTime, String appointmentType, String employee) {
        Details detail = new Details(appointmentDate, appointmentTime, appointmentType, employee);
        calenderDetails.add(detail);
    }
}
