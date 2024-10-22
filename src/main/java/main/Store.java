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

    /**
     * Called by main to starts the JavaFX application, initializing the database and setting the initial scene
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws Exception exception if fails to load scene or initialize database.
     */
    @Override
    public void start(Stage stage) throws Exception {
        DatabaseManager.initializeDatabase();
        View.initializeView(stage);
    }
}
