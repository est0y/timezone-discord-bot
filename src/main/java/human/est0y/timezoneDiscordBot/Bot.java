package human.est0y.timezoneDiscordBot;

import human.est0y.timezoneDiscordBot.domain.GuildSettings;
import human.est0y.timezoneDiscordBot.services.TimeExtractor;
import human.est0y.timezoneDiscordBot.services.TimePrinter;
import human.est0y.timezoneDiscordBot.services.TimeRoleFinder;
import human.est0y.timezoneDiscordBot.services.commands.CommandManager;
import human.est0y.timezoneDiscordBot.services.data.GuildSettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class Bot extends ListenerAdapter {

    private final TimeExtractor timeExtractor;

    private final TimeRoleFinder timeRoleFinder;

    private final GuildSettingsService guildSettingsService;

    private final TimePrinter timePrinter;

    private final CommandManager commandManager;

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        var guilds = event.getJDA().getGuilds();
        if (guildSettingsService.findById(guilds.get(0).getIdLong()).isEmpty()) {
            guilds.forEach(g -> guildSettingsService.save(new GuildSettings(g.getIdLong(), new HashMap<>())));
        }
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        guildSettingsService.save(new GuildSettings(event.getGuild().getIdLong(), new HashMap<>()));
    }


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

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        commandManager.getCommandByName(event.getName()).execute(event);
    }

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        if (!event.getComponentId().startsWith("menu:city")) {
            return;
        }
        event.getMessage().delete().queue();
        long timeRole = Long.parseLong(event.getComponentId().replace("menu:city|roleId:", ""));
        var roleName = event.getJDA().getRoleById(timeRole).getName();
        var timeZone = event.getValues().get(0);
        var guildSetting = guildSettingsService.findById(event.getGuild().getIdLong()).orElseThrow();
        guildSetting.getZoneIdByRoleIds().put(timeRole, timeZone);
        event.reply(String.format("Added role @%s with time zones %s", roleName, timeZone))
                .queue(h -> guildSettingsService.save(guildSetting));

    }
}
