package controller;

import main.View;
import models.Transaction;
import storage.TransactionStorage;
import utils.TimestampGenerator;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Transaction controller handles the logic for creating new transactions and fetching the list of transactions from
 * storage.
 *
 * <p>Public Methods:</p>
 */
public class TransactionController {

    private static ArrayList<Transaction> transactions;
    /**
     * Initialize the transactions ArrayList
     */
    private static void initializeTransactionList() {
        try {
            transactions = TransactionStorage.readTransactions();
        } catch (FileNotFoundException e) {
            System.out.println("File not found when reading transactions: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Generates the daily transaction report using Today's date by filtering the List of Transactions based on Today's
     * date, it then calls the displayTransactions method of ConsoleView, passing in the filteredList, to display it to
     * the User.
     * <p>
     * This could be expanded upon by allowing the user to go backwards day by day to view previous days transactions.
     * </p>
     */
    public static void generateDailyTransactionReport() {
        initializeTransactionList();
        ArrayList<Transaction> todaysTransactions = filterTransactionsByDate(transactions);
        View.displayTransactions(todaysTransactions);
    }


    /**
     * Filters the transaction list by date
     * @param transactions ArrayList of Transaction objects.
     * @return ArrayList of Transaction objects with a timestamp on today's date, this could be expanded on to take in a
     * date and return items for that date, allowing the user to input a date and show transactions for any date in the past.
     */
    private static ArrayList<Transaction> filterTransactionsByDate(ArrayList<Transaction> transactions) {
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();
        String todaysDateString = TimestampGenerator.getCurrentTime().split(" ")[0]; // GET DATE PART "YYYY-MM-DD"

        for (Transaction transaction : transactions) { // Loop through transactions
            String transactionDate = transaction.getTimestamp().split(" ")[0]; // GET DATE PART "YYYY-MM-DD"

            if (transactionDate.equals(todaysDateString)) { // If transactions occurred today, add to returning list.
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }
}
