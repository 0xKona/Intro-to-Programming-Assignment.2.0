package main;

import controller.StoreController;
import models.Item;
import models.Transaction;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;

public class View {

    static String mediumLineBreak = "----------------------------------------------------------";
    static String longLineBreak =   "--------------------------------------------------------------------------";
    /**
     * Displays the main menu of the Inventory Management System in the console.
     *
     * <p>The main menu includes options for adding a new item, updating the quantity of an existing item,
     * removing an item, viewing the daily transaction report, and exiting the system.
     * After displaying the options, the method calls {@link #handleMainMenuChoice()} to process the user's input.</p>
     */
    public static void displayMainMenu() {
        System.out.println("\n I N V E N T O R Y    M A N A G E M E N T    S Y S T E M");
        System.out.println(mediumLineBreak);
        System.out.println("1. ADD NEW ITEM");
        System.out.println("2. UPDATE QUANTITY OF EXISTING ITEM");
        System.out.println("3. REMOVE ITEM");
        System.out.println("4. VIEW DAILY TRANSACTION REPORT");
        System.out.println(mediumLineBreak);
        System.out.println("5. Exit");

        handleMainMenuChoice();
    }

    /**
     * Prompts the user to enter a main menu option [1-5] then uses the mainMenuRouting to activate the relevant
     * program. This is seperated from the main menu so that we can just call this prompt rather than generating the
     * entire menu again in certain situations such as when the router defaults as the inputted number is invalid.
     */
    public static void handleMainMenuChoice() {
        int capturedInput = getInput("Enter a number [1-5] and Press ENTER to continue: ", Integer::parseInt);
        StoreController.mainMenuRouting(capturedInput);
    }

    /**
     * Display header for adding a new item program
     */
    public static void displayAddNewItemHeader() {
        System.out.println("\n A D D   N E W   I T E M");
        System.out.println(mediumLineBreak);
    }

    /**
     * Prompts user to confirm details of the new Item by displaying each property, could expand on this to allow the
     * user to edit the item before confirming
     * @param item Instance of Item class
     * @return boolean (true = user confirmed item)
     */
    public static boolean promptNewItemConfirmation(Item item) {
        System.out.println("\n---- Please confirm you wish to add this item ---- ");
        System.out.println("\nItem ID: " + item.getId());
        System.out.println("Item Name: " + item.getName());
        System.out.println("Item Unit Price: £" + item.getUnitPrice());
        System.out.println("Item Quantity: " + item.getQtyInStock());
        System.out.println("Total Value: £" + item.getTotalValue());
        System.out.print("\nPress [Y] and [ENTER] to confirm item: ");

        Scanner confirmationScanner = new Scanner(System.in);
        String confirmation = confirmationScanner.nextLine().trim().toUpperCase();
        return Objects.equals(confirmation, "Y");
    }

    /**
     * Displays a list of Items and prompts the user to enter an Item ID to continue
     * @param items Item[]
     * @return An Item ID (int). || -1 (int) if User presses 0 to return to main screen.
     */
    public static int displayItems(ArrayList<Item> items, String headerTitle) {
        // Define format strings with fixed widths ensuring the table is aligned.
        String headerFormat = "%-8s %-15s %12s %10s %12s";
        String itemFormat = "%-8d %-15s %12f %10f %12f";

        // Print the header
        System.out.println("\n------- " + headerTitle + " ------");
        System.out.printf((headerFormat) + "%n", "ID", "Name", "Unit Price", "Quantity", "Total Price");
        System.out.println(longLineBreak);

        // Print each item using the itemFormat
        for (Item item : items) {
            System.out.printf((itemFormat) + "%n",
                    item.getId(),
                    item.getName(),
                    item.getUnitPrice(),
                    item.getQtyInStock(),
                    item.getTotalValue());
        }

        System.out.println(longLineBreak);
        System.out.println("0 : Exit and Return to main menu");
        System.out.print("\nTo select an item, type an ID and press ENTER: ");
        Scanner itemScanner = new Scanner(System.in);
        int itemID = itemScanner.nextInt();
        if (itemID == 0) {
            return -1;
        } else {
            return itemID;
        }
    }


    @FunctionalInterface
    public interface UserAction {
        void execute();
    }

    /**
     * tryInputAgain should be used when input validation fails, it allows the user to try again from where
     * the error occurred rather than starting again from the beginning of the program
     * @param message String, A message to display before asking the user if they wish to try again
     * @param action Method, A method to execute if the user types 'Y'. Example: () -> YourMethodName(args)
     */
    public static void tryInputAgain(String message, UserAction action) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message + "Do you want to try again? [Y/N]: ");
        /* I use .trim() to remove any leading and trailing whitespace and .toUpperCase() to ensure that it works if the
        user inputs a lowercase value of the correct character */
        String input = scanner.nextLine().trim().toUpperCase();

        if (input.equals("Y")) {
            action.execute();
        } else if (input.equals("N")) {
            displayMainMenu();
        } else {
            System.out.println("Invalid input. Please enter 'Y' or 'N'");
            tryInputAgain(message, action);
        }
    }

    /**
     * Prompts the user with the specified message, reads input from the console, and parses it into a value of type {@code T}
     * using the provided parser function. If the parsing is successful, the parsed value is returned.
     * <p>
     * If the input is invalid (i.e., the parser throws an exception), the method displays an error message and recursively
     * calls itself to prompt the user again. This process repeats until a valid input is provided.
     * <p>
     *
     * @param <T>    the type of the input to be returned after parsing
     * @param prompt the message displayed to the user when requesting input
     * @param parser a function that converts a {@code String} input into the desired type {@code T}
     * @return the parsed value of type {@code T} entered by the user
     */
    public static <T> T getInput(String prompt, Function<String, T> parser) {
        // TODO - Add an exit mechanism here
        try {
            System.out.print("\n" + prompt);
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            // Parse the input into the required Type (double / int / String etc) and return it.
            return parser.apply(input);
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
            // Recursively call getInput again when input is invalid
            return getInput(prompt, parser);
        }
    }

    /**
     * Prompts the user to confirm the permanent deletion of an item from storage.
     *
     * <p>The method displays a warning that the deletion cannot be reversed and asks for confirmation
     * from the user. If the user confirms by entering 'Y', the item is deleted from storage. If the user enters 'N',
     * they are returned to the main menu. If an invalid input is provided, the method recursively prompts the user again
     * until a valid input is entered.</p>
     */
    public static boolean confirmDeletion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----    ****IMPORTANT****    ----");
        System.out.println("THIS WILL DELETE THE ITEM PERMANENTLY AND CANNOT BE REVERSED");
        System.out.print("\n DO YOU WANT TO DELETE THIS ITEM? [Y/N]: ");

        String input = scanner.nextLine().trim().toUpperCase();

        switch (input) {
            case "Y": return true; // No need for a 'break' as return automatically does this
            case "N": return false;
            default: {
                System.out.println("Invalid input. Please enter 'Y' or 'N'");
                confirmDeletion();
            }
        }
        /* Should never get to this point, however java requires a final boolean return as the default in
        the switch calls itself again */
        return false;
    }

    /**
     * Displays a list of transactions in a formatted table in the console.
     *
     * <p>The method outputs the details of each transaction, including ID, description, quantity change,
     * value change, stock remaining, transaction type, and timestamp. A header with column labels is displayed first,
     * followed by the formatted transactions. After displaying all transactions, the user is prompted to exit
     * to the main menu.</p>
     *
     * @param transactions The list of {@link Transaction} objects to be displayed.
     */
    public static void displayTransactions(ArrayList<Transaction> transactions) {
        String headerFormat =          "%-8s %-30s %-20s %-20s %-20s %-20s %-12s";
        String transactionFormat =     "%-8d %-30s %-20f %-20f %-20f %-20s %-12s";

        String header = String.format((headerFormat) + "%n",
                "ID",
                "Description",
                "Quantity Change",
                "Change in Value",
                "Stock Remaining",
                "Transaction Type",
                "Time");
        String seperatorLine = header.replaceAll(".", "-");

        // Print the header
        System.out.print("\n------- " + "TODAY'S TRANSACTIONS" + " ------");
        System.out.print("\n" + header);
        System.out.println(seperatorLine);

        // Print each item using the itemFormat
        for (Transaction transaction : transactions) {
            System.out.printf((transactionFormat) + "%n",
                    transaction.getId(),
                    transaction.getDescription(),
                    transaction.getQuantityChange(),
                    transaction.getValueChange(),
                    transaction.getQuantityRemaining(),
                    transaction.getTransactionType(),
                    transaction.getTimestamp());
        }
        System.out.println(seperatorLine);
        exitToMainMenu();
    }

    /**
     * Prompts the user to press 0 to exit the current view and return to the main menu.
     * If the user provides an invalid input (anything other than 0), the method calls itself recursively
     * until a valid input is provided.
     *
     * <p>This method ensures that the user explicitly chooses to return to the main menu, preventing any accidental exits
     * from the current view.</p>
     */
    private static void exitToMainMenu() {
        System.out.print("\n0 : Exit and Return to main menu: ");

        Scanner exitScanner = new Scanner(System.in);
        int exitValue = exitScanner.nextInt();
        if (exitValue == 0) {
            displayMainMenu();
        } else {
            exitToMainMenu();
        }
    }
}
