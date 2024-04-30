package human.est0y.timezoneDiscordBot.services.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface SlashCommand {

    void execute(SlashCommandInteractionEvent event);

    String getCommandId();
}