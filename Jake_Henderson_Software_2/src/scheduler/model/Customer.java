/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.model;

import java.sql.Timestamp;
import java.time.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jake
 */
public class Customer {
    
    private static int customerId;
    private static String customerName;
    private static int addressId;
    private static byte active;
    private static LocalDateTime createDate;
    private static String createdBy;
    private static Timestamp lastUpdated;
    private static String lastUpdatedBy;
    


    public static int getCustomerId() {
        return customerId;
    }
    public static void setCustomerId(int aCustomerId) {
        customerId = aCustomerId;
    }
    public static String getCustomerName() {
        return customerName;
    }
    public static void setCustomerName(String aCustomerName) {
        customerName = aCustomerName;
    }
    public static int getAddressId() {
        return addressId;
    }
    public static void setAddressId(int aAddressId) {
        addressId = aAddressId;
    }
    public static LocalDateTime getCreateDate() {
        return createDate;
    }
    public static void setCreateDate(LocalDateTime aCreateDate) {
        createDate = aCreateDate;
    }
    public static String getCreatedBy() {
        return createdBy;
    }
    public static void setCreatedBy(String aCreatedBy) {
        createdBy = aCreatedBy;
    }
    public static Timestamp getLastUpdated() {
        return lastUpdated;
    }
    public static void setLastUpdated(Timestamp aLastUpdated) {
        lastUpdated = aLastUpdated;
    }
    public static String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    public static void setLastUpdatedBy(String aLastUpdatedBy) {
        lastUpdatedBy = aLastUpdatedBy;
    }

                       
}
