package com.example.starBank.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.DeleteMyCommands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Класс-конфигуратор для создания бина telegramBot
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
        bot.execute(new DeleteMyCommands());  //Что делает new DeleteMyCommands()?   *?*
        return bot;
    }
}
