package utils;

import storage.FileManager;

import java.io.*;
import java.util.ArrayList;

/**
 * IDGenerator class to generate unique 5-digit IDs
 * <p>Public Method: </p>
 * <li>{@link #generateNewID()}</li>
 */
public class IDGenerator {

    private static ArrayList<Integer> inUseIDs = new ArrayList<>();

    // This is to make testing with a set of IDs possible and is not used in the program code itself.
    public static void setInUseIDs(ArrayList<Integer> ids) {
        inUseIDs = ids;
    }

    /**
     * Public method for generating a new ID
     * @return 5 Digit int
     */
    public static int generateNewID() {
        readItemIDs();
        readTransactionIDs();

        /* Cannot store leading zeroes in an int so start from the lowest possible, another method would be to store
        the ID as a string, to allow for an ID of 00001, I will attempt this in Part 2 to resolve this shortcoming.
         */
        int generatedID = 10000;

        final int maxPossible = 99999;

        // Increments the generatedID until it finds an integer that is not in the inUse array
        for (int i = generatedID; i < maxPossible; i++ ) {
            if (!inUseIDs.contains(generatedID)) {
                break; // If the generatedID is not in use then break the loop and continue;
            }
            generatedID++; // Else increment the generatedID and continue loop
        }

        if (generatedID > maxPossible) {
            System.out.println("Error: Maximum ID limit reached.");
            /* The assignment specifically asked for a 5-Digit ID which would cause this to eventually happen. In a real
            world project I would use something like a UUID (Unique Universal Identifier) which would remove the
            possibility of running out of ID's, I also wouldn't have to check the exising IDs to make sure I wasn't
            duplicating an ID since each UUID is unique and the chances of getting a duplicate is astronomically
            small */
            return -1; // Indicate that ID generation failed.
        }
        inUseIDs.add(generatedID); // Add to in use ID's
        return generatedID; // Return the ID
    }

    /**
     * Reads ItemID's and Adds them to the inUseIDs Array
     */
    static void readItemIDs() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FileManager.ITEMS_FILE_PATH))) {
            String line;
            // Skip the header line
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] splitLine = line.split(",");
                int id = Integer.parseInt(splitLine[0]);
                if (!inUseIDs.contains(id)) {
                    inUseIDs.add(id);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading items.txt: " + e.getMessage());
        }
    }

    /**
     * Reads TransactionIDs and adds them to the inUseIDs Array
     */
    static void readTransactionIDs() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FileManager.TRANSACTIONS_FILE_PATH))) {
            String line;
            // Skip the header line
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] splitLine = line.split(",");
                int id = Integer.parseInt(splitLine[0]);
                if (!inUseIDs.contains(id)) {
                    inUseIDs.add(id);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading transactions.txt: " + e.getMessage());
        }
    }
}
