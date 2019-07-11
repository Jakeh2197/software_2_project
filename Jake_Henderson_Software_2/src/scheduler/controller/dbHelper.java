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
import scheduler.model.AppointmentDetails;
import scheduler.model.CustomerDetail;
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
    
    public static void addCustomer(int cityId, String addressOne, 
            String addressTwo,String postalCode, String phone, String customerName) {

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
            System.out.println(addressId);
                        
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
    }
    
    public static void retrieveAppointmentDetails() {
        
        //variables used in appointment details table
        String customerName;
        String employeeName;
        String location;
        String date;
        String time;
        
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT customerId, userId, location, start FROM appointment");
            while(rs.next()) {
                location = rs.getString("location");
                date = rs.getDate("start").toString();
                time = rs.getTime("start").toString();
                int customerId = rs.getInt("customerid");
                int userId = rs.getInt("userId");
                
                stmt = conn.createStatement();
                ResultSet cust = stmt.executeQuery("SELECT customerName FROM customer WHERE customerid=" + customerId);
                while(cust.next()) {
                    customerName = cust.getString("customerName");
                    
                    stmt = conn.createStatement();
                    ResultSet emp = stmt.executeQuery("SELECT userName FROM user WHERE userId=" + userId);
                    while(emp.next()) {
                        employeeName = emp.getString("userName");
                        AppointmentDetails.addAppointmentDetails(customerName, employeeName, location, date, time);
                    }
                }
              
            }
            
            
        } catch(SQLException e) {
            
        }
    }
    
    public static void deleteCustomer(String customerName) throws SQLException {
        String sql = "UPDATE customer SET active = 1 WHERE customerName = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, customerName);
        System.out.println(ps);
        ps.executeUpdate();
    }
    
    public static int getUserId() {
        return databaseUserId;
    }

    public static void setUserId(int aUserId) {
        databaseUserId = aUserId;
    }
}