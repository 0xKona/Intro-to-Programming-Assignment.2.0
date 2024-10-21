//package main;
//
//import controller.StoreController;
//import org.junit.jupiter.api.*;
//import org.mockito.MockedStatic;
//import org.mockito.Mockito;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.io.PrintStream;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class ViewTest {
//
//    @BeforeAll
//    static void setUp() {
//        Mockito.clearAllCaches();
//        Mockito.clearInvocations();
//    }
//
//    private final PrintStream originalOut = System.out;
//    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//    private final InputStream originalIn = System.in;
//
//    @BeforeEach
//    void setUpStreams() {
//        System.setOut(new PrintStream(outContent));
//    }
//
//    @AfterEach
//    void restoreStreams() {
//        System.setOut(originalOut); // Restore original System.out after test
//        System.setIn(originalIn); // Restore System.in after tests
//    }
//
//    @Test
//    @DisplayName("Display Main Menu should print the menu and call handleMainMenuChoice without executing it")
//    void testDisplayMainMenu() {
//        try (MockedStatic<View> mockedView = Mockito.mockStatic(View.class)) {
//            // Mock handleMainMenuChoice to prevent real execution but still verify it's called
//            mockedView.when(View::displayMainMenu).thenCallRealMethod();
//
//            // Act: Call the displayMainMenu method
//            View.displayMainMenu();
//
//            // Verify the output contains the expected menu text
//            String output = outContent.toString();
//            assertTrue(output.contains("I N V E N T O R Y    M A N A G E M E N T    S Y S T E M"));
//            assertTrue(output.contains(View.mediumLineBreak));
//            assertTrue(output.contains("1. ADD NEW ITEM"));
//            assertTrue(output.contains("2. UPDATE QUANTITY OF EXISTING ITEM"));
//            assertTrue(output.contains("3. REMOVE ITEM"));
//            assertTrue(output.contains("4. VIEW DAILY TRANSACTION REPORT"));
//            assertTrue(output.contains(View.mediumLineBreak));
//            assertTrue(output.contains("5. Exit"));
//
//            // Verify handleMainMenuChoice was called exactly once
//            mockedView.verify(View::handleMainMenuChoice, Mockito.times(1));
//        }
//    }
//
//    @Test
//    @DisplayName("Handle main menu choice should call main menu routing with user input")
//    void testHandleMainMenuChoice() {
//        // Simulate user input "1\n" (for selecting option 1: Add New Item)
//        String simulatedInput = "1\n";
//        ByteArrayInputStream simulatedIn = new ByteArrayInputStream(simulatedInput.getBytes());
//        System.setIn(simulatedIn);
//
//        try (MockedStatic<StoreController> mockedStoreController = Mockito.mockStatic(StoreController.class)) {
//            View.handleMainMenuChoice(); // Call Method
//
//            // Verify mainMenuRouting was called with the simulated input.
//            mockedStoreController.verify(() -> StoreController.mainMenuRouting(1), Mockito.times(1));
//        }
//    }
//}
