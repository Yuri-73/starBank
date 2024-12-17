package com.example.starBank.constants;

import com.example.starBank.model.RecommendationWithRules;
import com.example.starBank.model.RuleRequirements;

import java.util.UUID;

/**
 * @author Chowo
 */
public class RecommendationRuleConstants {

    public static final RecommendationWithRules SIMPLECREDIT = new RecommendationWithRules("Простой кредит",
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
            UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"));


    public static final RuleRequirements USER_OF = new RuleRequirements(1L, "USER_OF", "DEPOSIT", true, SIMPLECREDIT);
    public static final RuleRequirements TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW = new RuleRequirements(2L, "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW", "DEPOSIT,>", false, SIMPLECREDIT);
    public static final RuleRequirements TRANSACTION_SUM_COMPARE = new RuleRequirements(3L, "TRANSACTION_SUM_COMPARE", "DEBIT,DEPOSIT,>,100000", false, SIMPLECREDIT);

}
