package human.est0y.timezoneDiscordBot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class GuildSettings {

    private final long id;

    private final Map<Long, String> zoneIdByRoleIds;

}
