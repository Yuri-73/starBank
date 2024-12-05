package com.example.starBank.controllers;

import com.example.starBank.model.RecommendationWithRules;
import com.example.starBank.services.RecommendationRuleService;
import com.example.starBank.services.RecommendationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("rule")
public class RecommendationRuleController {

    private final RecommendationRuleService recommendationRuleService;

    public RecommendationRuleController(RecommendationRuleService recommendationRuleService) {
        this.recommendationRuleService = recommendationRuleService;
    }

    @PostMapping
    public ResponseEntity<RecommendationWithRules> createRecommendationWithRules(@RequestBody RecommendationWithRules recommendationWithRules) {
        System.out.println("recommendationWithRules-1: " + recommendationWithRules);
        RecommendationWithRules recommendationWithRules1 = recommendationRuleService.createRecommendationRuleService(recommendationWithRules);
        return ResponseEntity.ok(recommendationWithRules1);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecommendationWithRules> findRecommendationWithRules(@PathVariable Long id) {
        if (recommendationRuleService.findRecommendationWithRules(id) == null) {
            /**
             * Вывод 404 по варианту 1
             */
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(recommendationRuleService.findRecommendationWithRules(id));
    }

    @GetMapping() // GET http://localhost:8090/student
    public ResponseEntity<Collection<RecommendationWithRules>> getAllRecommendationWithRules() {
        return ResponseEntity.ok(recommendationRuleService.getAllRecommendationWithRules());
    }
}
