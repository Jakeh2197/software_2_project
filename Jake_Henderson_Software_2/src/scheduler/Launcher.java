package scheduler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scheduler.controller.dbHelper;


public class Launcher extends Application{
           
    public static dbHelper helper = new dbHelper();
        
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().
                getResource("view/LoginScreen.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("Scheduler");
        
        stage.setScene(scene);
        stage.show();
    }

   
    public static void main(String[] args) {

        launch(args);

    }
        
}
