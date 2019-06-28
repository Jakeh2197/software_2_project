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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jake
 */
public abstract class dbConnector {
    
    private static String submittedUserName;
    private static String submittedUserPassword;
    private static String userName;
    private static String userPassword;
    
    //variables for database connection
    private static Connection conn;
    private static Statement stmt;
    private static String dbUserName;
    private static String dbPassword;
    private static boolean confirmation = false;
    
    public static boolean connect(String userName, String userPassword) throws ClassNotFoundException, SQLException, IOException {
        
        submittedUserName = userName;
        submittedUserPassword = userPassword;
        
        /*
            configure db connection details with:
            Server name: 52.206.157.109 
            Database name: U04Ftj
            Username: U04Ftj
            Password: 53688224861
        */
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://52.206.157.109/U04Ftj";
        
        
        //credentials for database
        dbUserName = "U04Ftj";
        dbPassword = "53688224861";
        
        //open connection to database
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            conn = (Connection) DriverManager.getConnection(DB_URL, dbUserName, dbPassword);
            
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        //use sql to retrieve username and password
        ResultSet rs;
        try {
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT userName, password FROM user");
            
            //compare entered username and password to values in database
            while(rs.next()) {
                userName = rs.getString(1);
                userPassword = rs.getString(1);
                
                if(submittedUserName.equals(userName) && submittedUserPassword.equals(userPassword)) {
                    confirmation = true;
                }                             
            }
            
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return confirmation;
        
    }
    
}
