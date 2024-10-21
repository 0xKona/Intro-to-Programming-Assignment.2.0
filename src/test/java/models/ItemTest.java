//package models;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//public class ItemTest {
//
//    Item testItem;
//
//    @BeforeEach
//    void setUp() {
//        testItem = new Item();
//    }
//
//    @Test
//    @DisplayName("Setting and Getting Item ID should work")
//    void testSetID() {
//        testItem.setId(String.valueOf(11111));
//        Assertions.assertEquals(11111, testItem.getId());
//    }
//
//    @Test
//    @DisplayName("Setting and Getting Item Name should work")
//    void testSetName() {
//        testItem.setName("Test");
//        Assertions.assertEquals("Test", testItem.getName());
//    }
//
//    @Test
//    @DisplayName("Setting and getting Item unit price should work")
//    void testSetUnitPrice() {
//        testItem.setUnitPrice(1.0);
//        Assertions.assertEquals(1.0, testItem.getUnitPrice());
//    }
//
//    @Test
//    @DisplayName("Setting and getting Item quantity should work")
//    void testSetQuantity() {
//        testItem.setQtyInStock(1.0);
//        Assertions.assertEquals(1.0, testItem.getQtyInStock());
//    }
//
//    @Test
//    @DisplayName("Total Value should calculate correctly when qty or unit price changes")
//    void testTotalValue() {
//        testItem.setQtyInStock(10);
//        testItem.setUnitPrice(10);
//        Assertions.assertEquals(100, testItem.getTotalValue());
//    }
//
//}
