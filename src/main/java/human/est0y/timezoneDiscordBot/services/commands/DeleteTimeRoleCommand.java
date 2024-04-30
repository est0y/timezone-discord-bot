package human.est0y.timezoneDiscordBot.services.commands;

import human.est0y.timezoneDiscordBot.services.data.GuildSettingsService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteTimeRoleCommand implements SlashCommand {

    private final GuildSettingsService guildSettingsService;

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        var role = event.getOption("timerole").getAsRole();
        var guildSettings = guildSettingsService.findById(event.getGuild().getIdLong()).orElseThrow();
        guildSettings.getZoneIdByRoleIds().remove(role.getIdLong());
        event.reply("Removed").queue(h -> guildSettingsService.save(guildSettings));
    }

    @Override
    public String getCommandId() {
        return "deletetimerole";
    }
}
