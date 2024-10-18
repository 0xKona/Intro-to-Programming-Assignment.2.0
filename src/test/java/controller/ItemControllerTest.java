package controller;

import models.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import storage.ItemStorage;
import testData.MockData;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.mockito.Mockito.times;

public class ItemControllerTest {

    @BeforeEach
    void resetItemsField() throws Exception {
        // Use reflection to gain access to private field "items" despite it being private
        Field itemsField = ItemController.class.getDeclaredField("items");
        itemsField.setAccessible(true);
        itemsField.set(null, null);
    }

    @Test
    @DisplayName("Initializing Item List should work correctly on success")
    void testInitializeItemList_Success() throws IOException {
        // Create an ArrayList of mock items to be returned when the read items method is called
        ArrayList<Item> mockItems = MockData.getMockItemsList();

        try {
            /* Mock the item storage class using Mockito, this ensures that when we run our tests we do
            not read from the actual storage and instead return a fake mock list when the readItems method is
            called */
            var mockedItemStorage = Mockito.mockStatic(ItemStorage.class);
            mockedItemStorage.when(ItemStorage::readItems).thenReturn(mockItems);

            // Call the method we are now testing
            ItemController.initializeItemList();

            // Assert: Use reflection to access the private static field "items"
            Field itemsField = ItemController.class.getDeclaredField("items");
            itemsField.setAccessible(true);  // Allow access to the private field
            @SuppressWarnings("unchecked") // This stops warnings about unchecked casting, which we need to do here
            ArrayList<Item> items = (ArrayList<Item>) itemsField.get(null);  // Get the static field value

            Assertions.assertEquals(mockItems, items );
            mockedItemStorage.verify(ItemStorage::readItems, times(1));

            mockedItemStorage.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
