package com.example.starBank.services;

import com.example.starBank.model.RecommendationWithRules;
import com.example.starBank.model.RuleRequirements;
import com.example.starBank.repositories.RecommendationsRuleRepository;
import com.example.starBank.repositories.RuleRequirementsRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationRuleService {

    private RecommendationsRuleRepository recommendationsRuleRepository;
    private RuleRequirementsRepository ruleRequirementsRepository;
    private SimpleCreditRuleService simpleCreditRuleService;

    public RecommendationRuleService(RecommendationsRuleRepository recommendationsRuleRepository,
                                     RuleRequirementsRepository ruleRequirementsRepository,
                                     SimpleCreditRuleService simpleCreditRuleService) {
        this.recommendationsRuleRepository = recommendationsRuleRepository;
        this.simpleCreditRuleService = simpleCreditRuleService;
        this.ruleRequirementsRepository = ruleRequirementsRepository;
    }

    public RuleRequirements createRules(RuleRequirements ruleRequirements) {
        System.out.println("ruleRequirements2: " + ruleRequirements);

        return ruleRequirementsRepository.save(ruleRequirements);
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
            recommendationsByRules.add(new RecommendationWithRules());
        }
        return recommendationsByRules;
    }


}


