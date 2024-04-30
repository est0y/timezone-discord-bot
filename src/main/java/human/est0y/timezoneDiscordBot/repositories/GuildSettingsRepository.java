package human.est0y.timezoneDiscordBot.repositories;

import human.est0y.timezoneDiscordBot.domain.GuildSettings;

import java.util.Optional;

public interface GuildSettingsRepository {

    Optional<GuildSettings> findById(long id);

    GuildSettings save(GuildSettings guildSettings);
}
