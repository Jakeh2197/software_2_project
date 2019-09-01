/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.view.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import scheduler.Launcher;
import static scheduler.Launcher.main;
import scheduler.model.AppointmentCount;
import scheduler.model.AppointmentDetails;
import scheduler.model.CalenderDetails;
import scheduler.model.CustomerDetail;
import scheduler.model.EmployeeAppointments;
import scheduler.model.EmployeeDetails;
import scheduler.model.EmployeeDetails.EmployeeName;
import static scheduler.model.EmployeeDetails.employeeNames;
import scheduler.model.upcomingAppointments;
import scheduler.model.upcomingAppointments.App;
import static scheduler.view.controller.LoginScreenController.helper;

/**
 * FXML Controller class
 *
 * @author Jake
 */
public class MainScreenController implements Initializable {
    
    @FXML
    private Button logoutButton;
    @FXML
    private TableView upcomingAppointmentsTable;
    @FXML
    private TableColumn<App, String> customerColumn;
    @FXML
    private TableColumn<App, String> appointmentTypeColumn;
    @FXML
    private TableColumn<App, String> dateColumn;
    @FXML
    private TableColumn<App, String> timeColumn;   
    @FXML
    private Button customersButton;
    @FXML
    private Button appointmentsButton;
    @FXML
    private Button monthlyCalenderButton;
    @FXML
    private Button weeklyCalenderButton;
    @FXML
    private Button employeeSchedulesButton;
    @FXML 
    private Button appointmentCountButton;
    @FXML
    private Button changeLogButton;
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
//    public static ObservableList<EmployeeAppointments> employeeAppointments;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                
        //populate upcoming appointments table with applicable data
        upcomingAppointmentsTable.setItems(upcomingAppointments.appointments);
        customerColumn.setCellValueFactory(new PropertyValueFactory("customerName"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory("appType"));
        dateColumn.setCellValueFactory(new PropertyValueFactory("appDate"));
        timeColumn.setCellValueFactory(new PropertyValueFactory("appTime"));
        upcomingAppointmentsTable.getColumns().setAll(customerColumn, appointmentTypeColumn, dateColumn, timeColumn);
        
        try {
            int appointments = helper.checkForImmediateAppoitments();
            if (appointments != 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment starting soon");
                alert.setHeaderText("Alert!");
                alert.setContentText("You have " + appointments + " appointments starting soon.");
                alert.showAndWait();
            }
        } catch (SQLException | ParseException ex) {
            
        }
        
    }    
    
    @FXML
    private void logoutButtonHandler(ActionEvent event) throws IOException, SQLException {
                
        upcomingAppointmentsTable.getItems().clear();
        helper.closeConnection();
        
        Parent root = FXMLLoader.load(getClass().
                getResource("../LoginScreen.fxml")); 
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
        
        Stage closeStage = (Stage) logoutButton.getScene().getWindow();
        closeStage.close();
        
    }
    
    @FXML
    private void customersButtonHandler(ActionEvent event) throws IOException, ClassNotFoundException {
        
        helper.retrieveCustomerDetails();
        
        Parent root = FXMLLoader.load(getClass().
                getResource("../CustomersScreen.fxml")); 
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
        
        /*
            This lambda expression allows me to easily clear the customer table
            on the next screen whenever the window is closed. This blocks the
            program from populating the table with duplicate entries. 
        */
        stage.setOnCloseRequest((WindowEvent we) -> {
            CustomerDetail.customerDetails.clear();
        });
                
    }
    
    @FXML
    private void appointmentsButtonHandler(ActionEvent event) throws IOException, ParseException {
        
        helper.retrieveAppointmentDetails();
        
        Parent root = FXMLLoader.load(getClass().
                getResource("../AppointmentsScreen.fxml")); 
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
        
        stage.setOnCloseRequest((WindowEvent e) -> {
            upcomingAppointments.appointments.clear();
            
        });
        
        /*
            This lambda expression allows me to easily clear the appointment table
            on the next screen whenever the window is closed. This blocks the
            program from populating the table with duplicate entries. 
        */
        stage.setOnCloseRequest((WindowEvent we) -> {
            AppointmentDetails.appDetails.clear();
        });
        
    }
    
    @FXML
    private void employeeSchedulesButton(ActionEvent event) throws IOException, SQLException, ParseException {
        
        ScrollPane window = new ScrollPane();
        window.setMaxHeight(650);
        VBox content = new VBox();
        
        helper.retrieveEmployeeDetails();
        
        
        for(EmployeeName n : employeeNames) {
            
            ObservableList<EmployeeAppointments> employeeAppointments = FXCollections.observableArrayList();
            
            helper.retrieveEmployeeAppointments(n.getName(), employeeAppointments);
                        
            TableView schedulesTable = new TableView();
            
            TableColumn<String, EmployeeName> employeeColumn = new TableColumn<>();
            TableColumn<String, EmployeeAppointments> customerNameColumn = new TableColumn<>("Customer");
            TableColumn<String, EmployeeAppointments> dateColumn = new TableColumn<>("Date");           
            TableColumn<String, EmployeeAppointments> startTimeColumn = new TableColumn<>("Start Time");
            TableColumn<String, EmployeeAppointments> typeColumn = new TableColumn<>("Appointment Type");
            TableColumn<String, EmployeeAppointments> locationColumn = new TableColumn<>("Location");
            
            schedulesTable.setMaxHeight(200);
            schedulesTable.setMinWidth(625);
            employeeColumn.setMinWidth(625);
            employeeColumn.setText(n.getName());
            customerNameColumn.setMinWidth(125);
            dateColumn.setMinWidth(125);
            startTimeColumn.setMinWidth(125);
            typeColumn.setMinWidth(125);
            locationColumn.setMinWidth(125);
            
            employeeColumn.getColumns().addAll(customerNameColumn, dateColumn, startTimeColumn, typeColumn, locationColumn); 
            
            schedulesTable.setItems(employeeAppointments);
            customerNameColumn.setCellValueFactory(new PropertyValueFactory("customerName"));
            dateColumn.setCellValueFactory(new PropertyValueFactory("date"));
            startTimeColumn.setCellValueFactory(new PropertyValueFactory("startTime"));
            typeColumn.setCellValueFactory(new PropertyValueFactory("type"));
            locationColumn.setCellValueFactory(new PropertyValueFactory("location"));
            
            schedulesTable.getColumns().setAll(employeeColumn);
            content.getChildren().add(schedulesTable);
   
        }
        
        window.setContent(content);

        Parent root = FXMLLoader.load(getClass().
                getResource("../EmployeeSchedulesScreen.fxml")); 
        Scene scene = new Scene(window);
        Stage stage = new Stage();
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
        
        
        stage.setOnCloseRequest((WindowEvent we) -> {
            EmployeeDetails.employeeNames.clear();
        });
        
    }
    
    @FXML
    private void monthlyCalenderButtonHandler(ActionEvent event) throws IOException, SQLException, ParseException {
        
        helper.retrieveMontlyCalender();
        
        Parent root = FXMLLoader.load(getClass().
                getResource("../CalenderScreen.fxml")); 
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
        
        stage.setOnCloseRequest((WindowEvent we) -> {
            CalenderDetails.calenderDetails.clear();
        });
        
    }
    
    @FXML
    private void weeklyCalenderButtonHandler(ActionEvent event) throws IOException, SQLException, ParseException {
        
        helper.retrieveWeeklyCalender();
        
        Parent root = FXMLLoader.load(getClass().
                getResource("../CalenderScreen.fxml")); 
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
        
        stage.setOnCloseRequest((WindowEvent we) -> {
            CalenderDetails.calenderDetails.clear();
        });
        
    }
    
    @FXML 
    private void appointmentCountButtonHandler(ActionEvent event) throws SQLException, IOException {
        
        System.out.println("Made it");
        
        ScrollPane window = new ScrollPane();
        window.setMaxHeight(650);
        VBox content = new VBox();
        
        ObservableList<AppointmentCount> appointmentCount = FXCollections.observableArrayList();
        
        helper.appointmentTypeCount(appointmentCount);
        
        TableView appointmentCountTable = new TableView();
        
        TableColumn<String, AppointmentCount> appointmentTypeColumn = new TableColumn("Appointment Type");
        TableColumn<Integer, AppointmentCount> countColumn = new TableColumn("Count");
        
        appointmentCountTable.setMaxHeight(400);
        appointmentTypeColumn.setMinWidth(150);
        countColumn.setMinWidth(150);
        
        appointmentCountTable.setItems(appointmentCount);
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory("appointmentType"));
        countColumn.setCellValueFactory(new PropertyValueFactory("count"));
        
        appointmentCountTable.getColumns().setAll(appointmentTypeColumn, countColumn);
        content.getChildren().add(appointmentCountTable);
        
        window.setContent(content);

        Parent root = FXMLLoader.load(getClass().
                getResource("../appointmentTypeCount.fxml")); 
        Scene scene = new Scene(window);
        Stage stage = new Stage();
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
        
    }
    
    @FXML 
    private void changeLogButtonHandler(ActionEvent event) throws IOException {
        
        AnchorPane window = new AnchorPane();
        window.setMinHeight(150);
        window.setMinWidth(450);
        window.setMaxWidth(450);
        
        TextArea text = new TextArea();
        
        window.getChildren().add(text);
        
        
        File file = new File("src/scheduler/resources/changelog.");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while((line = br.readLine()) != null) {
            text.appendText(line + "\n");
            
        }
        
        Parent root = FXMLLoader.load(getClass().
                getResource("../appointmentTypeCount.fxml")); 
        Scene scene = new Scene(window);
        Stage stage = new Stage();
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
        
    }

}
