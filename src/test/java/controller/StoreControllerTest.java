//package controller;
//
//import main.View;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockedStatic;
//import org.mockito.Mockito;
//
//public class StoreControllerTest {
//
//    @Test
//    @DisplayName("Start should call View.displayMainMenu()")
//    void testStartRunsSuccessfully() {
//        // Mock the View class so we don't call the real methods
//        try (MockedStatic<View> mockedView = Mockito.mockStatic(View.class)) {
//            StoreController.start(); // Call the start method we are testing
//            // Check that displayMainMenu was called once via the mock.
//            mockedView.verify(View::displayMainMenu, Mockito.times(1));
//        }
//    }
//
//    @Test
//    @DisplayName("mainMenuRouting should call addNewItem when user enters 1")
//    void testRouterRoutesUserToAddNewItem() {
//        try (MockedStatic<ItemController> mockedView = Mockito.mockStatic(ItemController.class)) {
//            StoreController.mainMenuRouting(1);
//            mockedView.verify(ItemController::addNewItem, Mockito.times(1));
//        }
//    }
//
//    @Test
//    @DisplayName("mainMenuRouting should call editItemQuantity when user enters 2")
//    void testRouterRoutesUserToEditItemQuantity() {
//        try (MockedStatic<ItemController> mockedView = Mockito.mockStatic(ItemController.class)) {
//            StoreController.mainMenuRouting(2);
//            mockedView.verify(ItemController::editItemQuantity, Mockito.times(1));
//        }
//    }
//
//    @Test
//    @DisplayName("mainMenuRouting should call deleteItem when user enters 3")
//    void testRouterRoutesUserToDeleteItem() {
//        try (MockedStatic<ItemController> mockedView = Mockito.mockStatic(ItemController.class)) {
//            StoreController.mainMenuRouting(3);
//            mockedView.verify(ItemController::deleteItem, Mockito.times(1));
//        }
//    }
//
//    @Test
//    @DisplayName("mainMenuRouting should call generateDailyTransactionReport when user enters 4")
//    void testRouterRoutesUserToGenerateDailyTransactionReport() {
//        try (MockedStatic<TransactionController> mockedView = Mockito.mockStatic(TransactionController.class)) {
//            StoreController.mainMenuRouting(4);
//            mockedView.verify(TransactionController::generateDailyTransactionReport, Mockito.times(1));
//        }
//    }
//
//    @Test
//    @DisplayName("mainMenuRouting should call System.exit when user enters 5")
//    void testRouterRoutesUserToExitProgram() {
//        try (MockedStatic<StoreController> mockedController = Mockito.mockStatic(StoreController.class)) {
//
//            // Mock the exitProgram method to prevent it from actually calling System.exit
//            mockedController.when(() -> StoreController.mainMenuRouting(5)).thenCallRealMethod();
//
//            // Call the method that triggers exitProgram
//            StoreController.mainMenuRouting(5);
//
//            // Verify that exitProgram was called with argument 0
//            mockedController.verify(() -> StoreController.exitProgram(0), Mockito.times(1));
//        }
//    }
//
//    @Test
//    @DisplayName("mainMenuRouting should call handleMainMenuChoice when an invalid input is entered")
//    void testRouterRoutesUserToHandleInvalidInput() {
//        try (MockedStatic<View> mockedView = Mockito.mockStatic(View.class)) {
//            StoreController.mainMenuRouting(-1);
//            mockedView.verify(View::handleMainMenuChoice, Mockito.times(1));
//        }
//    }
//}
