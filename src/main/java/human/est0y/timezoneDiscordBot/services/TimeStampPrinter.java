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
@Qualifier("TimeStampPrinter")
public class TimeStampPrinter implements TimePrinter {

    @Override
    public void print(Message message, ZonedDateTime userZonedDateTime, GuildSettings guildSettings) {
        var memberName = message.getMember().getEffectiveName();
        var userLocalTime = userZonedDateTime.toLocalTime();
        var timeStamp = "<t:" + userZonedDateTime.toEpochSecond() + ":t>";
        var messageContent = String.format("%s mentions the time as %s \nyour time: %s",
                memberName,
                userLocalTime,
                timeStamp
        );
        message.reply(MessageCreateData.fromContent(messageContent)).queue();
    }
}
