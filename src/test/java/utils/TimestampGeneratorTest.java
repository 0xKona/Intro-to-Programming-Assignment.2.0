package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimestampGeneratorTest {

    @Test
    @DisplayName("getCurrentTime should generate the current time in the correct format")
    void shouldGenerateCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timeNow = now.format(formatter);

        String generatedTime = TimestampGenerator.getCurrentTime();
        Assertions.assertEquals(generatedTime, timeNow);
    }

    @Test
    @DisplayName("getCurrentDayStart should generate the start of the current day")
    void shouldGenerateCurrentDayStart() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timeNow = now.format(formatter);

        String[] timeAsArray = timeNow.split(" ");
        timeAsArray[1] = "00:00:00";
        String expectedTime = timeAsArray[0] + " " + timeAsArray[1];

        String generatedTime = TimestampGenerator.getCurrentDayStart();
        Assertions.assertEquals(expectedTime, generatedTime);
    }

    @Test
    @DisplayName("getCurrentDayEnd should generate the end of the current day")
    void shouldGenerateCurrentDayEnd() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timeNow = now.format(formatter);

        String[] timeAsArray = timeNow.split(" ");
        timeAsArray[1] = "23:59:59";

        String expectedTime = timeAsArray[0] + " " + timeAsArray[1];
        String generatedTime = TimestampGenerator.getCurrentDayEnd();
        Assertions.assertEquals(expectedTime, generatedTime);
    }
}
