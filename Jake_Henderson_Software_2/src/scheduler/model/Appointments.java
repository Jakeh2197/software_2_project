
package scheduler.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;

/**
This class is used to populate the upcoming appointments table on the 
main screen
 */
public class Appointments {
    
    public static ObservableList<App> appointments = FXCollections.observableArrayList();
    
    public static void addAppointment(String customerName, String appointmentType, String appointmentDate, String appointmentTime) {
        App app = new App(customerName, appointmentType, appointmentDate, appointmentTime);
        Appointments.appointments.add(app);
    }
    
    public static class App {
        
        private SimpleStringProperty customerName = new SimpleStringProperty();
            public String getCustomerName() {
                return customerName.get();
            }
            public final void setCustomerName(String customerName) {
                this.customerName.set(customerName);
            }
        
        private SimpleStringProperty appType = new SimpleStringProperty();
            public String getAppType() {
                return appType.get();
            }
            public final void setAppType(String appType) {
                this.appType.set(appType);
            }
        
        private SimpleStringProperty appDate = new SimpleStringProperty();
            public String getAppDate() {
                return appDate.get();
            }
            public final void setAppDate(String appDate) {
                this.appDate.set(appDate);
            }
        
        private SimpleStringProperty appTime = new SimpleStringProperty();
            public String getAppTime() {
                return appTime.get();
            }
            public final void setAppTime(String appTime) {
                this.appTime.set(appTime);
            }
        
        private App(String customerName, String appType, String appDate, String appTime) {
            this.customerName.set(customerName);
            this.appType.set(appType);
            this.appDate.set(appDate);
            this.appTime.set(appTime);
        }

        

    }
}
