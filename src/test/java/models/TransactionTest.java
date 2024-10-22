package models;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import storage.TransactionStorage;
import utils.IDGenerator;
import utils.TimestampGenerator;

import static org.mockito.Mockito.times;

class TransactionTest {

    Transaction testTransaction;
    Item testItem;

    MockedStatic<TimestampGenerator> mockedTimestampGenerator;
    MockedStatic<TransactionStorage> mockedTransactionStorage;
    MockedStatic<IDGenerator> mockedIDGenerator;

    @BeforeEach
    void setUp() {
        testTransaction = new Transaction();
        testItem = new Item();

        mockedTimestampGenerator = Mockito.mockStatic(TimestampGenerator.class);
        mockedTransactionStorage = Mockito.mockStatic(TransactionStorage.class);
        mockedIDGenerator = Mockito.mockStatic(IDGenerator.class);

        testItem.setId("11111");
        testItem.setName("testItem");
        testItem.setQtyInStock(10.0);
        testItem.setUnitPrice(10.0);
    }

    @AfterEach
    void tearDown() {
        mockedTimestampGenerator.close();
        mockedTransactionStorage.close();
        mockedIDGenerator.close();
    }

    @Test
    @DisplayName("Setting and Getting Transaction description should work")
    void testGetAndSetDescription() {
        testTransaction.setDescription("test_description");
        Assertions.assertEquals("test_description", testTransaction.getDescription());
    }

    @Test
    @DisplayName("Setting and Getting Transaction Quantity Change should work")
    void testGetAndSetQuantityChange() {
        testTransaction.setQuantityChange(-10);
        Assertions.assertEquals(-10, testTransaction.getQuantityChange());
    }

    @Test
    @DisplayName("Setting and Getting Value change should work")
    void testGetAndSetValueChange() {
        testTransaction.setValueChange(-100);
        Assertions.assertEquals(-100, testTransaction.getValueChange());
    }

    @Test
    @DisplayName("Setting and Getting Quantity remaining should work")
    void testGetAndSetQuantityRemaining() {
        testTransaction.setQuantityRemaining(50);
        Assertions.assertEquals(50, testTransaction.getQuantityRemaining());
    }

    @Test
    @DisplayName("Setting and Getting Transaction Type should work")
    void testGetAndSetTransactionType() {
        testTransaction.setTransactionType(TransactionType.ADDED);
        Assertions.assertEquals(TransactionType.ADDED, testTransaction.getTransactionType());

        testTransaction.setTransactionType(TransactionType.REMOVED);
        Assertions.assertEquals(TransactionType.REMOVED, testTransaction.getTransactionType());

        testTransaction.setTransactionType(TransactionType.UPDATED);
        Assertions.assertEquals(TransactionType.UPDATED, testTransaction.getTransactionType());
    }

    @Test
    @DisplayName("Setting and Getting a Timestamp should work")
    void testGetAndSetTimestamp() {
        String testTimestamp = TimestampGenerator.getCurrentTime();

        testTransaction.setTimestamp(testTimestamp);
        Assertions.assertEquals(testTimestamp, testTransaction.getTimestamp());
    }

    @Test
    @DisplayName("Should be able to generate transaction with ADDED transaction type")
    void testGenerateADDED() {
        testTransaction.generate(testItem, TransactionType.ADDED);

        Assertions.assertEquals(TransactionType.ADDED, testTransaction.getTransactionType());
        Assertions.assertEquals("testItem has been ADDED", testTransaction.getDescription());
        mockedTransactionStorage.verify(() -> TransactionStorage.writeNewTransaction(testTransaction), times(1));
    }

    @Test
    @DisplayName("Should be able to generate transaction with UPDATED transaction type")
    void testGenerateUPDATED() {
        testTransaction.generate(testItem, TransactionType.UPDATED);

        Assertions.assertEquals(TransactionType.UPDATED, testTransaction.getTransactionType());
        Assertions.assertEquals("testItem has been UPDATED", testTransaction.getDescription());

        mockedTransactionStorage.verify(() -> TransactionStorage.writeNewTransaction(testTransaction), times(1));
    }

    @Test
    @DisplayName("Should be able to generate transaction with REMOVED transaction type")
    void testGenerateREMOVED() {
        testTransaction.generate(testItem, TransactionType.REMOVED);
        Assertions.assertEquals(TransactionType.REMOVED, testTransaction.getTransactionType());
        Assertions.assertEquals("testItem has been REMOVED", testTransaction.getDescription());
        mockedTransactionStorage.verify(() -> TransactionStorage.writeNewTransaction(testTransaction), times(1));
    }
}