package human.est0y.timezoneDiscordBot.services.data;

import human.est0y.timezoneDiscordBot.domain.GuildSettings;

import java.util.Optional;

public interface GuildSettingsService {

    Optional<GuildSettings> findById(long id);

    GuildSettings save(GuildSettings guildSettings);
}
