package controller;

import main.View;
import models.Item;
import storage.ItemStorage;
import utils.IDGenerator;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Holds methods responsible for the logic of adding, updating and deleting items.
 * <p>Methods:</p>
 * <li>{@link #addNewItem()}</li>
 * <li>{@link #editItemQuantity()}</li>
 * <li>{@link #deleteItem()}</li>
 */
public class ItemController {

    // Initialize reads items from storage and assigns them to the variable "Items" for usage by the various methods
    private static ArrayList<Item> items;

    // Removed private from this method in order to make it package private and accessible for testing
    static void initializeItemList() {
        try {
            items = ItemStorage.readItems();
        } catch (IOException e) {
            System.out.println("Error reading items from file: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Displays CLI elements and performs logic for adding a new Item. Calling View.
     */
    public static void addNewItem() {
        View.displayAddNewItemHeader(); // Display user interface in CLI
        Item newItem = new Item(); // Create a new object from the Item class
        newItem.setId(IDGenerator.generateNewID()); // Set the ID.

        /* For each value call View.getInput, passing in a prompt and a parser to ensure we get the correct
           type back, the default is a String so in the Item Name one we simply pass a function that returns the input,
           however for the others I need to pass the parseDouble function that parses a String to a double.
         */
        String itemName = View.getInput("Enter Item Name: ", input -> input);
        newItem.setName(itemName);

        double itemPrice = View.getInput("Enter Items Unit Price [£]: ", Double::parseDouble);
        newItem.setUnitPrice(itemPrice);

        double itemQty = View.getInput("Enter Items Quantity [Units]: ", Double::parseDouble);
        newItem.setQtyInStock(itemQty);

        boolean userHasConfirmed = View.promptNewItemConfirmation(newItem); // Prompt user to confirm item

        if (userHasConfirmed) { // Conditional logic depending on user confirmation.
            newItem.submitNewItem(); // Add new item by using method in the Item class.
            System.out.println(newItem.getName() + " has been submitted.");
        } else {
            System.out.println(newItem.getName() + " has not been submitted.");
        }
        StoreController.start(); // Return the user back to the home screen.
    }

    /**
     * Displays CLI elements and performs logic for editing the quantity of an Item.
     */
    public static void editItemQuantity() {
        initializeItemList(); // initialize a list of existing items.
        int selectedItem = View.displayItems(items, "SELECT ITEM TO UPDATE QUANTITY");
        if (selectedItem == -1) { // return to main menu on rejection.
            View.displayMainMenu();
            return;
        }
        try {
            Item itemToUpdate = findItemByID(items, selectedItem); // Get instance of Item class by the ID.
            System.out.printf("\nEditing: %s [ID: %d]", itemToUpdate.getName(), itemToUpdate.getId());
            System.out.println("\nCurrent quantity: " + itemToUpdate.getQtyInStock());

            double newQuantity = View.getInput("Enter New Quantity: ", Double::parseDouble); // Get newQty
            itemToUpdate.setQtyInStock(newQuantity); // set newQty to object
            itemToUpdate.submitUpdatedItem(); // update item in storage using method in Item class.

            StoreController.start(); // Fully Reset Application upon completion
        } catch (IOException error) {
            /* If findItemByID returns an error due to not being able to match an Item with the ID provided
               we try again calling editItemQuantity with a Lambda function (Also known as an anonymous or unnamed function)
               and passing in the items as an argument.
             */
            View.tryInputAgain("Item with that ID does not exist: ", ItemController::editItemQuantity);
        }
    }

    /**
     * Method responsible for the 'delete item' option in the main menu, handles allowing the user to delete an Item and
     * displaying CLI elements for the user to interact with.
     */
    public static void deleteItem() {
        initializeItemList(); // Initialize the ArrayList of Items.
        /* ConsoleView.displayItems returns an item ID, if it returns -1, it means the user selected the option to return
        to the main menu, therefore we will return back to the main menu with a conditional if statement,
        if an actual ID is returned, the program will continue */
        int selectedItem = View.displayItems(items, "SELECT ITEM TO DELETE"); // Prompt user to select an Item.
        if (selectedItem == -1) {
            View.displayMainMenu(); // Return to main menu on rejection
            return;
        }
        /* Try to find the Item with the matching ID from the ArrayList of Items, if no Item is found, the User will be
        prompted again to input a valid ID */
        try {
            Item foundItem = findItemByID(items, selectedItem); // Find item from list by ID
            boolean deletionConfirmed = View.confirmDeletion(); // Ask user for confirmation to delete item
            if (deletionConfirmed) { // If user has confirmed (true), use the items method to submit the deletion
                foundItem.submitDeleteItem();
            }
            StoreController.start(); // reset application.
        } catch (IOException error) {
            View.tryInputAgain("Item with that ID does not exist: ", ItemController::deleteItem);
        }
    }

    /**
     * Finds item from the Array and returns the Item or an IOException if corresponding item is found
     * @param items Item[]
     * @param selectedID int
     * @return Item
     * @throws IOException Selected item does not exist
     */
    private static Item findItemByID(ArrayList<Item> items, int selectedID) throws IOException {
        for (Item item : items) {
            if (item.getId() == selectedID) {
                return item;
            }
        }
        System.out.println("\nItem not found. Please try again");
        throw new IOException("Selected item does not exist");
    }
}
