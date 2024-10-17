package controller;

import main.View;
import models.Item;
import storage.ItemStorage;
import utils.IDGenerator;

import java.io.IOException;
import java.util.ArrayList;

public class ItemController {

    private static ArrayList<Item> items;

    /**
     * Reads items from storage and assigns them to a private variable 'items'
     */
    private static void initializeItemList() {
        try {
            items = ItemStorage.readItems();
        } catch (IOException e) {
            System.out.println("Error reading items from file: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Displays CLI elements and performs logic for adding a new Item. Calling ConsoleView and ItemStorage.
     */
    public static void addNewItem() {
        View.displayAddNewItemHeader();

        // Create a new object from the Item class
        Item newItem = new Item();
        newItem.setId(IDGenerator.generateNewID());

        /* For each value we call ConsoleView.getInput, passing in a prompt and a parse to ensure we get the correct
           type back, the default is a String so in the Item Name one we simply pass a function that returns the input,
           however for the others I need to pass the parseDouble function that parses a String to a double.
         */
        String itemName = View.getInput("Enter Item Name: ", input -> input);
        newItem.setName(itemName);

        double itemPrice = View.getInput("Enter Items Unit Price [£]: ", Double::parseDouble);
        newItem.setUnitPrice(itemPrice);

        double itemQty = View.getInput("Enter Items Quantity [Units]: ", Double::parseDouble);
        newItem.setQtyInStock(itemQty);

        // Prompt user to confirm item
        boolean userHasConfirmed = View.promptNewItemConfirmation(newItem);

        if (userHasConfirmed) {
            newItem.submitNewItem();
            System.out.println(newItem.getName() + " has been submitted.");
        } else {
            System.out.println(newItem.getName() + " has not been submitted.");
        }

        StoreController.start(); // Return the user back to the home screen.
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

    /**
     * Method responsible for the 'delete item' option in the main menu, handles allowing the user to delete an Item and
     * displays CLI elements for the user to interact with.
     */
    public static void deleteItem() {
        initializeItemList();
        /* ConsoleView.displayItems returns an item ID, if it returns -1, it means the user selected the option to return
        to the main menu, therefore we will return back to the main menu with a conditional if statement,
        if an actual ID is returned, the program will continue */
        int selectedItem = View.displayItems(items, "SELECT ITEM TO DELETE");
        if (selectedItem == -1) {
            View.displayMainMenu();
            return;
        }
        System.out.println("Selected Item ID: " + selectedItem);

        /* Try to find the Item with the matching ID from the ArrayList of Items, if no Item is found, the User will be
        prompted again to input a valid ID */
        try {
            Item foundItem = findItemByID(items, selectedItem);
            View.confirmDeletion(foundItem);
            StoreController.start();
        } catch (IOException error) {
            View.tryInputAgain("Item with that ID does not exist: ", ItemController::deleteItem);
        }
    }

    /**
     * Displays CLI elements and performs logic for editing the quantity of an Item. Calls ConsoleView and ItemStorage.
     */
    public static void editItemQuantity() {
        initializeItemList(); // initialize a list of existing items.
        int selectedItem = View.displayItems(items, "SELECT ITEM TO UPDATE QUANTITY");
        if (selectedItem == -1) { // catch error and return to main menu.
            View.displayMainMenu();
            return;
        }
        System.out.println("Selected Item ID: " + selectedItem);
        try {
            Item itemToUpdate = findItemByID(items, selectedItem); // Get instance of Item class by the ID.

            // Set new quantity and write update to file
            double newQuantity = View.getInput("Enter New Quantity: ", Double::parseDouble);
            itemToUpdate.setQtyInStock(newQuantity);
            System.out.println("New Quantity: " + itemToUpdate.getQtyInStock());
            System.out.println("Old Quantity: " + itemToUpdate.getPreviousQuantityInStock());
            itemToUpdate.submitUpdatedItem();

            StoreController.start(); // Fully Reset Application upon completion
        } catch (IOException error) {
            /* If findItemByID returns an error due to not being able to match an Item with the ID provided
               we try again calling editItemQuantity with a Lambda function (Also known as an anonymous or unnamed function)
               and passing in the items as an argument.
             */
            View.tryInputAgain("Item with that ID does not exist: ", ItemController::editItemQuantity);
        }
    }
}
