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
import java.util.UUID;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    public static final String START = "/start";
    public static final String RECOMMEND = "/recommend sheron.berge";

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
     */
    private void startMessageReceived(Long chatId, String userName) {
        String responseMessage = "Здравствуйте, " + userName + " Банк Стар подберёт новые продукты для вас!";
        sendMessage(chatId, responseMessage);  //Вызов внутреннего метода для передачи в итоге текущей задачи пользователю
    }

    /**
     * Метод получения и отправки уведомлений пользователю банка
     */
    private void sendMessage(Long chatId, String sendingMessage) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), sendingMessage);
        SendResponse sendResponse = telegramBot.execute(sendMessage);
    }

    /**
     * Связующий метод обработки сообщений
     */
    @Override
    public int process(List<Update> updates) {
       updates.forEach(update -> {
            String messageReceived = update.message().text();
            System.out.println("messageReceived - " + messageReceived);
            logger.info("Обработка текущей задачи: {}", messageReceived);

            if (update.message().chat() == null || update.message().text().isBlank()) {
                logger.warn("Невалидное сообщение от: {}", messageReceived);
            }
            long chatId = update.message().chat().id(); //Выделение из абдейта идентификатора чата, по которому следует отправить сообщение боту

            if ((START).equals(messageReceived)) {  //Если текст команды соответствует "/start", то выводится соответствующее приветствие пользователю.
                logger.info("Стартовая команда для бота {}", chatId);
                startMessageReceived(chatId, update.message().chat().firstName()); //Вызов метода формирования приветственного сообщения с именем зарегистрированного пользователя
                return;
            }
            if (messageReceived.startsWith("/recommend")) {
                logger.info("Команда уведомления для бота {}", chatId);

                String[] parts = update.message().text().split(" ");
                String userName;
                UUID id = null;
                if (parts[0].equals("/recommend")) {
                    userName = parts[1];
                    System.out.println(userName);
                    id = recommendationsRepository.getUserIdByUsername(userName);
                    System.out.println(userName);
                    System.out.println(id);
                }
                List<RecommendationWithRules> listAll = recommendationsRuleRepository.findAll();
                List<Recommendation> list = recommendationService.getRecommendation(id, listAll);

                sendMessage(chatId, "Рекомендации для клиента банка ID = " + id + ":\n" + list.toString());
            } else {
                logger.info("Формат для {} невалидный", chatId);
                sendMessage(chatId, "Неверный формат для сообщения");
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}



