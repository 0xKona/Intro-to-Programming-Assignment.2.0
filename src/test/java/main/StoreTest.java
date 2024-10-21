//package main;
//
//import controller.StoreController;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockedStatic;
//import org.mockito.Mockito;
//import storage.FileManager;
//
//import java.io.FileNotFoundException;
//
//public class StoreTest {
//
//    // Define the global static mocks
//    private MockedStatic<FileManager> mockedFileManager;
//    private MockedStatic<StoreController> mockedStoreController;
//
//    @BeforeEach
//    public void setUp() {
//        // Initialize static mocks for FileManager and StoreController
//        mockedFileManager = Mockito.mockStatic(FileManager.class);
//        mockedStoreController = Mockito.mockStatic(StoreController.class);
//    }
//
//    @AfterEach
//    public void tearDown() {
//        // Close static mocks to avoid interference
//        mockedFileManager.close();
//        mockedStoreController.close();
//    }
//
//    @Test
//    @DisplayName("Main should call FileManager and StoreController correctly")
//    void testFileManager() throws FileNotFoundException {
//        // Act: Call the main method of the Store class
//        Store.main(new String[]{});
//
//        // Verify that FileManager.initializeStorage() was called once
//        mockedFileManager.verify(FileManager::initializeStorage, Mockito.times(1));
//
//        // Verify that StoreController.start() was called once
//        mockedStoreController.verify(StoreController::start, Mockito.times(1));
//    }
//}
