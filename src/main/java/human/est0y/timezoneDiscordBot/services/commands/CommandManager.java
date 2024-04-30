package human.est0y.timezoneDiscordBot.services.commands;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class CommandManager {
    private final Map<String, SlashCommand> commandsMap;

    public CommandManager(List<SlashCommand> slashCommands) {
        this.commandsMap = slashCommands.stream().collect(Collectors.toMap(SlashCommand::getCommandId, s -> s));
    }

    public SlashCommand getCommandByName(String name) {
        return commandsMap.get(name);
    }
}