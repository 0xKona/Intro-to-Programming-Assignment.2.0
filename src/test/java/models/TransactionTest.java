//package models;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import utils.TimestampGenerator;
//
//class TransactionTest {
//
//    Transaction testTransaction;
//
//    @BeforeEach
//    void setUp() {
//        testTransaction = new Transaction();
//    }
//
//    @Test
//    @DisplayName("Setting and Getting Transaction ID should work")
//    void testGetAndSetId() {
//        testTransaction.setId(String.valueOf(1));
//        Assertions.assertEquals(1, testTransaction.getId());
//    }
//
//    @Test
//    @DisplayName("Setting and Getting Transaction description should work")
//    void testGetAndSetDescription() {
//        testTransaction.setDescription("test_description");
//        Assertions.assertEquals("test_description", testTransaction.getDescription());
//    }
//
//    @Test
//    @DisplayName("Setting and Getting Transaction Quantity Change should work")
//    void testGetAndSetQuantityChange() {
//        testTransaction.setQuantityChange(-10);
//        Assertions.assertEquals(-10, testTransaction.getQuantityChange());
//    }
//
//    @Test
//    @DisplayName("Setting and Getting Value change should work")
//    void testGetAndSetValueChange() {
//        testTransaction.setValueChange(-100);
//        Assertions.assertEquals(-100, testTransaction.getValueChange());
//    }
//
//    @Test
//    @DisplayName("Setting and Getting Quantity remaining should work")
//    void testGetAndSetQuantityRemaining() {
//        testTransaction.setQuantityRemaining(50);
//        Assertions.assertEquals(50, testTransaction.getQuantityRemaining());
//    }
//
//    @Test
//    @DisplayName("Setting and Getting Transaction Type should work")
//    void testGetAndSetTransactionType() {
//        testTransaction.setTransactionType(TransactionType.ADDED);
//        Assertions.assertEquals(TransactionType.ADDED, testTransaction.getTransactionType());
//
//        testTransaction.setTransactionType(TransactionType.REMOVED);
//        Assertions.assertEquals(TransactionType.REMOVED, testTransaction.getTransactionType());
//
//        testTransaction.setTransactionType(TransactionType.UPDATED);
//        Assertions.assertEquals(TransactionType.UPDATED, testTransaction.getTransactionType());
//    }
//
//    @Test
//    @DisplayName("Setting and Getting a Timestamp should work")
//    void testGetAndSetTimestamp() {
//        String testTimestamp = TimestampGenerator.getCurrentTime();
//
//        testTransaction.setTimestamp(testTimestamp);
//        Assertions.assertEquals(testTimestamp, testTransaction.getTimestamp());
//    }
//}