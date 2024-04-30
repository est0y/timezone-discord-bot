package human.est0y.timezoneDiscordBot.services;

import org.springframework.stereotype.Service;

@Service
public class StringSimilarity {

    public double calculateSimilarity(String str1, String str2) {
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();
        String lowerString = str1.length() > str2.length() ? str2 : str1;
        String higherString = str1.length() > str2.length() ? str1 : str2;
        int lowerLength = lowerString.length();
        int higherLength = higherString.length();
        int maxMatchesInRow = 0;
        for (int i = 0, matchesInRow = 0; i < higherLength; i++) {
            int localI = i;
            for (int j = 0; j < lowerLength; j++) {
                if (lowerString.charAt(j) == higherString.charAt(localI)) {
                    localI++;
                    matchesInRow++;
                    if (localI >= higherLength - 1) {
                        maxMatchesInRow = Math.max(maxMatchesInRow, matchesInRow);
                        matchesInRow = 0;
                        break;
                    }
                } else {
                    maxMatchesInRow = Math.max(maxMatchesInRow, matchesInRow);
                    matchesInRow = 0;
                    break;
                }
            }
        }
        return (double) maxMatchesInRow / (double) lowerLength;
    }
}
