package models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TransactionTypeTest {

    @Test
    @DisplayName("Converting Added Enum to String should be accurate")
    void testConvertAddedToString() {
        TransactionType transactionType = TransactionType.ADDED;
        String result = transactionType.toString();
        String expected = "ADDED";
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Converting Added String to Enum should be accurate")
    void testConvertAddedToEnum() {
        String stringType = "ADDED";
        TransactionType result = TransactionType.valueOf(stringType);
        TransactionType expectedResult = TransactionType.ADDED;
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Converting Updated Enum to String should be accurate")
    void testConvertUpdatedToString() {
        TransactionType transactionType = TransactionType.UPDATED;
        String result = transactionType.toString();
        String expected = "UPDATED";
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Converting Updated String to Enum should be accurate")
    void testConvertUpdatedToEnum() {
        String stringType = "UPDATED";
        TransactionType result = TransactionType.valueOf(stringType);
        TransactionType expectedResult = TransactionType.UPDATED;
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Converting Removed Enum to String should be accurate")
    void testConvertRemovedToString() {
        TransactionType transactionType = TransactionType.REMOVED;
        String result = transactionType.toString();
        String expected = "REMOVED";
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Converting Removed String to Enum should be accurate")
    void testConvertRemovedToEnum() {
        String stringType = "REMOVED";
        TransactionType result = TransactionType.valueOf(stringType);
        TransactionType expectedResult = TransactionType.REMOVED;
        Assertions.assertEquals(expectedResult, result);
    }
}
