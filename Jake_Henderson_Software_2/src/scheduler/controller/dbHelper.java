/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.controller;

import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import scheduler.model.AppointmentDetails;
import scheduler.model.CustomerDetail;
import scheduler.model.EmployeeDetails;
import scheduler.model.LogPrintWriter;
import scheduler.model.upcomingAppointments;

/**
 *
 * @author Jake
 */
public class dbHelper {     
    
        
    private static String submittedUserName;
    private static String submittedUserPassword;
    private static int databaseUserId;
    private static int[] custId;   
  
    //variables for database connection
    private static Connection conn;
    private static Statement stmt;
    private static String dbUserName;
    private static String dbPassword;
    private static boolean confirmation = false;
    private static String databaseUserName = null;
    private static String databaseUserPassword = null;
    
    //database driver and url
    private static String JDBC_DRIVER;
    private static String DB_URL;
    
    public dbHelper() {
        /*
            configure db connection details with:
            Server name: 52.206.157.109 
            Database name: U04Ftj
            Username: U04Ftj
            Password: 53688224861
        */
        JDBC_DRIVER = "com.mysql.jdbc.Driver";
        DB_URL = "jdbc:mysql://52.206.157.109/U04Ftj";
        
        //credentials for database
        dbUserName = "U04Ftj";
        dbPassword = "53688224861";
    }
    
    public static boolean connect(String userName, String userPassword) throws ClassNotFoundException, SQLException, IOException {
        
        submittedUserName = userName;
        submittedUserPassword = userPassword;
               
        //open connection to database
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbHelper.conn = (Connection) DriverManager.getConnection(DB_URL, dbUserName, dbPassword);   
        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        //use sql to retrieve username and password
        try {
            
            String sql = "SELECT userName, password, userId FROM user WHERE userName=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, submittedUserName);
            ResultSet rs = ps.executeQuery();
            
            //retrieve userName, password and ID from database
            while(rs.next()) {
                databaseUserName = rs.getString("userName");
                databaseUserPassword = rs.getString("password");
                databaseUserId = rs.getInt("userId");
                       
                //compare entered userName and password to database values
                if(submittedUserName.equals(databaseUserName) && submittedUserPassword.equals(databaseUserPassword)) {
                    confirmation = true;
                }                             
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        if(confirmation == true) {
            
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String login = "User: " + databaseUserName + " logged in on: " + timestamp;
            LogPrintWriter.writeLogin(login);

        }
        return confirmation;
        
    }
        
    public static void retrieveUpcomingAppointments() throws ClassNotFoundException, SQLException {
        
        //variables used in upcomingAppointmentsTable
        int customerId = 0;
        String customerName = null;
        String appointmentType = null;
        String appointmentDate = null;
        String appointmentTime = null;
                        
        //retrieve customerId from customer table
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT type, start, customerId FROM appointment WHERE userID=" + databaseUserId);
            rs.beforeFirst();
            while(rs.next()) {
                appointmentType = rs.getString("type");
                appointmentDate = rs.getDate("start").toString();
                appointmentTime = rs.getTime("start").toString();
                customerId = rs.getInt("customerId");
                ResultSet rs1;
                try {
                    stmt = conn.createStatement();
                    rs1 = stmt.executeQuery("SELECT customerName FROM customer WHERE customerid=" + customerId);
                    while(rs1.next()) {
                        customerName = rs1.getString("customerName");
                    }
                    upcomingAppointments.addAppointment(customerName, appointmentType, appointmentDate, appointmentTime);
                } catch(SQLException e) {
                    
                }   
            }
            
                      
        } catch(SQLException e) {
            
        } 
    }
    
    public static void retrieveCustomerDetails() throws ClassNotFoundException {
                
        String customerName;
        String address = null;
        int customerId;
        
        //retrieve customer information used in table
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM customer WHERE active=0");
            while(rs.next()) {
                customerName = rs.getString("customerName");
                customerId = rs.getInt("customerid");
                ResultSet add;
                stmt = conn.createStatement();
                add = stmt.executeQuery("SELECT address FROM address WHERE addressID=" + rs.getInt("addressId"));
                while(add.next()) {
                    address = add.getString("address");
                }
                CustomerDetail.addCustomerDetails(customerName, address, customerId);
            }
            
        } catch(SQLException e) {
            
        }
    }
    
    public static void retrieveEmployeeDetails() throws SQLException {
        
        String employeeName;
        
        String retrieveNames = "SELECT userName FROM user";
        PreparedStatement ps = conn.prepareStatement(retrieveNames);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            employeeName = rs.getString("userName");
            EmployeeDetails.addEmployeeName(employeeName);
        }
    }
    
    public static void addCustomer(int cityId, String addressOne, 
            String addressTwo,String postalCode, String phone, String customerName) throws IOException {

        try {

            //insert customer data into address table
            String address = "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, ?, ?, ?, now(), ?, now(), ?)";
            PreparedStatement ps = conn.prepareStatement(address);
            ps.setString(1, addressOne);
            ps.setString(2, addressTwo);
            ps.setString(3, Integer.toString(cityId));
            ps.setString(4, postalCode);
            ps.setString(5, phone);
            ps.setString(6, "test");
            ps.setString(7, "test");
            ps.execute();
                        
            //retrieve addressId from inserted row for use in customer table
            ps = conn.prepareStatement("SELECT LAST_INSERT_ID() FROM address");
            ResultSet rs = ps.executeQuery();
            rs.next();
            String addressId = rs.getString(1);
                        
            //insert customer data into customer table
            String customer = "INSERT INTO customer(customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateby) VALUES (?, ?, ?, now(), ?, now(), ?)";
            PreparedStatement ps1 = conn.prepareStatement(customer);
            ps1.setString(1, customerName);
            ps1.setString(2, addressId);
            ps1.setString(3, Integer.toString(0));
            ps1.setString(4, databaseUserName);
            ps1.setString(5, databaseUserName);
            ps1.execute();
            
        } catch(SQLException e) {
            
        }
        String change = databaseUserName + " added new customer: " + customerName;
        LogPrintWriter.writeChangeLog(change);
    }
    
    public static void retrieveAppointmentDetails() {
        //variables used in appointment details table
        String customerName;
        String employeeName;
        String location;
        String date;
        String time;
        int appointmentId;
        
        try {
            String sql = "SELECT appointmentid, customerId, userId, location, start FROM appointment";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                appointmentId = rs.getInt("appointmentid");
                int customerId = rs.getInt("customerid");
                int userId = rs.getInt("userId");
                location = rs.getString("location");
                date = rs.getDate("start").toString();
                time = rs.getTime("start").toString();

                
                stmt = conn.createStatement();
                ResultSet cust = stmt.executeQuery("SELECT customerName FROM customer WHERE customerid=" + customerId);
                while(cust.next()) {
                    customerName = cust.getString("customerName");
                    
                    stmt = conn.createStatement();
                    ResultSet emp = stmt.executeQuery("SELECT userName FROM user WHERE userId=" + userId);
                    while(emp.next()) {
                        employeeName = emp.getString("userName");
                        AppointmentDetails.addAppointmentDetails(customerName, employeeName, location, date, time, appointmentId);
                    }
                }
              
            }
            
            
        } catch(SQLException e) {
            
        }
        
    }
    
    public static void deleteCustomer(String customerName) throws SQLException, IOException {
        String sql = "UPDATE customer SET active = 1 WHERE customerName = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.executeUpdate();
        
        String change = databaseUserName + " deleted customer: " + customerName;
        LogPrintWriter.writeChangeLog(change);
    }
    
    public static void addAppointment(String customerName, String employeeName, String title, String description, String location, String contact, String type, String date, String startTime, String endTime) throws SQLException {
        
        int customerId;
        int userId;
        String start = date + " " + startTime;
        String end = date + " " + endTime;
        
        String selectCustomerId = "SELECT customerid FROM customer WHERE customerName=?";
        PreparedStatement ps = conn.prepareStatement(selectCustomerId);
        ps.setString(1, customerName);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            customerId = rs.getInt("customerid");
            
            String selectUserId ="SELECT userId FROM user WHERE userName=?";
            PreparedStatement ps1 = conn.prepareStatement(selectUserId);
            ps1.setString(1, employeeName);
            ResultSet rs1 = ps1.executeQuery();
            while(rs1.next()) {
                userId = rs1.getInt("userId");
                String addAppointment = "INSERT INTO appointment (customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, now(), ?)";
                PreparedStatement ps2 = conn.prepareStatement(addAppointment);
                ps2.setString(1, Integer.toString(customerId));
                ps2.setString(2, Integer.toString(userId));
                ps2.setString(3, title);
                ps2.setString(4, description);
                ps2.setString(5, location);
                ps2.setString(6, contact);
                ps2.setString(7, type);
                ps2.setString(8, "");
                ps2.setString(9, start);
                ps2.setString(10, end);
                ps2.setString(11, databaseUserName);
                ps2.setString(12, databaseUserName);
                ps2.executeUpdate();
            }
        }
        
        
        
        
    }
    
    public static void deleteAppointment(int appointmentId) throws SQLException, IOException {
        String sql = "DELETE FROM appointment WHERE appointmentid=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, Integer.toString(appointmentId));
        ps.execute();
        
        String change = databaseUserName + " deleted appointment: " + appointmentId;
        LogPrintWriter.writeChangeLog(change);
    }   
    
    public static int getUserId() {
        return databaseUserId;
    }
        
    public static void setUserId(int aUserId) {
        databaseUserId = aUserId;
    }
}