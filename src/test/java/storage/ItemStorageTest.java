package storage;

import models.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.mockito.Mockito.times;

//public class ItemStorageTest {

//    @Test
//    @DisplayName("ItemStorage.addNewItem should be called with an Item correctly")
//    void testAddNewItem() {
//        try (MockedStatic<DatabaseManager> mockedDatabaseManager = Mockito.mockStatic(DatabaseManager.class)) {
//            MockedStatic<ItemStorage> mockedItemStorage = Mockito.mockStatic(ItemStorage.class);
//            Item testItem = new Item();
//
//            MockedStatic<Connection> mockedConnection = Mockito.mockStatic(Connection.class);
//            Connection connection = Mockito.mock(Connection.class);
//
//            mockedItemStorage.when(() -> ItemStorage.addNewItem(testItem)).thenCallRealMethod();
//            mockedDatabaseManager.when(DatabaseManager::connect).thenReturn(Mockito.mock(Connection.class));
//            mockedConnection.when(() -> connection.prepareStatement(Mockito.anyString())).thenReturn(Mockito.mock(PreparedStatement.class));
//
//            ItemStorage.addNewItem(testItem);
//
//            mockedDatabaseManager.verify(DatabaseManager::connect, times(1));
//
//
//
//
//        }
//    }

//    @BeforeEach
//    void setup() {
//        // Clear any previous files to avoid conflicts.
//        new File(FileManager.ITEMS_FILE_PATH).delete();
//        new File(FileManager.TRANSACTIONS_FILE_PATH).delete();
//    }
//
//    @Test
//    @DisplayName("Initialize Storage should call both checkItems and CheckTransactions")
//    void testInitializeStorage() {
//        try (MockedStatic<FileManager> mockedFileManager = Mockito.mockStatic(FileManager.class)) {
//            mockedFileManager.when(FileManager::initializeStorage).thenCallRealMethod();
//
//            FileManager.initializeStorage();
//
//            mockedFileManager.verify(FileManager::checkItemsFile, Mockito.times(1));
//            mockedFileManager.verify(FileManager::checkTransactionsFile, Mockito.times(1));
//        }
//    }
//
//    // TODO - Expand on these tests for full coverage in production.

//}
