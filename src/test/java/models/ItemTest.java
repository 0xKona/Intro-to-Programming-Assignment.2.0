package models;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import storage.ItemStorage;
import storage.TransactionStorage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class ItemTest {

    Item testItem;
    MockedStatic<TransactionStorage> mockedTransactionStorage;
    MockedStatic<ItemStorage> mockedItemStorage;


    @BeforeEach
    void setUp() {
        testItem = new Item();
        mockedTransactionStorage = Mockito.mockStatic(TransactionStorage.class);
        mockedItemStorage = Mockito.mockStatic(ItemStorage.class);
    }

    @AfterEach
    void tearDown() {
        mockedTransactionStorage.close();
        mockedItemStorage.close();
    }

    @Test
    @DisplayName("Setting and Getting Item ID should work")
    void testSetID() {
        testItem.setId("11111");
        Assertions.assertEquals("11111", testItem.getId());
    }

    @Test
    @DisplayName("Setting and Getting Item Name should work")
    void testSetName() {
        testItem.setName("Test");
        Assertions.assertEquals("Test", testItem.getName());
    }

    @Test
    @DisplayName("Setting and getting Item unit price should work")
    void testSetUnitPrice() {
        testItem.setUnitPrice(1.0);
        Assertions.assertEquals(1.0, testItem.getUnitPrice());
    }

    @Test
    @DisplayName("Setting and getting Item quantity should work")
    void testSetQuantity() {
        testItem.setQtyInStock(1.0);
        Assertions.assertEquals(1.0, testItem.getQtyInStock());
    }

    @Test
    @DisplayName("Total Value should calculate correctly when qty or unit price changes")
    void testTotalValue() {
        testItem.setQtyInStock(10);
        testItem.setUnitPrice(10);
        Assertions.assertEquals(100, testItem.getTotalValue());
    }

    @Test
    @DisplayName("Item class should hold previous quantity in stock when updated")
    void testPreviousQuantity() {
        double originalQuantity = 5.0;
        testItem.setQtyInStock(originalQuantity);
        double newQuantity = 10.0;
        testItem.setQtyInStock(newQuantity);

        Assertions.assertEquals(originalQuantity, testItem.getPreviousQuantityInStock());
        Assertions.assertEquals(newQuantity, testItem.getQtyInStock());
    }

    @Test
    @DisplayName("Item class should hold previous total value in stock when updated")
    void testPreviousTotalValue() {
        double originalQuantity = 10.0;
        double originalUnitPrice = 10.0;

        testItem.setQtyInStock(originalQuantity);
        testItem.setUnitPrice(originalUnitPrice);

        double newQuantity = 5.0;

        testItem.setQtyInStock(newQuantity);

        Assertions.assertEquals(100, testItem.getPreviousTotalValue());
        Assertions.assertEquals(50, testItem.getTotalValue());

    }

    @Test
    @DisplayName("Submit new item should submit and call generate transaction")
    void testSubmitNewItem() {
        testItem.submitNewItem();

        mockedItemStorage.verify(() -> ItemStorage.addNewItem(testItem), times(1));
        mockedTransactionStorage.verify(() -> TransactionStorage.writeNewTransaction(any(Transaction.class)), times(1));
    }

    @Test
    @DisplayName("Submit updated item should submit and call generate transaction")
    void testSubmitUpdatedItem() {
        testItem.submitUpdatedItem();
        mockedItemStorage.verify(() -> ItemStorage.updateItem(testItem), times(1));
        mockedTransactionStorage.verify(() -> TransactionStorage.writeNewTransaction(any(Transaction.class)), times(1));
    }

    @Test
    @DisplayName("Submit delete item shouldl submit to ItemStorage and generate transaction")
    void testSubmitDeleteItem() {
        testItem.submitDeleteItem();
        mockedItemStorage.verify(() -> ItemStorage.deleteItem(testItem), times(1));
        mockedTransactionStorage.verify(() -> TransactionStorage.writeNewTransaction(any(Transaction.class)), times(1));
    }

}
