package human.est0y.timezoneDiscordBot.services.data;

import human.est0y.timezoneDiscordBot.domain.GuildSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InMemoryGuildSettingsService implements GuildSettingsService {
    
    private final Map<Long, GuildSettings> guildSettingsById = new HashMap<>();
    
    @Override
    public Optional<GuildSettings> findById(long id) {
        return Optional.ofNullable(guildSettingsById.get(id));
    }

    @Override
    public GuildSettings save(GuildSettings guildSettings) {
        return guildSettingsById.put(guildSettings.getId(), guildSettings);
    }
}
