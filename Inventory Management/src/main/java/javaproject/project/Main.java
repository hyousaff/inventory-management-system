package javaproject.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;



// JAVA DOC FOLDER LOCATION: C482\Project\JavaDoc

/**
 *
 *
 * @author Hamza Yousaf
 *
 * <p>
 *     Creating the main stage and loading the scene
 * </p>
 */



public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Mainform.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hamza - Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /** FUTURE ENHANCEMENT: Create user login ability to the Inventory Management System, with each login
     having limited permissions to perform actions. e.g. an associate can only view/add, A manager permission is
     needed to edit and remove.

     @param args the command line arguments.*/
    public static void main(String[] args) {
        launch();
    }
}