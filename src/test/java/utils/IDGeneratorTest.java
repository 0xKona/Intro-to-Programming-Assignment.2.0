package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class IDGeneratorTest {

    @Test
    @DisplayName("Generate new ID should work correctly when called with TableType.Items")
    void testGeneratingNewIDForItem() throws SQLException {
        ArrayList<String> inUseID = new ArrayList<>(Arrays.asList("10000", "10001", "10002", "10003", "10004", "10005", "10006", "10007"));

        String table = String.valueOf(TableType.Items);

        try (MockedStatic<IDGenerator> mockedIDGenerator = Mockito.mockStatic(IDGenerator.class)) {
            mockedIDGenerator.when(() -> IDGenerator.generateNewID(TableType.valueOf(table))).thenCallRealMethod();
            when(IDGenerator.formatID(anyInt())).thenCallRealMethod();
            when(IDGenerator.checkIfIDExists(anyInt(), anyString())).thenAnswer(invocation -> {
               int checkingThisInt = invocation.getArgument(0);
               String formatToCheck = IDGenerator.formatID(checkingThisInt);
               boolean idInUse = inUseID.contains(formatToCheck);
               return idInUse;
            });
        }

        String testID = IDGenerator.generateNewID(TableType.Items);
        boolean result = inUseID.contains(testID);
        assertFalse(result);
    }

    @Test
    @DisplayName("Generate new ID should work correctly when called with TableType.Transactions")
    void testGeneratingNewIDForTransaction() throws SQLException {
        ArrayList<String> inUseID = new ArrayList<>(Arrays.asList("10000", "10001", "10002", "10003", "10004", "10005", "10006", "10007"));

        String table = String.valueOf(TableType.Transactions);

        try (MockedStatic<IDGenerator> mockedIDGenerator = Mockito.mockStatic(IDGenerator.class)) {
            mockedIDGenerator.when(() -> IDGenerator.generateNewID(TableType.valueOf(table))).thenCallRealMethod();
            when(IDGenerator.formatID(anyInt())).thenCallRealMethod();
            when(IDGenerator.checkIfIDExists(anyInt(), anyString())).thenAnswer(invocation -> {
                int checkingThisInt = invocation.getArgument(0);
                String formatToCheck = IDGenerator.formatID(checkingThisInt);
                boolean idInUse = inUseID.contains(formatToCheck);
                return idInUse;
            });
        }

        String testID = IDGenerator.generateNewID(TableType.Transactions);
        boolean result = inUseID.contains(testID);
        assertFalse(result);
    }


    @Test
    @DisplayName("Format ID Should work correctly when needing leading zero")
    void testFormatID() {
        String expected = "00001";
        String actual = IDGenerator.formatID(1);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Format ID Should work correctly when not needing leading zero")
    void testFormatIDNoLeadingZero() {
        String expected = "10000";
        String actual = IDGenerator.formatID(10000);
        assertEquals(expected, actual);
    }
}
