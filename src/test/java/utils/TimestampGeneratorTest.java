package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimestampGeneratorTest {

    @Test
    @DisplayName("Timestamp Generate should generate the current time")
    public void shouldGenerateCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timeNow = now.format(formatter);

        String generatedTime = TimestampGenerator.getCurrentTime();
        Assertions.assertEquals(generatedTime, timeNow);
    }
}
