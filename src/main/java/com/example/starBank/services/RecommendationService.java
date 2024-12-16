package com.example.starBank.services;

import com.example.starBank.exceptions.InvalidAmountOfArgumentsException;
import com.example.starBank.model.Recommendation;
import com.example.starBank.model.RecommendationWithRules;
import com.example.starBank.model.RuleRequirements;
import com.example.starBank.recommendation_rules.Invest500;
import com.example.starBank.recommendation_rules.RecommendationRuleSet;
import com.example.starBank.recommendation_rules.SimpleCredit;
import com.example.starBank.recommendation_rules.TopSaving;
import com.example.starBank.repositories.RecommendationCounterRepository;
import com.example.starBank.repositories.RecommendationsRepository;
import com.example.starBank.repositories.RecommendationsRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RecommendationService {
    private final RecommendationsRepository recommendationsRepository;
    private final RecommendationRuleSet product1;
    private final RecommendationRuleSet product2;
    private final RecommendationRuleSet product3;

    private final RecommendationCounterRepository counterRepository;


    public RecommendationService(RecommendationsRepository recommendationsRepository, Invest500 product1, TopSaving product2, SimpleCredit product3, RecommendationCounterRepository counterRepository) {
        this.recommendationsRepository = recommendationsRepository;
        this.product1 = product1;
        this.product2 = product2;
        this.product3 = product3;
        this.counterRepository = counterRepository;

    }

    /**
     * Метод получения значения amount из БД через репозиторий
     * @param id id клиента банка
     * @return Возвращает 4-значное число amount или 0
     */
    @Autowired
    RecommendationsRuleRepository recommendationsRuleRepository;
    public int get(UUID id) {
        return recommendationsRepository.getRandomTransactionAmount(id);
    }

    /**
     * Метод формирования списка рекомендаций
     * @param id id клиента банка
     * @return Возвращает коллекцию рекомендаций
     */

    public List<Recommendation> getRecommendation(UUID id) {
        List<Recommendation> listOfRecommendation = new ArrayList<>();
        Recommendation recommendation1 = product1.getRecommendationByRule(id).orElse(null);
        Recommendation recommendation2 = product2.getRecommendationByRule(id).orElse(null);
        Recommendation recommendation3 = product3.getRecommendationByRule(id).orElse(null);
        if (recommendation1 != null) {
            listOfRecommendation.add(recommendation1);
        }
        if (recommendation2 != null) {
            listOfRecommendation.add(recommendation2);
        }
        if (recommendation3 != null) {
            listOfRecommendation.add(recommendation3);
        }
        if (listOfRecommendation.size() == 0) {
            listOfRecommendation.add(new Recommendation(null, "Рекомендуемых продуктов нет", "-"));
        }
        return listOfRecommendation;
    }

    /**
     * Метод формирования списка рекоммендаций для клиента
     * @param id идентификатор клиента
     * @param recommendationsWithRules лист рекоммендаций в БД
     * @return Возвращает список полученных рекомендаций
     */
//    @Cacheable("RecommendationWithRules")
    public List<Recommendation> getRecommendation(UUID id, List<RecommendationWithRules> recommendationsWithRules) {
        List<Recommendation> listOfRecommendation = new ArrayList<>();
        for (RecommendationWithRules r : recommendationsWithRules) {
            if (recommendationAppliance(id, r.getRuleRequirements())) {
                listOfRecommendation.add(new Recommendation(r.getProductId(), r.getName(), r.getText()));
                counterRepository.findByRecommendationWithRulesIdAndIncrementCounter(r.getId());
            }
        }
        if (listOfRecommendation.isEmpty()) {
            listOfRecommendation.add(new Recommendation(null, "Рекомендуемых продуктов нет", "-"));
        }
        return listOfRecommendation;
    }

    /**
     * Приватный метод для перебора правил из массива и передачу его в метод ruleSwitch, где правило проверяется на выполнение условий.
     * @param id идентификатор клиента
     * @param rules лист динамических правил
     * @return возвращает true при положительном запросе динамического правила
     */
    private Boolean recommendationAppliance(UUID id, List<RuleRequirements> rules) {
        boolean tmp = false;
        for (RuleRequirements rule : rules) {
            if (rule.isNegate()) {
                tmp = !ruleSwitch(id, rule);
            } else {
                tmp = ruleSwitch(id, rule);
            }
            if (!tmp) {
                return tmp;
            }
        }
        return tmp;
    }

    /**
     * Проверяется значение query из модели правил для вызова соответствующего ему запроса из репозитория
     * @param id идентификатор клиента
     * @param rule динамическое правило
     * @throws InvalidAmountOfArgumentsException Неверный формат аргумента
     * @return возвращает общее true при true всех входящих правил в динамическое правило
     */
    private Boolean ruleSwitch(UUID id, RuleRequirements rule) {
        System.out.println("RuleRequirements rule - " + rule);
        switch (rule.getQuery()) {
            case "USER_OF" -> {
                if (rule.getArguments().split(",").length != 1) {
                    throw new InvalidAmountOfArgumentsException(rule);
                }
                return recommendationsRepository.getUserOfResult(id, rule);
            }
            case "ACTIVE_USER_OF" -> {
                if (rule.getArguments().split(",").length != 1) {
                    throw new InvalidAmountOfArgumentsException(rule);
                }
                return recommendationsRepository.getActiveUserOfResult(id, rule);
            }
            case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW" -> {
                if (rule.getArguments().split(",").length != 2) {
                    throw new InvalidAmountOfArgumentsException(rule);
                }
                return recommendationsRepository.getTransactionSumCompareDepositWithDrawResult(id, rule);
            }
            case "TRANSACTION_SUM_COMPARE" -> {
                if (rule.getArguments().split(",").length != 4) {
                    throw new InvalidAmountOfArgumentsException(rule);
                }
                return recommendationsRepository.getTransactionSumCompareResult(id, rule);
            }
            default -> {
                return false;
            }
        }
    }
}
