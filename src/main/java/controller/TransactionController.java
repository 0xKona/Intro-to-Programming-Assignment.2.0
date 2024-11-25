package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import main.View;
import models.Item;
import models.Transaction;
import storage.ItemStorage;
import storage.TransactionStorage;
import utils.TimestampGenerator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Transaction controller handles the logic fetching the list of transactions from storage and displaying it to the user.
 * <p>Methods:</p>
 */
public class TransactionController {

    private String filterQuery;

    @FXML
    public TextField filterTransactionsInput = new TextField();

    @FXML
    public void setFilterTransactionsInput() {
        filterQuery = filterTransactionsInput.getText().toLowerCase();
        initialize();
    }

    @FXML
    public void filterTransactions() {
        initialize();
    }

    @FXML
    public void navigateHome() throws IOException {
        View.navigateToMainMenu();
    }

    @FXML
    public TableView<Transaction> transactionTable;

    private String startDate = TimestampGenerator.getCurrentDayStart();;
    private String endDate = TimestampGenerator.getCurrentDayEnd();;

    public void initialize() {
        // Set initial date to today
        startDatePicker.setValue(LocalDate.now());
        initializeTableView(startDate, endDate);
    }

    @FXML
    public DatePicker startDatePicker = new DatePicker();

    @FXML
    public void setStartDate() {
        startDate = startDatePicker.getValue().toString().concat(" 00:00:00");
        endDate = startDatePicker.getValue().toString().concat(" 23:59:59");
        // Reload Table View with new date settings
        initializeTableView(startDate, endDate);
    }

    // Initializes the transactionTableView component.
    private void initializeTableView(String startDate, String endDate) {
        System.out.println("Initialize TableView with startdate: " + startDate + " and enddate: " + endDate);
        transactionTable.setEditable(Boolean.FALSE); // This table is not editable so this is set to false.
        // Generate the ArrayList of Transactions, providing the start and end date.
        ArrayList<Transaction> storageList = TransactionStorage.readTransactions(startDate, endDate);
        ArrayList<Transaction> transactionsList = new ArrayList<Transaction>();
        System.out.println("Filter Query: " + filterQuery);
        // TODO - Filter by text here
        // Retrieve all Items from the database and store them in an ArrayList variable.
        // Logic for filtering items based on user input
        for (Transaction transaction : storageList) {
            // If the filterQuery is null or an empty string set itemList to all items and break loop to save compute time
            if (filterQuery == null || filterQuery.isEmpty()) {
                transactionsList = storageList;
                break;
            }
            String transactionId = transaction.getId().toLowerCase();
            String transactionDesc = transaction.getDescription().toLowerCase();
            String transactionType = transaction.getTransactionType().toString().toLowerCase();

            boolean idMatch = transactionId.contains(filterQuery);
            boolean nameMatch = transactionDesc.contains(filterQuery);
            boolean typeMatch = transactionType.contains(filterQuery);


            if (idMatch || nameMatch || typeMatch) {
                transactionsList.add(transaction);
            }
        }

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
        transactionTable.getColumns().setAll(Arrays.asList(idColumn, descriptionColumn, quantityColumn, totalValueColumn, quantityRemainingColumn, transactionTypeColumn, timestampColumn));
        // Ensure that the table is sorted in order of timestamp.
        transactionTable.getSortOrder().add(timestampColumn);
        transactionTable.sort();
        // Resize to stretch to window size.
        transactionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
    }

}
