package com.example.starBank.services;

import com.example.starBank.model.Recommendation;
import com.example.starBank.model.RecommendationWithRules;
import com.example.starBank.model.RuleRequirements;
import com.example.starBank.recommendation_rules.Invest500;
import com.example.starBank.recommendation_rules.RecommendationRuleSet;
import com.example.starBank.recommendation_rules.SimpleCredit;
import com.example.starBank.recommendation_rules.TopSaving;
import com.example.starBank.repositories.RecommendationsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RecommendationService {
    private final RecommendationsRepository recommendationsRepository;
    private final RecommendationRuleSet product1;
    private final RecommendationRuleSet product2;
    private final RecommendationRuleSet product3;

    public RecommendationService(RecommendationsRepository recommendationsRepository, Invest500 product1, TopSaving product2, SimpleCredit product3) {
        this.recommendationsRepository = recommendationsRepository;
        this.product1 = product1;
        this.product2 = product2;
        this.product3 = product3;
    }
    /**
     * Метод получения значения amount из БД через репозиторий
     * @param id для поиска по id клиента банка
     * @return Возвращает 4-значное число amount или 0
     */
    public int get(UUID id) {
        return recommendationsRepository.getRandomTransactionAmount(id);
    }

    /**
     * Метод формирования списка рекомендаций
     * @param id для поиска по id клиента банка
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
            listOfRecommendation.add(new Recommendation(null,"Рекомендуемых продуктов нет", "-"));
        }
        return listOfRecommendation;
    }

    /*Публичный метод для создания листа рекомендаций (пока пустого), прохода по листу
        объектов класса RecommendationWithRules и вызова приватного метода recommendationAppliance
        */
    public List<Recommendation> getRecommendation(UUID id, List<RecommendationWithRules> recommendationsWithRules) {
        List<Recommendation> listOfRecommendation = new ArrayList<>();
        for (RecommendationWithRules r : recommendationsWithRules) {
            if (recommendationAppliance(id, r.getRuleRequirements())) {
                listOfRecommendation.add(new Recommendation(r.getProductId(), r.getName(), r.getText()));
            }
        }
        if (listOfRecommendation.isEmpty()) {
            listOfRecommendation.add(new Recommendation(null,"Рекомендуемых продуктов нет", "-"));
        }
        return listOfRecommendation;
    }

    /*Приватный метод для перебора правил из массива и передачу его в метод ruleSwitch, где
    правило проверяется на выполнение условий.
    Так же в случае isNegate = true, возвращаемый boolean будет изменен на противоположное значение.
    В случае если правило не выполнено, следующее правило проверяться не будет и будет возвращено
    значение false.
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
                return false;
            }
        }
        return tmp;
    }


    /*Приватный метод со switch. Здесь мы проверяем значение query из класса Rule и вызываем
    соответствующий ему case, где вызывается метод из репозитория.
    В случае отсутствия нужного case будет возвращено значение false
    */
    private Boolean ruleSwitch(UUID id, RuleRequirements rule) {
        System.out.println("RuleRequirements rule - " +rule);
        switch (rule.getQuery()) {
            case "USER_OF" -> {
                return recommendationsRepository.getUserOfResult(id, rule);
            }
            case "ACTIVE_USER_OF" -> {
                return recommendationsRepository.getActiveUserOfResult(id, rule);
            }
            case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW" -> {
                return recommendationsRepository.getTransactionSumCompareDepositWithDrawResult(id, rule);
            }
            case "TRANSACTION_SUM_COMPARE" -> {
                return recommendationsRepository.getTransactionSumCompareResult(id, rule);
            }
            default ->
            {
                return false;
            }
        }
    }
}
