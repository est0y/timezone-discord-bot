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
        String pattern = "\\d{2}:\\d{2}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(string);
        if (m.find()) {
            String timeStringWithSmile = m.group();
            return Optional.of(LocalTime.parse(timeStringWithSmile, DateTimeFormatter.ofPattern("HH:mm")));
        }
        return Optional.empty();
    }
}
