package com.example.starBank.listener;

import com.example.starBank.model.Recommendation;
import com.example.starBank.model.RecommendationWithRules;
import com.example.starBank.repositories.RecommendationsRepository;
import com.example.starBank.repositories.RecommendationsRuleRepository;
import com.example.starBank.services.RecommendationService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Yuri-73
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final RecommendationsRepository recommendationsRepository;
    private final RecommendationsRuleRepository recommendationsRuleRepository;
    private final RecommendationService recommendationService;
    private final TelegramBot telegramBot;

    public TelegramBotUpdatesListener(RecommendationsRepository recommendationsRepository, RecommendationsRuleRepository recommendationsRuleRepository,
                                      RecommendationService recommendationService, TelegramBot telegramBot) {
        this.recommendationsRepository = recommendationsRepository;
        this.recommendationsRuleRepository = recommendationsRuleRepository;
        this.recommendationService = recommendationService;
        this.telegramBot = telegramBot;
    }

    /**
     * Обработчик обновлений
     */
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Метод формирования приветственного сообщения по стартовой команде «/start»)
     * @param chatId Идентификатор пользователя, зарегистрированного в боте
     * @param firstName Имя пользователя, зарегистрированного в боте
     * @param lastName Фамилия пользователя, зарегистрированного в боте
     */
    private void startMessageReceived(Long chatId, String firstName, String lastName) {
        String responseMessage = "Здравствуйте, " + firstName + " " + lastName + "! Банк Стар подберёт для Вас новые продукты!";
        sendMessage(chatId, responseMessage);  //Вызов внутреннего метода для передачи текущей задачи пользователю
    }

    /**
     * Метод получения и отправки уведомлений пользователю банка
     * @param chatId Идентификатор пользователя, зарегистрированного в боте
     * @param sendingMessage Текст уведомления для распечатки
     */
    private void sendMessage(Long chatId, String sendingMessage) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), sendingMessage);
        SendResponse sendResponse = telegramBot.execute(sendMessage);
    }

    /**
     * Связующий метод обработки вводных сообщений
     * Выдаёт предупреждения при неформатном сообщении или неправильном имени клиента банка
     * @param updates массив вводных сообщений
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            String messageReceived = update.message().text();
            logger.info("Обработка текущей задачи: {}", messageReceived);

            if (update.message().chat() == null || messageReceived.isBlank()) {
                logger.warn("Невалидное сообщение от: {}", messageReceived);
            }
            long chatId = update.message().chat().id(); //Выделение из абдейта идентификатора чата, по которому следует отправить сообщение боту

            if (Objects.equals("/start", messageReceived)) {  //Если текст команды соответствует "/start", то выводится соответствующее приветствие пользователю.
                logger.info("Стартовая команда для бота {}", chatId);
                startMessageReceived(chatId, update.message().chat().firstName(), update.message().chat().lastName()); //Вызов метода формирования приветственного сообщения с именем зарегистрированного пользователя
                return;
            }
            if (messageReceived.matches("/recommend\\s(.+)?")) {
                logger.info("Команда уведомления для бота {}", chatId);
                String[] parts = update.message().text().split(" ");
                String userName = parts[1];
                UUID id;

                if (recommendationsRepository.getBooleanUserIdByUsername(userName)) {
                    id = recommendationsRepository.getUserIdByUsername(userName);
                } else {
                    sendMessage(chatId, "Пользователь не найден");
                    logger.info("Пользователь не найден {}", chatId);
                    return;
                }
                List<RecommendationWithRules> listAll = recommendationsRuleRepository.findAll();
                List<Recommendation> list = recommendationService.getRecommendation(id, listAll);

                sendMessage(chatId, "Новые продукты для клиента банка ID = " + id + ":\n" + list.toString());
            } else {
                logger.info("Формат для {} невалидный", chatId);
                sendMessage(chatId, "Неверный формат для сообщения");
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}



