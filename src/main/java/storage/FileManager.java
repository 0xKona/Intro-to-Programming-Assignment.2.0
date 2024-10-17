package storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles initializing the storage files and checking if they exist.
 * <p>Methods:</p>
 * <li>{@link #ITEMS_FILE_PATH}</li>
 * <li>{@link #TRANSACTIONS_FILE_PATH}</li>
 * <li>{@link #initializeStorage()}</li>
 */
public class FileManager {

    /* ***IMPORTANT NOTE***
        Reasoning for root file path when generating files:

        Originally I kept the text files in a package called 'files' with the following path:
            "src/files/items.txt"

        This however caused Issues when building to a JAR file as the program could not modify the text files inside itself,
        the solution being to generate them in the same path as the JAR file if they don't exist, hence the creation of
        the FileManager class.

        Having the paths defined in  single location and using the variables elsewhere allows me to change it easily in
        the future if needed.
    */

    // These two variables are in uppercase as they will not be changed and are constants
    public static final String ITEMS_FILE_PATH = "items.txt";
    public static final String TRANSACTIONS_FILE_PATH = "transactions.txt";

    // Checks if the items file exists, if not it tries to create the file with the headers
    private static void checkItemsFile() {
        // Initialize a file with the relevant path
        File itemsFile = new File(ITEMS_FILE_PATH);
        if (!itemsFile.exists()) { // Check if file exists at that path
            try (FileWriter writer = new FileWriter(itemsFile)) {
                // If it doesn't exist, Try to write the file with the header line at the top.
                writer.write("id,description,unitPrice,qtyInStock,totalPrice\n");
                System.out.println("Items storage initialized successfully");
            } catch (IOException e) {
                // If it fails, throw an error and exit program
                System.err.println("Error creating items.txt file: " + e.getMessage());
                System.exit(201);
            }
        } else {
            // If file exists continue with program
            System.out.println("Item storage loaded successfully.");
        }
    }

    /* Checks if the transactions file exists, if not it tries to create the file with the headers. following the same
    process as checkItemsFile() */
    private static void checkTransactionsFile() {
        File transactionsFile = new File(TRANSACTIONS_FILE_PATH);
        if (!transactionsFile.exists()) {
            try (FileWriter writer = new FileWriter(transactionsFile)) {
                writer.write("id,description,changeInQty,valueChange(£),stockRemaining,transactionType,timestamp\n");
                System.out.println("Transactions storage initialized successfully.");
            } catch (IOException e) {
                System.err.println("Error creating transactions.txt file: " + e.getMessage());
                System.exit(202);
            }
        } else {
            System.out.println("Transaction storage loaded successfully.");
        }
    }

    /**
     * Calls the private {@link #checkItemsFile()} and {#checkTransactionFile()} methods, returns an error code and ends
     * the program if a failure occurs.
     */
    public static void initializeStorage() {
        // NOTE: I am not wrapping these in a try/catch block as the called methods already handle exceptions internally
        checkItemsFile();
        checkTransactionsFile();
    }


}
