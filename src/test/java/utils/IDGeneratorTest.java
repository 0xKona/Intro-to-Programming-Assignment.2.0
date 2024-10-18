package utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IDGeneratorTest {

    @Test
    @DisplayName("generateNewID should return a new ID based on existing IDs")
    void testGenerateNewID() {
        // Arrange: Set up mock inUseIDs
        ArrayList<Integer> mockInUseIDs = new ArrayList<>();
        mockInUseIDs.add(10000);
        mockInUseIDs.add(10001);
        mockInUseIDs.add(10002);
        mockInUseIDs.add(10003);

        // Inject the mock IDs
        IDGenerator.setInUseIDs(mockInUseIDs);

        try (MockedStatic<IDGenerator> mockedIDGenerator = Mockito.mockStatic(IDGenerator.class)) {
            // Mock readItemIDs and readTransactionIDs to do nothing
            mockedIDGenerator.when(IDGenerator::readItemIDs).thenAnswer(invocation -> null);
            mockedIDGenerator.when(IDGenerator::readTransactionIDs).thenAnswer(invocation -> null);
            mockedIDGenerator.when(IDGenerator::generateNewID).thenCallRealMethod();

            // Act: Call generateNewID
            int newID = IDGenerator.generateNewID();

            // Assert: The new ID should be 10004
            assertEquals(10004, newID);

        }


    }
}
