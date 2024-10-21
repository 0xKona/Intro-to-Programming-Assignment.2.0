package controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import main.View;
import models.Item;
import storage.ItemStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Holds methods responsible for the logic of adding, updating and deleting items.
 * <p>Methods:</p>
 */
public class ItemController {

    @FXML
    public void navigateHome() throws IOException {
        View.navigateToMainMenu();
    }

    /* BELOW CODE IS FOR ADDING A NEW ITEM */
    private static Item newItem;

    public static void initializeNewItem() {
        newItem = new Item();
    }

    // Update newitem name
    @FXML
    private TextField itemNameField;
    @FXML
    public void updateNewItemName() throws IOException {
        String newItemName = itemNameField.getText();
        newItem.setName(newItemName);
    }

    @FXML
    private TextField itemAmountField;
    @FXML
    public void updateNewItemAmount() throws IOException {
        String newItemAmount = itemAmountField.getText();
        newItem.setQtyInStock(Double.parseDouble(newItemAmount));
    }

    @FXML
    private TextField itemPriceField;
    @FXML
    public void updateNewItemPrice() throws IOException {
        String newItemPrice = itemPriceField.getText();
        newItem.setUnitPrice(Double.parseDouble(newItemPrice));
    }

    @FXML
    public void submitNewItem() throws IOException {
        newItem.submitNewItem();
        navigateHome();
    }

    /* BELOW CODE IS FOR EDITING ITEM QUANTITY */
    @FXML
    public TableView<Item> itemTable;

    public void initialize() {
        /* Conditionally initialize itemTable, this is necessary due to the fact that my two fxml views are using this
        same controller, which means that when add-item-view.fxml is loaded it tries to initialize the TableView used in
        edit-items-view which causes a null exception, to fix this I simply need to do a not null check here first however
        a more robust solution would be to seperate this ItemController class into two seperate controllers for each fxml
        file. */
        if (itemTable != null) {
            // Set TableView to be editable, this will allow the user to edit the field directly later.
            itemTable.setEditable(Boolean.TRUE);

            // Retrieve all Items from the database and store them in an ArrayList variable.
            ArrayList<Item> itemList = ItemStorage.readItems();
            /* Convert it to an ObservableList that can be used by the TableView, This allows for changes to Objects in the
            list to be reflected in the table view */
            ObservableList<Item> observableItems = FXCollections.observableArrayList(itemList);

            /* Generate the ID Column and set the cell factory to be of a type Integer and map it to the id element in an
             Item object */
            TableColumn<Item, Integer> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("id"));

            /* Generate the Description column and set the cell factory to be of a type String and map it to the description
            element in an Item Object */
            TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));

            /* Generate the Unit Price column and set the cell factory to be of a type Double and map it to the unitPrice
            element in an Item Object */
            TableColumn<Item, Double> unitPriceColumn = new TableColumn<>("Unit Price");
            unitPriceColumn.setCellValueFactory(new PropertyValueFactory<Item, Double>("unitPrice"));

            /* Generate the Quantity column and set the cell factory to be of a type Double and map it to the qtyInStock
            element in an Item Object */
            TableColumn<Item, Double> unitQuantityColumn = new TableColumn<>("Quantity (Double click to Edit)");
            unitQuantityColumn.setCellValueFactory(new PropertyValueFactory<Item, Double>("qtyInStock"));
            /* Setting on edit commit allows us to handle the changes made to this cell (When the user presses enter) this
            logic will occur via a lambda function, the argument being the targetCell that was edited */
            unitQuantityColumn.setOnEditCommit(
                    targetCell -> {
                        double oldValue = targetCell.getOldValue(); // Save original value to a variable.

                        /* Get the Item Object from the targetCell and assign to a variable, then update the quantity with
                        the new value */
                        Item newItem = targetCell.getTableView().getItems().get(targetCell.getTablePosition().getRow());
                        newItem.setQtyInStock(targetCell.getNewValue());

                        // Update item in database
    //                    ItemManagerDB.updatedItemQuantity(newItem, oldValue);
                        newItem.submitUpdatedItem();
                    }
            );

            // Update the cell factory to enable editing the text value inside it.
            unitQuantityColumn.setCellFactory(column -> {
                // Converts the Double value too and from a String value that the user can edit.
                TextFieldTableCell<Item, Double> cell = new TextFieldTableCell<>(new StringConverter<>() {
                    @Override
                    public String toString(Double object) {
                        return object != null ? object.toString() : ""; // Convert Double to String for display
                    }

                    @Override
                    public Double fromString(String string) {
                        try {
                            return Double.parseDouble(string);  // Convert String input back to Double
                        } catch (NumberFormatException e) {
                            return null;
                        }
                    }
                });

                /* Add a listener to the cell to monitor changes in its item property, if the row of this column is empty
                then the user shouldn't be able to edit the value as there is no Object in this row to update. */
                cell.itemProperty().addListener((observable, oldValue, newValue) -> {
                    if (cell.getTableRow() == null || cell.getTableRow().getItem() == null) {
                        cell.setEditable(false);  // If no valid row or item, make cell non-editable
                    } else {
                        cell.setEditable(true);
                        if (newValue != null) {
                            cell.setText(newValue.toString());
                        }
                    }
                });
                return cell;
            });

            /* Generate the Total Value column and set the cell factory to be of a type Double and map it to the totalValue
            element in an Item Object */
            TableColumn<Item, Double> totalValueColumn = new TableColumn<>("Total Value");
            totalValueColumn.setCellValueFactory(new PropertyValueFactory<Item, Double>("totalValue"));

            // Generates a column at the end with a delete button inside of it.
            TableColumn<Item, Item> deleteItemColumn = new TableColumn<>(""); // No header text for this column needed.
            deleteItemColumn.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue())
            );
            deleteItemColumn.setCellFactory(param -> new TableCell<>() {
                private final Button deleteButton = new Button("Delete Item"); // Generate the delete button

                // On delete update the cell to empty itself as the Object for this row will no longer exist.
                @Override
                protected void updateItem(Item item, boolean empty) {
                    super.updateItem(item, empty);

                    // If not item on this row, no button should be displayed.
                    if (item == null) {
                        setGraphic(null);
                        return;
                    }
                    // Else we set the graphic to display a button, on press it removes it from the table view and the DB.
                    setGraphic(deleteButton);
                    deleteButton.setOnAction(event -> {
                        getTableView().getItems().remove(item);
    //                    ItemManagerDB.deleteItem(item);
                        item.submitDeleteItem();
                    });
                }
            });

            // Add items to the table view
            itemTable.setItems(observableItems);
            // Format the items into the columns and add them to the TableView
            itemTable.getColumns().addAll(Arrays.asList(idColumn, nameColumn, unitPriceColumn, unitQuantityColumn, totalValueColumn, deleteItemColumn));
            // Evenly spread and space columns to fit the screen size
            itemTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        }
    }
}
