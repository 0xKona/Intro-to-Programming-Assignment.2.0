package storage;

import models.Item;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ItemStorageTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private MockedStatic<DatabaseManager> dbManagerMockedStatic;
    private MockedStatic<IDGenerator> idGeneratorMockedStatic;

    @BeforeEach
    void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Mock DatabaseManager static methods
        dbManagerMockedStatic = Mockito.mockStatic(DatabaseManager.class);
        dbManagerMockedStatic.when(DatabaseManager::connect).thenReturn(mockConnection);

        // Mock IDGenerator static method
        idGeneratorMockedStatic = Mockito.mockStatic(IDGenerator.class);

        // Configure mockConnection to return mockPreparedStatement
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }

    @Test
    @DisplayName("addNewItem should insert a new item into the database")
    void testAddNewItem() throws SQLException {
        // Mock ID generation
        String generatedID = "ITEM001";
        idGeneratorMockedStatic.when(() -> IDGenerator.generateNewID(TableType.Items)).thenReturn(generatedID);

        Item mockItem = new Item();
        mockItem.setName("Sample Item");
        mockItem.setUnitPrice(10.0);
        mockItem.setQtyInStock(5.0);

        // Call addNewItem method
        ItemStorage.addNewItem(mockItem);

        // Verify PreparedStatement setup
        verify(mockPreparedStatement).setString(1, generatedID);
        verify(mockPreparedStatement).setString(2, "Sample Item");
        verify(mockPreparedStatement).setDouble(3, 10.0);
        verify(mockPreparedStatement).setDouble(4, 5.0);
        verify(mockPreparedStatement).setDouble(5, 50.0);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    @DisplayName("updateItem should update the item's quantity in the database")
    void testUpdateItem() throws SQLException {
        Item mockItem = new Item();
        mockItem.setId("ITEM001");
        mockItem.setQtyInStock(10.0);

        // Call updateItem method
        ItemStorage.updateItem(mockItem);

        // Verify PreparedStatement setup
        verify(mockPreparedStatement).setDouble(1, 10.0);
        verify(mockPreparedStatement).setString(2, "ITEM001");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    @DisplayName("deleteItem should delete the item from the database")
    void testDeleteItem() throws SQLException {
        Item mockItem = new Item();
        mockItem.setId("ITEM001");

        // Call deleteItem method
        ItemStorage.deleteItem(mockItem);

        // Verify PreparedStatement setup
        verify(mockPreparedStatement).setString(1, "ITEM001");
        verify(mockPreparedStatement).execute();
    }

    @Test
    @DisplayName("readItems should retrieve items from the database")
    void testReadItems() throws SQLException {
        // Mock the result set to simulate a single item
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);  // One item, then end of result set
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Sample Item");
        when(mockResultSet.getDouble("unitPrice")).thenReturn(10.0);
        when(mockResultSet.getDouble("qtyInStock")).thenReturn(5.0);

        // Mock PreparedStatement to return the ResultSet
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Mock ID formatting
        idGeneratorMockedStatic.when(() -> IDGenerator.formatID(1)).thenReturn("ITEM001");

        // Call readItems method
        ArrayList<Item> items = ItemStorage.readItems();

        // Verify the result
        assertEquals(1, items.size());
        assertEquals("ITEM001", items.getFirst().getId());
        assertEquals("Sample Item", items.getFirst().getName());
        assertEquals(10.0, items.getFirst().getUnitPrice());
        assertEquals(5.0, items.getFirst().getQtyInStock());
    }

    @AfterEach
    void tearDown() {
        dbManagerMockedStatic.close();
        idGeneratorMockedStatic.close();
    }
}
