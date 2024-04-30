package human.est0y.timezoneDiscordBot.services;

import human.est0y.timezoneDiscordBot.domain.GuildSettings;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TimeRoleFinder {
    public Role getTimeRole(Member member, GuildSettings guildSettings) {
        Map<Long, Role> userRoles = member.getRoles().stream().collect(Collectors.toMap(ISnowflake::getIdLong, r -> r));
        var matchingRolesId = guildSettings.getZoneIdByRoleIds().keySet().stream()
                .filter(userRoles::containsKey).toList();
        if (matchingRolesId.size() != 1) {
            throw new IllegalStateException();
        }
        return userRoles.get(matchingRolesId.get(0));
    }
}
