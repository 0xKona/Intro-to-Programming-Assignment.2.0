package main;

import controller.StoreController;
import storage.FileManager;

/**
 * Main class of that starts the application, initializes the storage and then loads the main menu.
 */
public class Store {
    /**
     * Main method of the application where the program starts from.
     */
    public static void main(String[] args) {
        FileManager.initializeStorage(); // Initialize the storage files before starting the app.
        StoreController.start();
    }
}
