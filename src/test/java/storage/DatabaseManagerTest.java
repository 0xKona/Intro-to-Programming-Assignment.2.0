package storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class DatabaseManagerTest {

    private static final String DB_URL = "jdbc:h2:" + System.getProperty("user.home") + "/inventoryDB";
    private static final String DB_USER = "mvp";
    private static final String DB_PASSWORD = "";

    private Connection mockConnection;
    private Statement mockStatement;
    private MockedStatic<DriverManager> driverManagerMockedStatic;

    @BeforeEach
    void setUp() throws SQLException {
        Mockito.clearAllCaches(); // Clear mocks before running to avoid conflicts between tests

        mockConnection = mock(Connection.class);
        mockStatement = mock(Statement.class);

        // Start the mocked static DriverManager for each test
        driverManagerMockedStatic = Mockito.mockStatic(DriverManager.class);

        // Configure mockConnection to return mockStatement when createStatement() is called
        when(mockConnection.createStatement()).thenReturn(mockStatement);

        // Mock DriverManager to return mockConnection
        driverManagerMockedStatic.when(() -> DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD))
                .thenReturn(mockConnection);
    }

    @AfterEach
    void tearDown() {
        driverManagerMockedStatic.close(); // Close the static mock to avoid residual effects between tests
    }

    @Test
    @DisplayName("connect() should call DriverManager with correct arguments")
    void testConnect() throws SQLException {
        // Call the connect method
        Connection connection = DatabaseManager.connect();

        // Verify that DriverManager.getConnection was called with correct arguments
        driverManagerMockedStatic.verify(() -> DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD), times(1));
        assertTrue(connection != null, "Connection should not be null");
    }

    @Test
    @DisplayName("initializeDatabase() should execute SQL commands to create tables")
    void testInitializeDatabase() throws SQLException {
        // Mock statement execution to simulate successful table creation
        when(mockStatement.execute(anyString())).thenReturn(true);

        // Call the initializeDatabase method
        boolean result = DatabaseManager.initializeDatabase();

        // Verify that the statement.execute was called with the SQL to create the "Items" table
        verify(mockStatement, times(1)).execute("CREATE TABLE IF NOT EXISTS Items (" +
                "id INT PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "unitPrice DOUBLE, " +
                "qtyInStock DOUBLE, " +
                "totalPrice DOUBLE)");

        // Verify that the statement.execute was called with the SQL to create the "Transactions" table
        verify(mockStatement, times(1)).execute("CREATE TABLE IF NOT EXISTS Transactions (" +
                "id INT PRIMARY KEY, " +
                "description VARCHAR(255), " +
                "quantityChange DOUBLE, " +
                "valueChange DOUBLE, " +
                "quantityRemaining DOUBLE, " +
                "transactionType VARCHAR(50), " +
                "timestamp TIMESTAMP)");

        // Assert that initializeDatabase returned true
        assertTrue(result, "initializeDatabase should return true when tables are created successfully");
    }
}
