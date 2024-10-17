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

    private static final ArrayList<Integer> inUseIDs = new ArrayList<>();

    /**
     * Public method for generating a new ID
     * @return 5 Digit int
     */
    public static int generateNewID() {
        readItemIDs();
        readTransactionIDs();

        int maxID = 10000; // Start from 10000 to ensure 5-digit IDs, TODO : This could be improved to do it from 00000

        /* I loop through the inUseIds and if it is bigger or equal to maxID we set it to the ID + 1, this means we only
        have to loop through the in use IDs, the alternative approach would be to loop through EVERY number starting from
        10000 and check if it is in use, however this would be far more expensive, the downside with my method is that it
        is possible that some IDs are not able to be re-used, since the maxID will always be the biggest inUse int + 1
         */
        for (int id : inUseIDs) {
            if (id >= maxID) {
                maxID = id + 1;
            }
        }
        if (maxID > 99999) {
            System.out.println("Error: Maximum ID limit reached.");
            /* The assignment specifically asked for a 5 Digit ID which would cause this to eventually happen
               in a real world project I would use something like a UUID (Unique Universal Identifier) which would remove
               the possibility of running out of ID's, I also wouldn't have to check the exising IDs to make sure I
               wasn't duplicating an ID since each UUID is unique and the chances of getting a duplicate is astronomically
               small (1 in a billion which for this project is fine, however some larger corporate projects may need more
               certainty).
             */
            return -1; // Indicate that ID generation failed
        }
        inUseIDs.add(maxID);
        return maxID;
    }

    /**
     * Reads ItemID's and Adds them to the inUseIDs Array
     */
    private static void readItemIDs() {
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
    private static void readTransactionIDs() {
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
