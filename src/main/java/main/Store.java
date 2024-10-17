package main;

import controller.StoreController;
import storage.FileManager;

/**
 * Main class of that starts the application, initializes the storage and then loads the main menu.
 */
public class Store {
    /**
     * Main method
     */
    public static void main(String[] args) {
        // Initialize the storage files before starting the app.
        FileManager.initializeStorage();
        StoreController.start();
    }
}
