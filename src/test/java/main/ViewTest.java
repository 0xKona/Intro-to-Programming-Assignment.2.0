package main;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ViewTest {

    @BeforeAll
    static void setUp() {
        Mockito.clearAllCaches();
        Mockito.clearInvocations();
    }

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut); // Restore original System.out after test
    }

    @Test
    @DisplayName("Display Main Menu should print the menu and call handleMainMenuChoice without executing it")
    void testDisplayMainMenu() {
        try (MockedStatic<View> mockedView = Mockito.mockStatic(View.class)) {
            // Mock handleMainMenuChoice to prevent real execution but still verify it's called
            mockedView.when(View::displayMainMenu).thenCallRealMethod();

            // Act: Call the displayMainMenu method
            View.displayMainMenu();

            // Verify the output contains the expected menu text
            String output = outContent.toString();
            assertTrue(output.contains("I N V E N T O R Y    M A N A G E M E N T    S Y S T E M"));
            assertTrue(output.contains(View.mediumLineBreak));
            assertTrue(output.contains("1. ADD NEW ITEM"));
            assertTrue(output.contains("2. UPDATE QUANTITY OF EXISTING ITEM"));
            assertTrue(output.contains("3. REMOVE ITEM"));
            assertTrue(output.contains("4. VIEW DAILY TRANSACTION REPORT"));
            assertTrue(output.contains(View.mediumLineBreak));
            assertTrue(output.contains("5. Exit"));

            // Verify handleMainMenuChoice was called exactly once
            mockedView.verify(View::handleMainMenuChoice, Mockito.times(1));
        }
    }
}
