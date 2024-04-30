package human.est0y.timezoneDiscordBot.config;

import human.est0y.timezoneDiscordBot.Bot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class JdaConfig {

    @Bean
    public JDA jda(@Value("${app.bot.token}") String token, Bot bot) {
        var jda = JDABuilder.createDefault(token)
                .addEventListeners(bot)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
        jda.upsertCommand("addtimerole", "add time role")
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions. DISABLED)
                .addOption(OptionType.ROLE,"timerole","time role",true)
                .addOption(OptionType.STRING,"timezone","timezone",true)
                .queue();
        jda.upsertCommand("deletetimerole", "delete time role")
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions. DISABLED)
                .addOption(OptionType.ROLE,"timerole","time role",true)
                .queue();
        jda.upsertCommand("time", "now time")
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.ENABLED)
                .queue();

        return jda;
    }
}