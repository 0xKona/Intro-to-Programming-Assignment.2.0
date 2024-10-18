package controller;

import models.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import storage.TransactionStorage;
import testData.MockData;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.mockito.Mockito.times;

public class TransactionControllerTest {

    @BeforeEach
    void resetTransactionsField() throws Exception {
        // Use reflection to gain access to private field "transactions" despite it being private
        Field transactionsField = TransactionController.class.getDeclaredField("transactions");
        transactionsField.setAccessible(true);
        transactionsField.set(null, null);
    }

    @Test
    @DisplayName("Initializing Transaction List should work correctly on success")
    void testInitializeTransactionList_Success() throws IOException {
        // Create an ArrayList of mock items to be returned when the read items method is called
        ArrayList<Transaction> mockTransactions = MockData.getMockTransactionsList();

        try {
            /* Mock the item storage class using Mockito, this ensures that when we run our tests we do
            not read from the actual storage and instead return a fake mock list when the readItems method is
            called */
            var mockedTransactionStorage = Mockito.mockStatic(TransactionStorage.class);
            mockedTransactionStorage.when(TransactionStorage::readTransactions).thenReturn(mockTransactions);

            // Call the method we are now testing
            TransactionController.initializeTransactionList();

            // Assert: Use reflection to access the private static field "items"
            Field transactionsField = TransactionController.class.getDeclaredField("transactions");
            transactionsField.setAccessible(true);  // Allow access to the private field
            @SuppressWarnings("unchecked") // This stops warnings about unchecked casting, which we need to do here
            ArrayList<Transaction> transactions = (ArrayList<Transaction>) transactionsField.get(null);  // Get the static field value

            Assertions.assertEquals(mockTransactions, transactions );
            mockedTransactionStorage.verify(TransactionStorage::readTransactions, times(1));
            mockedTransactionStorage.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
