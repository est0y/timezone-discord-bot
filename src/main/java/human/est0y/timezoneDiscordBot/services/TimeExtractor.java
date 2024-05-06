package human.est0y.timezoneDiscordBot.services;

import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TimeExtractor {
    public Optional<LocalTime> extract(String string) {
        String pattern = "(\\d{1,2})[.:\\s](\\d{2})";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(string);
        if (m.find()) {
            String timeString;
            if (m.group(1).length() == 1) { // Если первая цифра часа однозначная, добавляем "0" в начало
                timeString = "0" + m.group(1) + ":" + m.group(2);
            } else {
                timeString = m.group(1) + ":" + m.group(2);
            }
            return Optional.of(LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm")));
        }
        return Optional.empty();
    }
}
