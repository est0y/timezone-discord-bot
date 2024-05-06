package human.est0y.timezoneDiscordBot.services;

import human.est0y.timezoneDiscordBot.domain.GuildSettings;
import net.dv8tion.jda.api.entities.Message;

import java.time.ZonedDateTime;

public interface TimePrinter {
    void print(Message message, ZonedDateTime userZonedDateTime, GuildSettings guildSettings);
}
