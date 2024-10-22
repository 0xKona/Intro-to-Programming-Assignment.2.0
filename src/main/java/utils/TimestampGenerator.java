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
     * @return String (yyyy-MM-dd HH:mm:ss)
     */
    public static String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    /**
     * Returns the timestamp for the start of today's date
     * @return String (yyyy-MM-dd HH:mm:ss)
     */
    public static String getCurrentDayStart() {
        LocalDateTime now = LocalDateTime.now().toLocalDate().atStartOfDay();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startOfDay = now.format(formatter);
        return startOfDay;
    }

    /**
     * Returns the timestamp for the end of today's date
     * @return String (yyyy-MM-dd HH:mm:ss)
     */
    public static String getCurrentDayEnd() {
        LocalDateTime now = LocalDateTime.now().toLocalDate().atTime(23, 59, 59);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String endOfDay = now.format(formatter);
        return endOfDay;
    }
}
