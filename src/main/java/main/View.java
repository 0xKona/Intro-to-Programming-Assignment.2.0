package main;

import controller.ItemController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * View handles displaying the CLI user interface
 * <p>Methods:</p>
 */
public class View {

    public static Stage currentStage;

    // Loads CSS Styling
    private static void loadCss(Scene scene) {
        scene.getStylesheets().add(Objects.requireNonNull(View.class.getResource("/styles/global.css")).toExternalForm());
    }

    /**
     * Main method to load and initialize initial screen
     * @param stage JavaFX Stage
     * @throws IOException Thrown if fails to find or load FXML file
     */
    public static void initializeView(Stage stage) throws IOException {
        currentStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/views/main-menu.fxml"));
        Scene mainScene = new Scene(fxmlLoader.load(), 1280,720);
        currentStage.setScene(mainScene);
        currentStage.setTitle("Inventory Management System");
        loadCss(mainScene);
        currentStage.show();
    }

    /**
     * Navigates the user to the Transaction Report.
     * @throws IOException error.
     */
    public static void navigateToTransactionReport() throws IOException {
        System.out.println("Navigating to transaction report");
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/views/transactions-view.fxml"));
        Scene dashboardScene = new Scene(fxmlLoader.load(), 1280, 720);
        loadCss(dashboardScene);
        currentStage.setScene(dashboardScene);
    }

    /**
     * Navigates the user to the Main Menu
     * @throws IOException error.
     */
    public static void navigateToMainMenu() throws IOException {
        System.out.println("Navigating to main menu");
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/views/main-menu.fxml"));
        Scene newScene = new Scene(fxmlLoader.load(), 1280, 720);
        loadCss(newScene);
        currentStage.setScene(newScene);
    }

    /**
     * Navigates the user to the Add New Item form.
     * @throws IOException error
     */
    public static void navigateToAddNewItem() throws IOException {
        System.out.println("Navigating to add new item");
        ItemController.initializeNewItem();
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/views/add-new-item-view.fxml"));
        Scene newScene = new Scene(fxmlLoader.load(), 1280, 720);
        loadCss(newScene);
        currentStage.setScene(newScene);
    }

    /**
     * Navigates the user to the View Items screen.
     * @throws IOException error.
     */
    public static void navigateToViewItems() throws IOException {
        System.out.println("Navigating to view items");
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/views/edit-items-view.fxml"));
        Scene newScene = new Scene(fxmlLoader.load(), 1280, 720);
        loadCss(newScene);
        currentStage.setScene(newScene);
    }
}
