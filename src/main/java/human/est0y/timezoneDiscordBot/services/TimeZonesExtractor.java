package human.est0y.timezoneDiscordBot.services;

import human.est0y.timezoneDiscordBot.domain.GuildSettings;
import net.dv8tion.jda.api.JDA;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeZonesExtractor {
    public List<String> getTimeZones(JDA jda, ZonedDateTime time, GuildSettings guildSettings) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        List<String> result = new ArrayList<>();
        guildSettings.getZoneIdByRoleIds().keySet().stream().map(jda::getRoleById).forEach(role -> {
            String timeZoneId = guildSettings.getZoneIdByRoleIds().get(role.getIdLong());
            String hereTime = formatter.format(time.withZoneSameInstant(ZoneId.of(timeZoneId)));
            result.add(String.format("%s: %s", role.getName(), hereTime));
        });
        return result;
    }
}
