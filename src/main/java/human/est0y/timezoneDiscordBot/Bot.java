package human.est0y.timezoneDiscordBot;

import human.est0y.timezoneDiscordBot.services.TimeExtractor;
import human.est0y.timezoneDiscordBot.services.TimePrinter;
import human.est0y.timezoneDiscordBot.services.TimeRoleFinder;
import human.est0y.timezoneDiscordBot.services.data.GuildSettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class Bot extends ListenerAdapter {

    private final TimeExtractor timeExtractor;

    private final TimeRoleFinder timeRoleFinder;

    private final GuildSettingsService guildSettingsService;

    private final TimePrinter timePrinter;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        var memberLocalTime = timeExtractor.extract(event.getMessage().getContentRaw()).orElseThrow();
        var guild = event.getGuild();
        var guildSettings = guildSettingsService.findById(guild.getIdLong()).orElseThrow();
        Role timeRole = timeRoleFinder.getTimeRole(event.getMember(), guildSettings);
        var userTimeZone = guildSettings.getZoneIdByRoleIds().get(timeRole.getIdLong());
        var userZonedDateTime = ZonedDateTime.of(LocalDate.now(), memberLocalTime, ZoneId.of(userTimeZone));
        timePrinter.print(event.getMessage(), userZonedDateTime, guildSettings);
    }
}
