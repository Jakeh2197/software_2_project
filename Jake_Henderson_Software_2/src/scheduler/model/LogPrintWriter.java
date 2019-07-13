/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 *
 * @author Jake
 */
public class LogPrintWriter {
    
    public static void writeLogin(String login) throws IOException {
        
        File logins = new File("src/scheduler/resources/logins.txt");
                
        logins.setWritable(true);
        FileWriter fileWriter = new FileWriter(logins, true);
        try (PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println(login);
        }
        logins.setReadOnly();
    }
    
    public static void writeChangeLog(String change) throws IOException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        LocalDate now = LocalDate.now();

        File changeLog = new File("src/scheduler/resources/changelog" + now + ".txt");
        
        changeLog.setWritable(true);
        FileWriter fileWriter = new FileWriter(changeLog, true);
        try (PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println(change);
        }
        changeLog.setReadOnly();
    }
    
}
