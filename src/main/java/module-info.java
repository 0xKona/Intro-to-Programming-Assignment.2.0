module com.inventorymanagement {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens main to javafx.fxml;  // Allow JavaFX to reflectively access classes in main package
    opens controller to javafx.fxml;
    opens storage to javafx.base;
    opens models to javafx.base;
    opens utils to javafx.base;

    exports main;
}