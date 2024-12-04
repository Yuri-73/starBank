package com.example.starBank.services;

import com.example.starBank.model.RecommendationWithRules;
import com.example.starBank.model.RuleRequirements;
import com.example.starBank.repositories.RecommendationsRuleRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class RecommendationRuleService {

    private RecommendationsRuleRepository recommendationsRuleRepository;

    public RecommendationRuleService(RecommendationsRuleRepository recommendationsRuleRepository) {
        this.recommendationsRuleRepository = recommendationsRuleRepository;
    }

    public RecommendationWithRules createRecommendationRuleService(RecommendationWithRules recommendationWithRules) {
        System.out.println("recommendationWithRules: " +recommendationWithRules);
        return recommendationsRuleRepository.save(recommendationWithRules);
    }

    public RecommendationWithRules findRecommendationWithRules(Long id) {
        RecommendationWithRules recommendationWithRules = recommendationsRuleRepository.findById(id).orElse(null);
        System.out.println("recommendationWithRules-5: " +recommendationWithRules.getRuleRequirements());
        return recommendationWithRules;
    }

//    public List<RecommendationWithRules> getAllRecommendationWithRules() {
//        return recommendationsRuleRepository.findAll();
//    }
}

//    public Collection<Student> getAllStudent() {
//        logger.info("Старт метода getAllStudent");
//        return studentRepository.findAll();
//    }
