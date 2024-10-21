package utils;

import storage.DatabaseManager;
//import storage.FileManager;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

/**
 * IDGenerator class to generate unique 5-digit IDs
 * <p>Public Method: </p>
 * <li>{@link #generateNewID(TableType)}</li>
 */
public class IDGenerator {

    /**
     * Generates a Unique ID by generating a random five-digit ID and then checking if it is already in use by the database
     * @param table String (Should be "Items" or "Transactions")
     * @return String new ID
     * @throws SQLException error in the case that an invalid table is provided.
     */
    public static String generateNewID(TableType table) throws SQLException {
        if (table.equals(TableType.Items) || table.equals(TableType.Transactions)) { // Check table is valid.
            Random random = new Random(); // Generate random number
            int newID;

            do { // Do-while loop, while condition (checkIfIDExists) is true, increment the ID
                newID = random.nextInt(100000);
            } while (checkIfIDExists(newID, String.valueOf(table)));

            return formatID(newID);

        } else {
            throw new SQLException("Error generating unique ID: Table " + table + " does not exist.");
        }
    }

    /**
     * Helper function, checks if the generating ID exists in the table provided.
     * @param id int
     * @param table String ("Items" || "Transactions")
     * @return boolean (True if ID is already in use)
     */
    private static boolean checkIfIDExists(int id, String table) {
        // SQL Query string, selects every item that matches the ID.
        String sqlQuery = "SELECT * FROM " + table +" WHERE id = " + id;
        try {
            Connection connection = DatabaseManager.connect();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Converts int ID to a String and adds leading Zeroes if necessary.
     * @param id int
     * @return String ID
     */
    public static String formatID (int id) {
        StringBuilder idAsString = new StringBuilder(Integer.toString(id));
        while (idAsString.length() < 5) {
            idAsString.insert(0, "0");
        }
        return idAsString.toString();
    }
}
