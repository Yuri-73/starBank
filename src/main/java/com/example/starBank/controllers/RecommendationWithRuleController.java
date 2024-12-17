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

/**
 * @author Yuri-73
 */
@RestController
@RequestMapping("/rule")
public class RecommendationWithRuleController {

    private final RecommendationRuleService recommendationRuleService;

    public RecommendationWithRuleController(RecommendationRuleService recommendationRuleService) {
        this.recommendationRuleService = recommendationRuleService;
    }

    /**
     * Метод получения объекта рекомендации для записи его в БД
     * @param recommendationWithRules объект рекомендации
     * @return Возвращает записанный в БД объект рекомендации.
     */
    @PostMapping()
    public ResponseEntity<RecommendationWithRules> createRecommendationWithRules(@RequestBody RecommendationWithRules recommendationWithRules) {
        System.out.println("recommendationWithRules-1: " + recommendationWithRules);
        RecommendationWithRules recommendationWithRules1 = recommendationRuleService.createRecommendationRules(recommendationWithRules);
        return ResponseEntity.ok(recommendationWithRules1);
    }

    /**
     * Метод получения полного списка рекомендаций
     * @return Возвращает список рекомендации из БД PostgreSQL
     */
    @GetMapping()
    public ResponseEntity<Collection<RecommendationWithRules>> getAllRecommendationWithRules() {
        return ResponseEntity.ok(recommendationRuleService.getAllRecommendationWithRules());
    }

    /**
     * Метод удаления рекомендации из БД
     * @param id Идентификатор клиента банка PostgreSQL
     * @return Возвращает статус 204 удалнной рекомендации из БД PostgreSQL или ошибку 400
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<RecommendationWithRules> deleteById(Long id) {
        if (!recommendationRuleService.deleteById(id))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.status(204).build();
    }
}
