package storage;

import models.Transaction;
import models.TransactionType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import utils.IDGenerator;
import utils.TableType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static org.mockito.Mockito.*;

class TransactionStorageTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private MockedStatic<DatabaseManager> dbManagerMockedStatic;
    private MockedStatic<IDGenerator> idGeneratorMockedStatic;

    @BeforeEach
    void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);

        // Mock DatabaseManager static methods
        dbManagerMockedStatic = Mockito.mockStatic(DatabaseManager.class);
        dbManagerMockedStatic.when(DatabaseManager::connect).thenReturn(mockConnection);

        // Mock IDGenerator static method
        idGeneratorMockedStatic = Mockito.mockStatic(IDGenerator.class);

        // Configure mockConnection to return mockPreparedStatement
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }

    @Test
    @DisplayName("writeNewTransaction should insert a new transaction into the database")
    void testWriteNewTransaction() throws SQLException {
        // Mock ID generation
        String generatedID = "TRANS001";
        idGeneratorMockedStatic.when(() -> IDGenerator.generateNewID(TableType.Transactions)).thenReturn(generatedID);

        Transaction mockTransaction = new Transaction();
        mockTransaction.setDescription("Sample Transaction");
        mockTransaction.setQuantityChange(10);
        mockTransaction.setQuantityRemaining(50);
        mockTransaction.setValueChange(100.0);
        mockTransaction.setTransactionType(TransactionType.ADDED);
        mockTransaction.setTimestamp("2023-10-10 10:10:10");

        // Call writeNewTransaction method
        TransactionStorage.writeNewTransaction(mockTransaction);

        // Verify PreparedStatement setup
        verify(mockPreparedStatement).setString(1, generatedID);
        verify(mockPreparedStatement).setString(2, "Sample Transaction");
        verify(mockPreparedStatement).setDouble(3, 10.0);
        verify(mockPreparedStatement).setDouble(4, 50.0);
        verify(mockPreparedStatement).setDouble(5, 100.0);
        verify(mockPreparedStatement).setString(6, "ADDED");
        verify(mockPreparedStatement).setString(7, "2023-10-10 10:10:10");
        verify(mockPreparedStatement).executeUpdate();
    }

    @AfterEach
    void tearDown() {
        dbManagerMockedStatic.close();
        idGeneratorMockedStatic.close();
    }
}
