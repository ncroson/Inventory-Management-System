package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;

/**
 * @author Nathan Croson
 * JavaDoc located in InventoryManagementSystem/javadoc/index.html
 * Main class of Inventory management system.  Program starts here.
 * FUTURE ENHANCEMENT
 * Display logical and type errors alerts for checking datain one alert message.
 * FUTURE ENHANCEMENT
 * add sound effects to alert to errors in data entry
 * FUTURE ENHANCEMENT
 * Inventory of parts updated when associated parts are added or removed from product
 */
public class Main extends Application {

    /**
     * Start method of main. Program starts here.  Loads Main Menu screen first.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{



        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    /**
     * Main method.
     * @param args
     */
    public static void main(String[] args) {



        launch(args);
    }
}
