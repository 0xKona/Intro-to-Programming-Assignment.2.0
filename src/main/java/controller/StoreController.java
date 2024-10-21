package controller;

import javafx.fxml.FXML;
import main.View;

import java.io.IOException;

/**
 * Handles the main menu display and routing to the various programs.
 * <p>Methods:</p>
 */
public class StoreController {

    @FXML
    public void navigateToAddNewItem() throws IOException {
        View.navigateToAddNewItem();
    }

    @FXML
    public void navigateToViewItems() throws IOException {
        View.navigateToViewItems();
    }

    @FXML
    public void navigateToTransactionReport() throws IOException {
        View.navigateToTransactionReport();
    }

    @FXML
    public void closeProgram() {
        exitProgram(0);
    }

    // Wrapper method for System.exit that makes it easier to test without actually exiting the program.
    public static void exitProgram (int statusCode) {
        System.exit(statusCode);
    }
}
