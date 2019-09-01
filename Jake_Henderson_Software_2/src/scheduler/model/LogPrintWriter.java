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

        File changeLog = new File("src/scheduler/resources/changelog.txt");
        
        changeLog.setWritable(true);
        FileWriter fileWriter = new FileWriter(changeLog, true);
        try (PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println(change);
        }
        changeLog.setReadOnly();
    }
    
}
