package storage;

import models.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Handles logic for reading and writing to the file storage.
 * <p>Methods:</p>
 * <li>{@link #addNewItem(Item)}</li>
 * <li>{@link #readItems()}</li>
 * <li>{@link #updateItem(Item)}</li>
 * <li>{@link #deleteItem(Item)}</li>
 */
public class ItemStorage {

    /**
     * Inserts a new item into the database
     * @param item Item object.
     */
    public static void addNewItem(Item item) {
        // The SQL statement provided to the database, ? is an argument I can insert into it later
        String sqlStatement = "INSERT INTO Items (id, name, unitPrice, qtyInStock, totalPrice) VALUES (?, ?, ?, ?, ?)";
        try {
            int newID = DatabaseManager.generateUniqueID("Items"); // Generate a new ID
            Connection connection = DatabaseManager.connect(); // Connect the database
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement); // Prepare statement
            // Set arguments on my statement
            preparedStatement.setInt(1, newID);
            preparedStatement.setString(2, item.getName());
            preparedStatement.setDouble(3, item.getUnitPrice());
            preparedStatement.setDouble(4, item.getQtyInStock());
            preparedStatement.setDouble(5, item.getTotalValue());
            // Execute the statement.
            preparedStatement.executeUpdate();

        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * Updates an Items Quantity in the database
     * @param item instance of the Item class
     */
    public static void updateItem(Item item) {
        String sqlStatement = "UPDATE Items SET qtyInStock = ? WHERE id = ?"; // SQL Statement

        try {
            Connection connection = DatabaseManager.connect(); // Connect to DB
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement); // Initialize the statement
            // Add arguments to statement
            preparedStatement.setDouble(1, item.getQtyInStock());
            preparedStatement.setString(2, item.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * Deletes and Item from the database using its ID
     * @param item instance of the Item class
     */
    public static void deleteItem(Item item) {
        String sqlStatement = "DELETE FROM Items WHERE id = ?"; // SQL Statement

        try {
            Connection connection = DatabaseManager.connect(); // Connect to database
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement); // Initialize statement
            // Add arguments to statement
            preparedStatement.setString(1, item.getId());
            preparedStatement.execute();
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * Reads Items from storage, formats them and returns them in an ArrayList.
     * @return ArrayList
     */
    public static ArrayList<Item> readItems() {
        ArrayList<Item> items = new ArrayList<>(); // Initialize a new ArrayList of Item Objects;
        String sqlStatement = "SELECT * FROM Items"; // SQL Statement

        try {
            Connection connection = DatabaseManager.connect(); // Connect to database
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement); // Initialize statement.
            /* Immediately execute SQL query as we don't need to pass any arguments in this time and save it to a
            ResultSet variable */
            ResultSet resultSet = preparedStatement.executeQuery();

            // Iterate over result set and create item objects from entries
            while (resultSet.next()) {
                Item item = new Item(); // Initialize new Item object
                item.setId(DatabaseManager.formatID(resultSet.getInt("id")));
                item.setName(resultSet.getString("name"));
                item.setUnitPrice(resultSet.getDouble("unitPrice"));
                item.setQtyInStock(resultSet.getDouble("qtyInStock"));
                // Total Value is auto calculated and set in the class

                items.add(item); // Add item to ArrayList
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items; // Return the ArrayList of Items.
    }
}
