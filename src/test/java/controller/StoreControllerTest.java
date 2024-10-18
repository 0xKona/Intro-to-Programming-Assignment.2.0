package controller;

import main.View;
import mockit.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class StoreControllerTest {

    @Test
    @DisplayName("Start should call View.displayMainMenu()")
    void testStartRunsSuccessfully() {
        // Mock the View class so we don't call the real methods
        try (MockedStatic<View> mockedView = Mockito.mockStatic(View.class)) {
            StoreController.start(); // Call the start method we are testing
            // Check that displayMainMenu was called once via the mock.
            mockedView.verify(View::displayMainMenu, Mockito.times(1));
        }
    }

    @Test
    @DisplayName("mainMenuRouting should call addNewItem when user enters 1")
    void testRouterRoutesUserToAddNewItem() {
        try (MockedStatic<ItemController> mockedView = Mockito.mockStatic(ItemController.class)) {
            StoreController.mainMenuRouting(1);
            mockedView.verify(ItemController::addNewItem, Mockito.times(1));
        }
    }

    @Test
    @DisplayName("mainMenuRouting should call editItemQuantity when user enters 2")
    void testRouterRoutesUserToEditItemQuantity() {
        try (MockedStatic<ItemController> mockedView = Mockito.mockStatic(ItemController.class)) {
            StoreController.mainMenuRouting(2);
            mockedView.verify(ItemController::editItemQuantity, Mockito.times(1));
        }
    }

    @Test
    @DisplayName("mainMenuRouting should call deleteItem when user enters 3")
    void testRouterRoutesUserToDeleteItem() {
        try (MockedStatic<ItemController> mockedView = Mockito.mockStatic(ItemController.class)) {
            StoreController.mainMenuRouting(3);
            mockedView.verify(ItemController::deleteItem, Mockito.times(1));
        }
    }

    @Test
    @DisplayName("mainMenuRouting should call generateDailyTransactionReport when user enters 4")
    void testRouterRoutesUserToGenerateDailyTransactionReport() {
        try (MockedStatic<TransactionController> mockedView = Mockito.mockStatic(TransactionController.class)) {
            StoreController.mainMenuRouting(4);
            mockedView.verify(TransactionController::generateDailyTransactionReport, Mockito.times(1));
        }
    }

    /* To test System.exit we needed to use the JMockit dependency this allows us to mock the System class
    without calling the exit method itself and ending the test before it could finish, there were other methods that
    were tried however all of which were deprecated after Java 17 or caused security errors on my machine,
    it is possible however that was caused by organisational policies. However, the JMockit library makes it
    possible to test System exit operations safely. */
    @Test
    @DisplayName("mainMenuRouting should call System.exit when user enters 5")
    void testRouterRoutesUserToExitProgram() {
        // Mock System.exit to throw a RuntimeException instead of exiting
        new MockUp<System>() {
            @Mock
            public void exit(int value) {
                throw new RuntimeException(String.valueOf(value)); // Throw exception with the exit code
            }
        };

        // Assert that the RuntimeException is thrown with the expected exit code (0)
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            // Call mainMenuRouting with input 5, which should trigger System.exit(0)
            StoreController.mainMenuRouting(5);
        });

        // Verify that System.exit was called with the expected status code (0)
        assert(exception.getMessage().equals("0"));  // The exit code should be passed in the exception
    }

    @Test
    @DisplayName("mainMenuRouting should call handleMainMenuChoice when an invalid input is entered")
    void testRouterRoutesUserToHandleInvalidInput() {
        try (MockedStatic<View> mockedView = Mockito.mockStatic(View.class)) {
            StoreController.mainMenuRouting(-1);
            mockedView.verify(View::handleMainMenuChoice, Mockito.times(1));
        }
    }
}
