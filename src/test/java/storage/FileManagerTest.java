//package storage;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockedStatic;
//import org.mockito.Mockito;
//
//import java.io.File;
//
//public class FileManagerTest {
//
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
//
//}
