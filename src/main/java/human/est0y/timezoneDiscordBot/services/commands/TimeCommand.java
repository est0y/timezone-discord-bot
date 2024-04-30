package human.est0y.timezoneDiscordBot.services.commands;

import human.est0y.timezoneDiscordBot.services.TimeZonesExtractor;
import human.est0y.timezoneDiscordBot.services.data.GuildSettingsService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class TimeCommand implements SlashCommand {

    private final GuildSettingsService guildSettingsService;

    private final TimeZonesExtractor timeZonesExtractor;

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        var stringBuilder = new StringBuilder("Now time:\n");
        var guildSettings = guildSettingsService.findById(event.getGuild().getIdLong()).orElseThrow();
        var timezones = timeZonesExtractor.getTimeZones(event.getJDA(), ZonedDateTime.now(), guildSettings);
        if (timezones.isEmpty()) {
            event.reply("There are no time zones. (To add, use the `/addtimerole` command)").queue();
        } else {
            timezones.forEach(tz -> stringBuilder.append(tz).append("\n"));
            event.reply(MessageCreateData.fromContent(stringBuilder.toString())).queue();
        }
    }

    @Override
    public String getCommandId() {
        return "time";
    }
}
