package main;

import javafx.application.Application;
import javafx.stage.Stage;
import storage.DatabaseManager;

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
