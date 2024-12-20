package com.example.starBank.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.DeleteMyCommands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Yuri-73
 */
@Configuration
public class TelegramBotConfiguration {
    @Value("${telegram.bot.token}")
    private String token;
    @Bean
    public TelegramBot telegramBot() {
        /**
         * Создание бота путем передачи токена, полученного от @BotFather
         */
        TelegramBot bot = new TelegramBot(token);
        bot.execute(new DeleteMyCommands());
        return bot;
    }
}
