package com.example.starBank.recommendation_rules;

import com.example.starBank.model.Recommendation;
import com.example.starBank.repositories.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Chowo
 */
@Component
public class SimpleCredit implements RecommendationRuleSet{
    private final RecommendationsRepository repository;
    private final Recommendation recommendation;

    public SimpleCredit(RecommendationsRepository repository) {
        recommendation = new Recommendation(UUID.randomUUID(),"Простой кредит", "Откройте мир выгодных кредитов с нами!\n" +
                "\n" +
                "Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.\n" +
                "\n" +
                "Почему выбирают нас:\n" +
                "\n" +
                "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов.\n" +
                "\n" +
                "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.\n" +
                "\n" +
                "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое.\n" +
                "\n" +
                "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!");
        this.repository = repository;
    }

    /**
     * Метод формирования рекомендации по условию полученных значений из БД Н2
     * @param id Идентификатор клиента банка
     * @return Возвращает штатную рекомендацию или пустое значение
     */
    @Override
    public Optional<Recommendation> getRecommendationByRule(UUID id) {
        int creditCount = repository.getCreditCount(id);
        int debitDepositAmount = repository.getDebitDepositAmount(id);
        int debitWithdrawAmount = repository.getDebitWithdrawAmount(id);
        if (creditCount < 1 && debitDepositAmount > debitWithdrawAmount && debitWithdrawAmount > 100000) {
            return Optional.of(recommendation);
        }
        return Optional.empty();
    }

}
