package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.View;
import models.Transaction;
import storage.TransactionStorage;
import utils.TimestampGenerator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Transaction controller handles the logic fetching the list of transactions from storage and displaying it to the user.
 * <p>Methods:</p>
 */
public class TransactionController {

    @FXML
    public void navigateHome() throws IOException {
        View.navigateToMainMenu();
    }

    @FXML
    public TableView<Transaction> transactionTable;

    public void initialize() {
        String startOfDay = TimestampGenerator.getCurrentDayStart();
        String endOfDay = TimestampGenerator.getCurrentDayEnd();

        initializeTableView(startOfDay, endOfDay);
    }

    // Initializes the transactionTableView component.
    private void initializeTableView(String startDate, String endDate) {
        transactionTable.setEditable(Boolean.FALSE); // This table is not editable so this is set to false.
        // Generate the ArrayList of Transactions, providing the start and end date.
        ArrayList<Transaction> transactionsList = TransactionStorage.readTransactions(startDate, endDate);
        // Convert to an Observable List, This allows the TableView to update when the list changes.
        ObservableList<Transaction> observableTransactions = FXCollections.observableArrayList(transactionsList);

        // ID Column
        TableColumn<Transaction, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<Transaction, Integer>("id"));

        // Description Column
        TableColumn<Transaction, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("description"));

        // Quantity Change Column
        TableColumn<Transaction, Double> quantityColumn = new TableColumn<>("Quantity Change");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("quantityChange"));

        // Value Change Column
        TableColumn<Transaction, Double> totalValueColumn = new TableColumn<>("Value Change");
        totalValueColumn.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("valueChange"));

        // Quantity Remaining Column
        TableColumn<Transaction, Double> quantityRemainingColumn = new TableColumn<>("Quantity Remaining");
        quantityRemainingColumn.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("quantityRemaining"));

        // Transaction Type Column
        TableColumn<Transaction, String> transactionTypeColumn = new TableColumn<>("Transaction Type");
        transactionTypeColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("transactionType"));

        // Timestamp Column
        TableColumn<Transaction, String> timestampColumn = new TableColumn<>("Timestamp");
        timestampColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("timestamp"));

        timestampColumn.setSortType(TableColumn.SortType.ASCENDING);

        // Add the Items to the table view
        transactionTable.setItems(observableTransactions);
        // Sort into columns
        transactionTable.getColumns().addAll(Arrays.asList(idColumn, descriptionColumn, quantityColumn, totalValueColumn, quantityRemainingColumn, transactionTypeColumn, timestampColumn));
        // Ensure that the table is sorted in order of timestamp.
        transactionTable.getSortOrder().add(timestampColumn);
        transactionTable.sort();
        // Resize to stretch to window size.
        transactionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
    }

}
