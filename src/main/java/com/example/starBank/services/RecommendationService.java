package com.example.starBank.services;

import com.example.starBank.model.Recommendation;
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
            listOfRecommendation.add(new Recommendation(0,"Рекомендуемых продуктов нет", "-"));
        }
        return listOfRecommendation;
    }
}
