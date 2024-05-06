package human.est0y.timezoneDiscordBot.services;

import human.est0y.timezoneDiscordBot.domain.GuildSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
@Qualifier("TimePrinter")
public class TimePrinterImpl implements TimePrinter {

    private final TimeZonesExtractor timeZonesExtractor;

    @Override
    public void print(Message message, ZonedDateTime userZonedDateTime, GuildSettings guildSettings) {
        var memberName = message.getMember().getEffectiveName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(memberName)
                .append(" mentions the time as ")
                .append(userZonedDateTime.toLocalTime())
                .append("\n");
        var jda = message.getJDA();

        timeZonesExtractor.getTimeZones(jda, userZonedDateTime, guildSettings)
                .forEach(tz -> stringBuilder.append(tz).append("\n"));
        message.reply(MessageCreateData.fromContent(stringBuilder.toString())).queue();
    }
}
