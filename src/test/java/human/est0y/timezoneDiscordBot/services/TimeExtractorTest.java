package human.est0y.timezoneDiscordBot.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Optional;

class TimeExtractorTest {

    @Test
    void extract() {
        var timeExtractor = new TimeExtractor();
        var optionalTime = timeExtractor.extract("any12:14any");
        Assertions.assertEquals(LocalTime.of(12, 14), optionalTime.orElseThrow());

        optionalTime = timeExtractor.extract("any 17:18 any");
        Assertions.assertEquals(LocalTime.of(17, 18), optionalTime.orElseThrow());

        optionalTime = timeExtractor.extract("any any");
        Assertions.assertEquals(Optional.empty(), optionalTime);

        optionalTime = timeExtractor.extract("any 1:18 any");
        Assertions.assertEquals(LocalTime.of(1, 18), optionalTime.orElseThrow());

        optionalTime = timeExtractor.extract("any1 18any");
        Assertions.assertEquals(LocalTime.of(1, 18), optionalTime.orElseThrow());

        optionalTime = timeExtractor.extract("any1.18any");
        Assertions.assertEquals(LocalTime.of(1, 18), optionalTime.orElseThrow());

        optionalTime = timeExtractor.extract("any 12 18 any");
        Assertions.assertEquals(LocalTime.of(12, 18), optionalTime.orElseThrow());

        optionalTime = timeExtractor.extract("any12 18any");
        Assertions.assertEquals(LocalTime.of(12, 18), optionalTime.orElseThrow());

        optionalTime = timeExtractor.extract("any12.18any");
        Assertions.assertEquals(LocalTime.of(12, 18), optionalTime.orElseThrow());

        optionalTime = timeExtractor.extract("any 12.18any");
        Assertions.assertEquals(LocalTime.of(12, 18), optionalTime.orElseThrow());
    }
}