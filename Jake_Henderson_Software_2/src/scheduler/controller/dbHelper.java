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
import java.sql.ResultSet;
import java.util.ArrayList;
import scheduler.model.upcomingAppointments;
import scheduler.model.upcomingAppointments.App;


/**
 *
 * @author Jake
 */
public class dbHelper {     
    
        
    private static String submittedUserName;
    private static String submittedUserPassword;
    private static int userId;
    private static int[] custId;   
  
    //variables for database connection
    private static Connection conn;
    private static Statement stmt;
    private static String dbUserName;
    private static String dbPassword;
    private static boolean confirmation = false;
    
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
        ResultSet rs;
        try {
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT userName, password, userId FROM user");
            
            //retrieve userName, password and ID from database
            while(rs.next()) {
                userName = rs.getString("userName");
                userPassword = rs.getString("password");
                setUserId(rs.getInt("userId"));
                       
                //compare entered userName and password to database values
                if(submittedUserName.equals(userName) && submittedUserPassword.equals(userPassword)) {
                    confirmation = true;
                }                             
            }
            
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return confirmation;
        
    }
    
    public static void retrieveUpcomingAppointments() throws ClassNotFoundException {
        
        //variables used in upcomingAppointmentsTable
        String customerName = null;
        String appointmentType = null;
        String appointmentDate = null;
        String appointmentTime = null;
                
        //connect to database
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            dbHelper.conn = (Connection) DriverManager.getConnection(DB_URL, dbUserName, dbPassword);
            
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        //retrieve customerId from customer table
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT customerId FROM appointment WHERE userId=" + userId);
            rs.last();
            custId = new int[rs.getRow()];
            rs.beforeFirst();
            while(rs.next()) {
                for(int i = 0; i < custId.length; i++) {
                    custId[i] = rs.getInt("customerId");
                }
                
            }
                      
        } catch(SQLException e) {
            
        }
        
        //retrieve data for variables used in upcomingAppointmentsTable
        try {
            stmt = conn.createStatement();
            for(int i = 0; i < custId.length; i++) {
                rs = stmt.executeQuery("SELECT customerName FROM customer WHERE customerid=" + custId[i]);
                while(rs.next()) {
                    customerName = rs.getString("customerName");
                }   
            }
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT type, start FROM appointment WHERE userID=" + userId);
            while(rs.next()) {
                appointmentType = rs.getString("type");
                appointmentDate = rs.getDate("start").toString();
                appointmentTime = rs.getTime("start").toString();
            }

            //use variables to contstuct object to be added to observable list
            upcomingAppointments.addAppointment(customerName, appointmentType, appointmentDate, appointmentTime);
            
        } catch(SQLException e) {
            e.printStackTrace();
        }       
    }
   
    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int aUserId) {
        userId = aUserId;
    }
}