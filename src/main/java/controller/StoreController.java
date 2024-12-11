package controller;

import javafx.fxml.FXML;
import main.View;

import java.io.IOException;

/**
 * Handles the main menu display and routing to the various programs.
 * <p>Methods:</p>
 */
public class StoreController {

    /**
     * FXML Method for switching scene to the add new item scene
     * @throws IOException failure to load FXML file
     */
    @FXML
    public void navigateToAddNewItem() throws IOException {
        View.navigateToAddNewItem();
    }

    /**
     * FXML Method for switching scene to items view
     * @throws IOException failure to load FXML file
     */
    @FXML
    public void navigateToViewItems() throws IOException {
        View.navigateToViewItems();
    }

    /**
     * FXML Method for switching scene to transaction report
     * @throws IOException failure to load FXML file
     */
    @FXML
    public void navigateToTransactionReport() throws IOException {
        View.navigateToTransactionReport();
    }

    /**
     * FXML Method for closing the program
     */
    @FXML
    public void closeProgram() {
        exitProgram(0);
    }

    /**
     * Helper function to exit the program and test safely without terminating tests, we can mock this method to test
     * the closeProgram method, without it when we would run the closeProgram method in the JUnit tests, the tests would
     * exit since System.exit is called. when testing closeProgram, mock this method
     * @param statusCode int
     */
    public static void exitProgram (int statusCode) {
        System.exit(statusCode);
    }
}
