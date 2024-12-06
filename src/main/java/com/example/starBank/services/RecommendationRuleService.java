package com.example.starBank.services;

import com.example.starBank.model.RecommendationWithRules;
import com.example.starBank.model.RuleRequirements;
import com.example.starBank.repositories.QueryByConditionRuleRepository;
import com.example.starBank.repositories.RecommendationsRuleRepository;
import com.example.starBank.repositories.RuleRequirementsRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationRuleService {

    private RecommendationsRuleRepository recommendationsRuleRepository;

    private SimpleCreditRuleService simpleCreditRuleService;

    public RecommendationRuleService(RecommendationsRuleRepository recommendationsRuleRepository, SimpleCreditRuleService simpleCreditRuleService) {
        this.recommendationsRuleRepository = recommendationsRuleRepository;
        this.simpleCreditRuleService = simpleCreditRuleService;
    }

    public RecommendationWithRules createRecommendationRules(RecommendationWithRules recommendationWithRules) {
        System.out.println("recommendationWithRules: " + recommendationWithRules);
        return recommendationsRuleRepository.save(recommendationWithRules);
    }

    public RecommendationWithRules findRecommendationWithRules(Long id) {
        RecommendationWithRules recommendationWithRules = recommendationsRuleRepository.findById(id).orElse(null);
        System.out.println("recommendationWithRules-5: " + recommendationWithRules.getRuleRequirements());
        return recommendationWithRules;
    }

    public List<RecommendationWithRules> getAllRecommendationWithRules() {
        return recommendationsRuleRepository.findAll();
    }

    public List<RecommendationWithRules> getRecommendationRules(UUID id) {
        List<RecommendationWithRules> recommendationWithRules = getAllRecommendationWithRules();
        List<RecommendationWithRules> recommendationsByRules = new ArrayList<>();
        for (RecommendationWithRules recom : recommendationWithRules) {
            if (simpleCreditRuleService.recommendationSimpleCredit(id, recom)) {
                recommendationsByRules.add(recom);
            }
        }
        if (recommendationsByRules.isEmpty()) {
            recommendationsByRules.add(new RecommendationWithRules(0l, "Рекомендуемых продуктов нет", null, null));
        }
        return recommendationsByRules;
    }
}


