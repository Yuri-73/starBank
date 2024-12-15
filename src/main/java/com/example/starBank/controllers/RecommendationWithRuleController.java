package com.example.starBank.controllers;

import com.example.starBank.model.RecommendationWithRules;
import com.example.starBank.model.RuleRequirements;
import com.example.starBank.services.RecommendationRuleService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rule")
public class RecommendationWithRuleController {

    private final RecommendationRuleService recommendationRuleService;

    public RecommendationWithRuleController(RecommendationRuleService recommendationRuleService) {
        this.recommendationRuleService = recommendationRuleService;
    }

    @PostMapping()
    public ResponseEntity<RecommendationWithRules> createRecommendationWithRules(@RequestBody RecommendationWithRules recommendationWithRules) {
        System.out.println("recommendationWithRules-1: " + recommendationWithRules);
        RecommendationWithRules recommendationWithRules1 = recommendationRuleService.createRecommendationRules(recommendationWithRules);
        return ResponseEntity.ok(recommendationWithRules1);
    }

    @GetMapping()
    public ResponseEntity<Collection<RecommendationWithRules>> getAllRecommendationWithRules() {
        return ResponseEntity.ok(recommendationRuleService.getAllRecommendationWithRules());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RecommendationWithRules> deleteById(Long id) {
        if (!recommendationRuleService.deleteById(id))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.status(204).build();
    }
}
