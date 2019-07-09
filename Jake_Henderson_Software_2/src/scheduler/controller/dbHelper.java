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
import scheduler.model.CustomerDetail;
import scheduler.model.upcomingAppointments;

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
    private static String databaseUserName = null;
    private static String databaseUserPasswrod = null;
    
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
                databaseUserName = rs.getString("userName");
                databaseUserPasswrod = rs.getString("password");
                setUserId(rs.getInt("userId"));
                       
                //compare entered userName and password to database values
                if(submittedUserName.equals(databaseUserName) && submittedUserPassword.equals(databaseUserPasswrod)) {
                    confirmation = true;
                }                             
            }
            

            
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return confirmation;
        
    }
        
    public static void retrieveUpcomingAppointments() throws ClassNotFoundException, SQLException {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbHelper.conn = (Connection) DriverManager.getConnection(DB_URL, dbUserName, dbPassword);   
        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        //variables used in upcomingAppointmentsTable
        int customerId = 0;
        int userId = 0;
        String customerName = null;
        String appointmentType = null;
        String appointmentDate = null;
        String appointmentTime = null;
                        
        //retrieve customerId from customer table
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT type, start, customerId FROM appointment WHERE userID=" + userId);
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
        
        String customerName = null;
        String address = null;
        int customerId = 0;
        
        //retrieve customer information used in table
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM customer");
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
        
        System.out.println("Made it 1");      

        
        
        
        //works
//        String sql = "INSERT INTO country (country, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES ('Cuba', now(), 'test', now(), 'test')";

        //works
//        String sql = "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES ('work', 'home', '29', '12345', '1234567', now(), 'test', now(), 'test')";
        
        //works
//        String sql = "INSERT INTO appointment (customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy)"
//                + "VALUES ('51', '0', 'Insert Test', 'Insert', 'Phoenix', 'Email', 'Initial', 'www.insert.com', now(), now(), now(), 'test', now(), 'test')";

        //does not work
//        String sql = "INSERT INTO user (userName, password, active, createBy, createDate, lastUpdate, lastUpdatedBy) VALUES ('test1', 'test1', '0', 'test', '2019-06-27 03:02:43', '2019-06-27 03:02:43', 'test')"; 
     

        //works
//        String sql = "INSERT INTO customer(customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateby) VALUES (?, '73', '0', now(), 'test', now(), 'test')";

        //works
//        String sql = "INSERT INTO city (city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES ('Murray', '10', now(), 'test', now(), 'test')";
        try {
//            System.out.println("Made it 2");
//            PreparedStatement ps = conn.prepareStatement(sql);
//            System.out.println("Made it 3");
////            ps.setString(1, "jake");
//            System.out.println("Made it 4");
//            ps.execute();
//            System.out.println("Made it 5");
//            ps = conn.prepareStatement("SELECT LAST_INSERT_ID() FROM address");
//            ResultSet rs = ps.executeQuery();
//            rs.next();
//            System.out.println("Made it 6");

//            String sql = "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (" + 
//                    "'" + dbAddressOne + "'" + ", " + "'" + dbAddressTwo + "'" + ", " + "'" + dbCityId + "'" + ", " + "'" + dbPostalCode + "'" + ", " + "'" + dbPhone + "'" + ", " + date + ", 'test'" + ", " + date + ", 'test'" + ")";

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
            
            
            System.out.println("Made it 2");
            //insert customer data into customer table
            String customer = "INSERT INTO customer(customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateby) VALUES (?, ?, ?, now(), ?, now(), ?)";
            PreparedStatement test = conn.prepareStatement(customer);
            test.setString(1, customerName);
            test.setString(2, addressId);
            test.setString(3, Integer.toString(0));
            test.setString(4, databaseUserName);
            test.setString(5, databaseUserName);
            System.out.println("Made it 3");
            System.out.println(test);
            test.execute();
            System.out.println("Made it 4");
            
            
            
//            PreparedStatement addressTable = conn.prepareStatement("INSERT INTO address (address, address2, cityId, "
//                    + "postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) "
//                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            
            
//            PreparedStatement ps = conn.prepareStatement(addressTableInsert);
//            ps.setString(1, "123 Fake Street");
//            ps.execute();
//            ps = conn.prepareStatement("SELECT LAST_INSERT_ID() FROM country");
//            ResultSet rs = ps.executeQuery();
//            rs.next();
//            System.out.println(rs.getString(1));
//            System.out.println("Made it 3");
       
//            addressTable.setString(1, dbAddressOne);
//            addressTable.setString(2, dbAddressTwo);
//            addressTable.setString(3, Integer.toString(dbCityId));
//            addressTable.setString(4, dbPostalCode);
//            addressTable.setString(5, dbPhone);
//            addressTable.setString(6, date);
//            addressTable.setString(7, databaseUserName);
//            addressTable.setString(8, date);
//            addressTable.setString(9, databaseUserName);
//            
//            addressTable.executeUpdate();
//            System.out.println("Made it");
//            
//            ResultSet rs;
//            try {
//                System.out.println("Made it");
//                stmt = conn.createStatement();
//                rs = stmt.executeQuery("SELECT addressid FROM address WHERE address=" + dbAddressOne);
//                while(rs.next()) {
//                    dbAddressId = rs.getInt("addressid");
//                    System.out.println(dbAddressId);
//                }
//            } catch(SQLException e) {
//                
//            }
//            System.out.println("Made it");
//            PreparedStatement customerTable = conn.prepareStatement("INSTERT INTO customer "
//                    + "(customerName, addressId, createDate, createdBy, lastUpdate, lastUpdateBy)"
//                    + "VALUES (?, ?, ?, ?, ?, ?, ?");
//            
//            customerTable.setString(1, dbCustomerName);
//            customerTable.setString(2, Integer.toString(dbAddressId));
//            customerTable.setString(3, "0");
//            customerTable.setString(4, date);
//            customerTable.setString(5, databaseUserName);
//            customerTable.setString(6, date);
//            customerTable.setString(7, databaseUserName);
//            
//            customerTable.executeUpdate();
            
        } catch(SQLException e) {
            
        }
    }
    
    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int aUserId) {
        userId = aUserId;
    }
}