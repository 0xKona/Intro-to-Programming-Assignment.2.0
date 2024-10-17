package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles creating a timestamp at the current time
 * <p>Method: </p>
 * <li>{@link #getCurrentTime()}</li>
 */
public class TimestampGenerator {

    /**
     * Returns the timestamp for the current time
     * @return String
     */
    public static String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}
