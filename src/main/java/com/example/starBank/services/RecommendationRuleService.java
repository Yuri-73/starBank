package com.example.starBank.services;

import com.example.starBank.model.RecommendationWithRules;
import com.example.starBank.model.RuleRequirements;
import com.example.starBank.repositories.RecommendationsRuleRepository;
import com.example.starBank.repositories.RuleRequirementsRepository;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationRuleService {

    private final RecommendationsRuleRepository recommendationsRuleRepository;
    private final RuleRequirementsRepository ruleRequirementsRepository;

    public RecommendationRuleService(RecommendationsRuleRepository recommendationsRuleRepository,
                                     RuleRequirementsRepository ruleRequirementsRepository) {
        this.recommendationsRuleRepository = recommendationsRuleRepository;
        this.ruleRequirementsRepository = ruleRequirementsRepository;
    }

    public RecommendationWithRules createRecommendationRules(RecommendationWithRules recommendationWithRules) {
        System.out.println("recommendationWithRules: " + recommendationWithRules);
        recommendationWithRules.getRuleRequirements().stream()
                .forEach(ruleRequirements -> ruleRequirements.setRecommendationWithRules(recommendationWithRules));
        return recommendationsRuleRepository.save(recommendationWithRules);
    }


    public RecommendationWithRules findRecommendationWithRules(Long id) {
        RecommendationWithRules recommendationWithRules = recommendationsRuleRepository.findById(id).orElse(null);
        System.out.println("recommendationWithRules-5: " + recommendationWithRules.getRuleRequirements());
        return recommendationWithRules;
    }

    @Cacheable("RecommendationWithRules")
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


