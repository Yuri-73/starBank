package com.example.starBank.services;

import com.example.starBank.model.RecommendationCounter;
import com.example.starBank.model.RecommendationWithRules;
import com.example.starBank.repositories.RecommendationsRuleRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yuri-73
 */
@Service
public class RecommendationRuleService {

    private final RecommendationsRuleRepository recommendationsRuleRepository;

    public RecommendationRuleService(RecommendationsRuleRepository recommendationsRuleRepository) {
        this.recommendationsRuleRepository = recommendationsRuleRepository;
    }

    /**
     * Метод для записи в БД PostgreSQL объекта recommendationWithRules
     * @return Возвращает внесённый объект recommendationWithRules
     */
    public RecommendationWithRules createRecommendationRules(RecommendationWithRules recommendationWithRules) {
        System.out.println("recommendationWithRules: " + recommendationWithRules);
        recommendationWithRules.getRuleRequirements().stream()
                .forEach(ruleRequirements -> ruleRequirements.setRecommendationWithRules(recommendationWithRules));
        recommendationWithRules.setRecommendationCounter(new RecommendationCounter(recommendationWithRules));
        return recommendationsRuleRepository.save(recommendationWithRules);
    }

    /**
     * Метод для извлечения из БД PostgreSQL через репозиторий списка объектов рекомендаций
     * @return Возвращает список объектов recommendationWithRules
     */
//    @Cacheable("Recommendations")
    public List<RecommendationWithRules> getAllRecommendationWithRules() {
        return recommendationsRuleRepository.findAll();
    }

    /**
     * Метод для удаления из БД PostgreSQL через репозиторий объекта рекомендации
     * @param id Идентификатор рекомендации
     * @return Возвращает булевое значение
     */
    public boolean deleteById(Long id) {
        if (recommendationsRuleRepository.existsById(id)) {
            recommendationsRuleRepository.deleteById(id);
            return true;
        }
        return false;
    }


}


