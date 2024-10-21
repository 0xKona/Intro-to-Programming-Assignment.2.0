package storage;

import models.Transaction;
import models.TransactionType;
import utils.TimestampGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
     * Takes in a Transaction Object and inserts it into the database.
     * @param transaction Transaction Object
     * @throws SQLException Error message if fails to add to database
     */
    public static void writeNewTransaction(Transaction transaction) {
        // SQL Statement; '?' is an argument that can be passed in later
        String sqlStatement = "INSERT INTO Transactions (id, description, quantityChange, quantityRemaining, valueChange, transactionType, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            int newID = DatabaseManager.generateUniqueID("Transactions"); // Generate a unique ID for the relevant table.
            Connection connection = DatabaseManager.connect(); // Connect to Database
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement); // Initialize the statement
            // Pass arguments to SQL statement.
            preparedStatement.setInt(1, newID);
            preparedStatement.setString(2, transaction.getDescription());
            preparedStatement.setDouble(3, transaction.getQuantityChange());
            preparedStatement.setDouble(4, transaction.getQuantityRemaining());
            preparedStatement.setDouble(5, transaction.getValueChange());
            preparedStatement.setString(6, transaction.getTransactionType().toString());
            preparedStatement.setString(7, transaction.getTimestamp());
            // Execute the SQL query.
            preparedStatement.executeUpdate();
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * Get all transactions between a start and end time String in the format "yyyy-MM-dd HH:mm:ss"
     * @return ArrayList of Transaction Objects.
     */
    public static ArrayList<Transaction> readTransactions(String startDate, String endDate) {
        ArrayList<Transaction> transactions = new ArrayList<>(); // Initialize ArrayList of Objects

        String sqlQuery = "SELECT * FROM Transactions WHERE timestamp >= ? AND timestamp <= ?"; // SQL Query

        try {
            Connection connection = DatabaseManager.connect(); // Connect to database
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery); // Initialize the query.
            // Pass arguments to initialized query.
            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);
            // Save results to a ResultSet variable
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) { // Loop through resultSet and create Transaction Objects and add them to ArrayList
                Transaction transaction = new Transaction();
                transaction.setId(DatabaseManager.formatID(resultSet.getInt("id")));
                transaction.setDescription(resultSet.getString("description"));
                transaction.setQuantityChange(resultSet.getInt("quantityChange"));
                transaction.setQuantityRemaining(resultSet.getInt("quantityRemaining"));
                transaction.setValueChange(resultSet.getInt("valueChange"));
                transaction.setTransactionType(TransactionType.valueOf(resultSet.getString("transactionType")));
                transaction.setTimestamp(resultSet.getString("timestamp"));
                transactions.add(transaction);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    };
}