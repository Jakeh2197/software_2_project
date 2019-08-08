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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import javafx.scene.control.Alert;
import scheduler.model.AppointmentDetails;
import scheduler.model.CustomerDetail;
import scheduler.model.DaysOpen;
import scheduler.model.EmployeeDetails;
import scheduler.model.LogPrintWriter;
import scheduler.model.upcomingAppointments;

/**
 *
 * @author Jake
 */
public class dbHelper {     
      
    //this will be used to retrieve username whenver changes are made
    private static int databaseUserId;
  
    //variables for database connection
    private static Connection conn;
    private static Statement stmt;
    private static String dbUserName;
    private static String dbPassword;
    
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

    public boolean connect(String userName, String userPassword) throws ClassNotFoundException, SQLException, IOException {
        
        boolean confirmation = false;

        //open connection to database
        try {
            
            String databaseUserName = null;
            String databaseUserPassword;
            
            Class.forName("com.mysql.jdbc.Driver");
            dbHelper.conn = (Connection) DriverManager.getConnection(DB_URL, dbUserName, dbPassword); 
            
            //use sql to retrieve username and password
            try {

                String sql = "SELECT userName, password, userId FROM user WHERE userName=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, userName);
                ResultSet rs = ps.executeQuery();

                //retrieve userName, password and ID from database
                while(rs.next()) {
                    databaseUserName = rs.getString("userName");
                    databaseUserPassword = rs.getString("password");
                    databaseUserId = rs.getInt("userId");

                    //compare entered userName and password to database values
                    if(databaseUserName.equals(userName) && databaseUserPassword.equals(userPassword)) {
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
        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return confirmation;
          
    }
    
    private String retrieveUserName(int userId) throws SQLException {
        //retrieve user name of person making change
        /*this function is used to retrieve the username of the person signed in 
            and making changes to the database in order to log that users information
            for future reference
        */
        String selectUserName = "SELECT userName FROM user WHERE userID=?";
        PreparedStatement prepstate = conn.prepareStatement(selectUserName);
        prepstate.setString(1, Integer.toString(databaseUserId));
        String userName = null;
        ResultSet result = prepstate.executeQuery();
        while(result.next()) {
            userName = result.getString("userName");
        }
        return userName;
    }
        
    public void retrieveUpcomingAppointments() throws ClassNotFoundException, SQLException {
        
        //variables used in upcomingAppointmentsTable
        int customerId;
        String customerName = null;
        String appointmentType;
        String appointmentDate;
        String appointmentTime;
                        
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
    
    public void retrieveCustomerDetails() throws ClassNotFoundException {
                
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
    
    public void retrieveEmployeeDetails() throws SQLException {
        
        String employeeName;
        
        String retrieveNames = "SELECT userName FROM user";
        PreparedStatement ps = conn.prepareStatement(retrieveNames);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            employeeName = rs.getString("userName");
            EmployeeDetails.addEmployeeName(employeeName);
        }
    }
    
    public void addCustomer(String city, String addressOne, 
            String addressTwo,String postalCode, String phone, String customerName, String country) throws IOException, SQLException {
        
        String userName = this.retrieveUserName(databaseUserId);
        
        //check if the country has already been added, add if not present
        int countryId = 0;
        String countryCheck = "SELECT country, countryid FROM country WHERE country=?";
        PreparedStatement ps2 = conn.prepareStatement(countryCheck);
        ps2.setString(1, country);
        ResultSet rs1 = ps2.executeQuery();
        if(rs1.next() == false) {
            String countryInsert = "INSERT INTO country (country, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, now(), ?, now(), ?)";
            PreparedStatement ps3 = conn.prepareStatement(countryInsert, Statement.RETURN_GENERATED_KEYS);
            ps3.setString(1, country);
            ps3.setString(2, userName);
            ps3.setString(3, userName);
            ps3.executeUpdate();
            
            ResultSet generatedKeys = ps3.getGeneratedKeys();
            while(generatedKeys.next()) {
                countryId = generatedKeys.getInt(1);
            }
        } else {
            countryId = rs1.getInt("countryid");
        }
        
        //check if the city has already been added, add if not present
        int cityId = 0;
        String cityCheck = "SELECT city, cityid FROM city WHERE city=?";
        PreparedStatement ps4 = conn.prepareStatement(cityCheck);
        ps4.setString(1, city);
        ResultSet rs2 = ps4.executeQuery();
        if(rs2.next() == false) {
            String cityInsert = "INSERT INTO city (city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, now(), ?, now(), ?)";
            PreparedStatement ps5 = conn.prepareStatement(cityInsert, Statement.RETURN_GENERATED_KEYS);
            ps5.setString(1, city);
            ps5.setString(2, Integer.toString(countryId));
            ps5.setString(3, userName);
            ps5.setString(4, userName);
            ps5.executeUpdate();
            
            ResultSet generatedKeys = ps5.getGeneratedKeys();
            while(generatedKeys.next()) {
                cityId = generatedKeys.getInt(1);
            }
            
        } else {
            cityId = rs2.getInt("cityid");
        }
        
        /*
            search to see if customer information is already in database
            using customername and address
        */
        String checkForCustomer = "SELECT customerName FROM customer WHERE customerName=?";
        PreparedStatement sql = conn.prepareStatement(checkForCustomer);
        sql.setString(1, customerName);
        ResultSet nameCheck = sql.executeQuery();
        
        String checkForAddress = "SELECT address FROM address WHERE address=?";
        PreparedStatement sql1 = conn.prepareStatement(checkForAddress);
        sql1.setString(1, addressOne);
        ResultSet addressCheck = sql1.executeQuery();
        if(nameCheck.next() == false & addressCheck.next() == false) {
            
            try {
                //insert customer data into address table
                String address = "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, ?, ?, ?, now(), ?, now(), ?)";
                PreparedStatement ps = conn.prepareStatement(address);
                ps.setString(1, addressOne);
                ps.setString(2, addressTwo);
                ps.setString(3, Integer.toString(cityId));
                ps.setString(4, postalCode);
                ps.setString(5, phone);
                ps.setString(6, userName);
                ps.setString(7, userName);
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
                ps1.setString(4, userName);
                ps1.setString(5, userName);
                ps1.execute();
            
            } catch(SQLException e) {

            }
            String change = userName + " added new customer: " + customerName;
            LogPrintWriter.writeChangeLog(change);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Customer Already exists");
            alert.setHeaderText("Error!");
            alert.setContentText("Customer already exists, please select Update Customer option on previous screen");
            alert.showAndWait();
        }
        
    }
    
    public void retrieveAppointmentDetails() {
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
    
    public void deleteCustomer(String customerName) throws SQLException, IOException {
        
        String userName = this.retrieveUserName(databaseUserId);
        
        String sql = "UPDATE customer SET active = 1 WHERE customerName = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.executeUpdate();
        
        String change = userName + " deleted customer: " + customerName;
        LogPrintWriter.writeChangeLog(change);
    }
    
    public void addAppointment(String customerName, String employeeName, String title, String description, String location, String contact, String type, String startDate,  String endDate) throws SQLException, DateTimeParseException {
                
        String userName = this.retrieveUserName(databaseUserId);
        
                
        int customerId;
        int userId;

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
                ps2.setString(9, startDate.toString());
                ps2.setString(10, endDate.toString());
                ps2.setString(11, userName);
                ps2.setString(12, userName);
                ps2.executeUpdate();
            }
        }
  
    }
    
    public void deleteAppointment(int appointmentId) throws SQLException, IOException {
        
        String userName = this.retrieveUserName(databaseUserId);
        
        String sql = "DELETE FROM appointment WHERE appointmentid=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, Integer.toString(appointmentId));
        ps.execute();
        
        String change = userName + " deleted appointment: " + appointmentId;
        LogPrintWriter.writeChangeLog(change);
    }   
    
    public static int getUserId() {
        return databaseUserId;
    }
        
    public  void setUserId(int aUserId) {
        databaseUserId = aUserId;
    }
    
    public void closeConnection() throws SQLException {
        conn.close();
        stmt.close();
    }
}