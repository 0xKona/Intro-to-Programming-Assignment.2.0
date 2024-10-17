package storage;

import controller.TransactionController;
import models.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Stream;

public class ItemStorage {

    /**
     * Handles writing a new item into the text file storage.
     * @param item Instance of the Item class.
     */
    public static void writeNewItemToStorage(Item item) {
        String itemAsString = formatItemToString(item);
        try (Writer output = new FileWriter(FileManager.ITEMS_FILE_PATH, true)) {
            output.append(itemAsString);
        } catch (IOException e) {
            System.out.println("An error occurred while trying to write the new line to the file: " + e);
        }
    }

    /**
     * Reads Items from storage, formats them and returns them in an ArrayList.
     * @return ArrayList
     */
    public static ArrayList<Item> readItems() throws FileNotFoundException {
        // BufferedReader reads the file line by line
        BufferedReader reader = new BufferedReader(new FileReader(FileManager.ITEMS_FILE_PATH));
        // Skip the first header line and initialize the stream.
        Stream<String> lines = reader.lines().skip(1);
        ArrayList<Item> items = new ArrayList<>();
        /* For each line in the stream from the buffered reader, we can perform an operation on the string, if the line
        is empty we return immediately, if not we perform the formatting into an Item object
         */
        lines.forEach(line -> {
            if (line.isEmpty()) {
                return;
            } else {
                String[] splitLine = line.split(",");
                Item item = new Item();
                item.setId(Integer.parseInt(splitLine[0]));
                item.setName(splitLine[1]);
                item.setUnitPrice(Double.parseDouble(splitLine[2]));
                item.setQtyInStock(Double.parseDouble(splitLine[3]));
                /* I don't need to set the total value element in the item object since the Item class updates it
                automatically when the quantity or unit price changes */
                items.add(item);
            }
        });
        return items;
    }

    public static void updateItem(Item item) {
        ArrayList<String> lines = readLinesIntoList();

        // Find the line with the matching ID and replace it
        boolean itemFound = false;
        for (int i = 0; i < lines.size(); i++) {
            String currentLine = lines.get(i);
            if (currentLine.trim().isEmpty()) { // Skip empty lines
                continue;
            }
            if (i == 0) { // Skip the header line at index 0
                continue;
            }

            String[] splitLine = currentLine.split(","); // Split the line into an Array at each comma
            int currentID = Integer.parseInt(splitLine[0]); // The ID is the first thing in each line at index 0

            if (currentID == item.getId()) { // Check if the currentID matches the updatedItems ID
                String newItemLine = formatItemToString(item); // Format the updated item into a string
                lines.set(i, newItemLine); // Update that line to the updated string
                itemFound = true; // set item found to true so below if statement doesn't trigger
                break;
            }
        }

        if (!itemFound) {
            System.out.println("Item with ID " + item.getId() + " not found in items.txt.");
            return;
        }

        writeLinesBackToFile(lines);
    }

    /**
     * Deletes an item from storage by removing it from items.txt.
     * @param item Item to be deleted
     */
    public static void deleteItem(Item item) {
        ArrayList<String> lines = readLinesIntoList(); // Read all lines from items.txt into a list

        // Find and remove the line with the matching ID
        boolean itemFound = false;
        for (int i = 0; i < lines.size(); i++) { // Loop through each line
            String currentLine = lines.get(i); // set current line to line at the index in the lines ArrayList
            if (currentLine.trim().isEmpty() || i == 0) {
                continue; // Skip empty lines and header line
            }

            String[] splitLine = currentLine.split(",");
            int currentID = Integer.parseInt(splitLine[0]);
            if (currentID == item.getId()) { // If the currentID matches the items ID, remove it from the lines array
                lines.remove(i); // Remove this line from the list
                itemFound = true;
                break;
            }
        }

        if (!itemFound) {
            System.out.println("Item with ID " + item.getId() + " not found in items.txt.");
            return;
        }

        writeLinesBackToFile(lines);
        System.out.println("Item with ID " + item.getId() + " has been deleted from items.");
    }


    /**
     * Converts an Item class into a String
     * @param item Item class
     * @return String
     */
    private static String formatItemToString(Item item) {
        return String.format("\n%s,%s,%s,%s,%s",
                item.getId(),
                item.getName(),
                item.getUnitPrice(),
                item.getQtyInStock(),
                item.getTotalValue()
        );
    }

    /**
     * Reads lines of text file and returns them as an ArrayList
     * @return ArrayList
     */
    private static ArrayList<String> readLinesIntoList() {
        ArrayList<String> lines = new ArrayList<>();
        System.out.println("File path should be: " + FileManager.ITEMS_FILE_PATH);
        try (BufferedReader reader = new BufferedReader(new FileReader(FileManager.ITEMS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading items.txt: " + e.getMessage());
            System.exit(1);
        }
        return lines;
    }

    /**
     * Writes an ArrayList of lines back to the text file
     * @param lines ArrayList
     */
    private static void writeLinesBackToFile(ArrayList<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileManager.ITEMS_FILE_PATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to items.txt: " + e.getMessage());
        }
    }
}
