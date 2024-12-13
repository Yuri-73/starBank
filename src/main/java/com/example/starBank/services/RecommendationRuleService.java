package com.example.starBank.services;

import com.example.starBank.model.RecommendationWithRules;
import com.example.starBank.repositories.RecommendationsRuleRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationRuleService {

    private final RecommendationsRuleRepository recommendationsRuleRepository;

    public RecommendationRuleService(RecommendationsRuleRepository recommendationsRuleRepository) {
        this.recommendationsRuleRepository = recommendationsRuleRepository;
    }

    public RecommendationWithRules createRecommendationRules(RecommendationWithRules recommendationWithRules) {
        System.out.println("recommendationWithRules: " + recommendationWithRules);
        recommendationWithRules.getRuleRequirements().stream()
                .forEach(ruleRequirements -> ruleRequirements.setRecommendationWithRules(recommendationWithRules));
        return recommendationsRuleRepository.save(recommendationWithRules);
    }

    @Cacheable("Recommendations")
    public List<RecommendationWithRules> getAllRecommendationWithRules() {
        return recommendationsRuleRepository.findAll();
    }

    public boolean deleteById(Long id) {
        if (recommendationsRuleRepository.existsById(id)) {
            recommendationsRuleRepository.deleteById(id);
            return true;
        }
        return false;
    }


}


