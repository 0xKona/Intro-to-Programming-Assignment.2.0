package storage;

import models.Transaction;
import models.TransactionType;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Handles the text file storage for Transactions
 * <p>Public Methods:</p>
 * <ul>
 *   <li>{@link #writeNewTransaction(Transaction)}</li>
 *   <li>{@link #readTransactions()}</li>
 * </ul>
 */
public class TransactionStorage {

    /**
     * Writes the new Transaction into storage
     */
    public static void writeNewTransaction(Transaction transaction) {
        String transactionAsString = String.format("\n%s,%s,%s,%s,%s,%s,%s", // Format Transaction into a writeable string
                transaction.getId(),
                transaction.getDescription(),
                transaction.getQuantityChange(),
                transaction.getValueChange(),
                transaction.getQuantityRemaining(),
                transaction.getTransactionType(),
                transaction.getTimestamp()
        );

        /* Setting the second argument in FileWriter to true means that append is set to true, which means lines will be
            written to the END of the file, and won't overwrite anything.
         */
        try (Writer output = new FileWriter(FileManager.TRANSACTIONS_FILE_PATH, true)) {
            output.append(transactionAsString);
        } catch (IOException e) {
            System.out.println("An error occurred while trying to write the new line to the file: " + e);
        }
    }

    /**
     * Reads transactions from storage and returns them in an ArrayList.
     * @return ArrayList of Transactions
     */
    public static ArrayList<Transaction> readTransactions() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(FileManager.TRANSACTIONS_FILE_PATH)); // Buffered Reader reads file line by line
        Stream<String> lines = reader.lines().skip(1); // Skip the first header line.
        ArrayList<Transaction> transactions = new ArrayList<>(); // Initialize ArrayList of Transactions
        lines.forEach(currentLine -> {
            if (currentLine.isEmpty()) { // If current line is empty return and continue to next line
                return;
            } else {
                String[] splitLine = currentLine.split(",");
                Transaction transaction = new Transaction(); // Initialize new transaction object.
                transaction.setId(Integer.parseInt(splitLine[0]));
                transaction.setDescription(splitLine[1]);
                transaction.setQuantityChange(Double.parseDouble(splitLine[2]));
                transaction.setValueChange(Double.parseDouble(splitLine[3]));
                transaction.setQuantityRemaining(Double.parseDouble(splitLine[4]));
                transaction.setTransactionType(TransactionType.valueOf(splitLine[5]));
                transaction.setTimestamp(splitLine[6]);
                transactions.add(transaction); // after setting elements, add object to ArrayList.
            }
        });
        return transactions;
    }
}