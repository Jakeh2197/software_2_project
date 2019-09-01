/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jake
 */
public class AppointmentCount {
    
    private StringProperty appointmentType = new SimpleStringProperty();
    private IntegerProperty count = new SimpleIntegerProperty();
    
    public AppointmentCount(String appointmentType, int count) {
        setAppointmentType(appointmentType);
        setCount((Integer)count);
    }
    
    public String getAppointmentType() {
        return appointmentType.get();
    }
    public void setAppointmentType(String appointmentType) {
        this.appointmentType.set(appointmentType);
    }
    public Integer  getCount() {
        return count.get();
    }
    public void setCount(Integer count) {
        this.count.set(count);
    }
    
}
