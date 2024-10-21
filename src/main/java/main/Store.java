package main;

import javafx.stage.Stage;
import storage.DatabaseManager;
import javafx.application.Application;


/**
 * Main class of that starts the application, initializes the storage and then loads the main menu.
 */
public class Store extends Application {
    /**
     * Main method of the application where the program starts from.
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        DatabaseManager.initializeDatabase();
        View.initializeView(stage);
    }
}
