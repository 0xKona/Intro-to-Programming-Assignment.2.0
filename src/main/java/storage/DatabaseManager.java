package storage;

import java.sql.*;
import java.util.Random;

/**
 * Database Manager contains the method for connecting and initializing the database
 * <p>Methods: </p>
 * <li>{@link #connect()}</li>
 * <li>{@link #initializeDatabase()}</li>
 */
public class DatabaseManager {

    /* Database URL (H2 in file mode, it will create a file called "inventoryDB" in projects root directory)
    Note: Variable is a constant and will not change, therefore it is fully capitalized as per oracles naming
    conventions. */
    private static final String DB_URL = "jdbc:h2:./inventoryDB";

    /* Set the databases username and password, as this is a minimum viable product I will not be setting a password
    however in production this would be essential and managed with something like a .env variable */
    private static final String DB_USER = "mvp";
    private static final String DB_PASSWORD = "";

    /**
     * Connects to the database
     * @return Returns a connection to the H2 Database
     * @throws SQLException SQL Error if connection fails
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * Initialize Database should be run in the main method of the app, this ensures the database exists and the tables
     * are created if they do not already exist. returns true if the connection was successful
     */
    public static boolean initializeDatabase() {
        // String to create Items table and define columns.
        String createItemsTable = "CREATE TABLE IF NOT EXISTS Items (" +
                "id INT PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "unitPrice DOUBLE, " +
                "qtyInStock DOUBLE, " +
                "totalPrice DOUBLE)";

        // String to create transactions table and define columns
        String createTransactionsTable = "CREATE TABLE IF NOT EXISTS Transactions (" +
                "id INT PRIMARY KEY, " +
                "description VARCHAR(255), " +
                "quantityChange DOUBLE, " +
                "valueChange DOUBLE, " +
                "quantityRemaining DOUBLE, " +
                "transactionType VARCHAR(50), " +
                "timestamp TIMESTAMP)";


        try (Connection conn = connect(); Statement statement = conn.createStatement()) {
            // Create Items Table
            statement.execute(createItemsTable);

            // Create Transactions Table
            statement.execute(createTransactionsTable);

            System.out.println("Tables created successfully.");
            return true;
        } catch (SQLException e) {
            System.out.println("Tables failed to be created: " + e.getMessage());
            return false;
        }
    }

    /**
     * Generates a Unique ID by generating a random five-digit ID and then checking if it is already in use by the database
     * @param table String (Should be "Items" or "Transactions")
     * @return int new ID
     * @throws SQLException error in the case that an invalid table is provided.
     */
    public static int generateUniqueID(String table) throws SQLException {
        if (table.equals("Items") || table.equals("Transactions")) { // Check table is valid.
            Random random = new Random(); // Generate random number
            int newID;

            do { // Do-while loop, while condition (checkIfIDExists) is true, increment the ID
                newID = random.nextInt(100000);
            } while (checkIfIDExists(newID, table));

            return newID;

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
            Connection connection = connect();
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
