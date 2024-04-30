package human.est0y.timezoneDiscordBot.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringSimilarityTest {

    @Test
    void calculateSimilarity() {
        var stringSimilarity = new StringSimilarity();
        var result = stringSimilarity.calculateSimilarity("vbbpolanddsdv", "poland");
        Assertions.assertEquals(1.0,result);
        result = stringSimilarity.calculateSimilarity("vbbpoldsdv", "poland");
        Assertions.assertEquals(0.5,result);
    }
}