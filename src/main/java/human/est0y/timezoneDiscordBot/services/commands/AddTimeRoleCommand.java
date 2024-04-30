package human.est0y.timezoneDiscordBot.services.commands;

import human.est0y.timezoneDiscordBot.services.StringSimilarity;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.springframework.stereotype.Service;

import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class AddTimeRoleCommand implements SlashCommand {

    private final StringSimilarity stringSimilarity;

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        var timerole = event.getOption("timerole").getAsRole();
        var timezone = event.getOption("timezone").getAsString();
        var list = ZoneId.getAvailableZoneIds().stream().filter(zoneId -> stringSimilarity
                .calculateSimilarity(zoneId, timezone) >= 0.80).toList();
        var menuBuilder = StringSelectMenu.create("menu:city|roleId:" + timerole.getIdLong());
        int count = 0;
        for (String tz : list) {
            if (count >= 25) {
                break;
            }
            menuBuilder.addOption(tz, tz);
            count++;
        }
        if (menuBuilder.getOptions().isEmpty()) {
            event.reply("Not found. Try entering a country (like Poland) or city (like Chicago).").queue();
        } else {
            event.reply("Please pick your timezone")
                    .setEphemeral(true)
                    .addActionRow(menuBuilder.build()).queue();
        }
    }

    @Override
    public String getCommandId() {
        return "addtimerole";
    }
}
