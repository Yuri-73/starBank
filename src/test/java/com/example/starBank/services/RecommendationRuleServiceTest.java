package com.example.starBank.services;

import com.example.starBank.model.RecommendationCounter;
import com.example.starBank.model.RecommendationWithRules;
import com.example.starBank.repositories.RecommendationsRuleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static com.example.starBank.constants.RecommendationRuleConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Chowo
 */
@ExtendWith(MockitoExtension.class)
public class RecommendationRuleServiceTest {
    @Mock
    private RecommendationsRuleRepository repository;

    @InjectMocks
    private RecommendationRuleService out;

    @Test
    void createRecommendationRulesTest() {
        when(repository.save(any())).thenReturn(SIMPLECREDIT);

        RecommendationWithRules expectedRecommendation = new RecommendationWithRules(1L, "Простой кредит",
                "Откройте мир выгодных кредитов с нами!\n" +
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
                        "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!",
                UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"), List.of(USER_OF, TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW, TRANSACTION_SUM_COMPARE), new RecommendationCounter());

        assertEquals(expectedRecommendation, out.createRecommendationRules(expectedRecommendation));

    }

    @Test
    void getAllRecommendationWithRulesTest() {
        when(repository.findAll()).thenReturn(List.of(SIMPLECREDIT));

        RecommendationWithRules expectedRecommendation = new RecommendationWithRules(1L, "Простой кредит",
                "Откройте мир выгодных кредитов с нами!\n" +
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
                        "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!",
                UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"), List.of(USER_OF, TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW, TRANSACTION_SUM_COMPARE), new RecommendationCounter());

        assertEquals(List.of(expectedRecommendation), out.getAllRecommendationWithRules());
    }

    @Test
    void deleteByIDTest() {
        when(repository.existsById(ArgumentMatchers.any(Long.class))).thenReturn(true);

        assertTrue(out.deleteById(1L));

    }


}
