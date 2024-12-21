package com.example.starBank.controllers;

import com.example.starBank.model.RecommendationWithRules;
import com.example.starBank.model.RuleRequirements;
import com.example.starBank.services.RecommendationRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
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

    @Operation(summary = "Внесение в БД JPA PostgreSQL новой рекомендации с динамическим правилом (этап-2 работы)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Созданная рекомендация с динамическим правилом",
                            content = @Content(
                                    schema = @Schema(implementation = RecommendationWithRules.class)
                            )
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Новая рекомендация с динамическим правилом",
                    content = @Content(
//                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RecommendationWithRules.class)
                    )
            )
    )
    @PostMapping()
    public ResponseEntity<RecommendationWithRules> createRecommendationWithRules(@RequestBody RecommendationWithRules recommendationWithRules) {
        System.out.println("recommendationWithRules-1: " + recommendationWithRules);
        RecommendationWithRules recommendationWithRules1 = recommendationRuleService.createRecommendationRules(recommendationWithRules);
        return ResponseEntity.ok(recommendationWithRules1);
    }

    @Operation(summary = "Получение всего списка рекомендаций из БД JPA PostgreSQL (этап-2 работы)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденные рекомендации",
                            content = @Content(
                                    schema = @Schema(implementation = RecommendationWithRules.class)
                            )
                    )
            })
    @GetMapping()
    public ResponseEntity<Collection<RecommendationWithRules>> getAllRecommendationWithRules() {
        return ResponseEntity.ok(recommendationRuleService.getAllRecommendationWithRules());
    }

    @Operation(summary = "Удаление рекомендации из БД JPA PostgreSQL (этап-2 работы)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаление рекомендации - статус 204, при неудалении - статус 400",
                            content = @Content(
                                    schema = @Schema(implementation = RecommendationWithRules.class)
                            )
                    )
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<RecommendationWithRules> deleteById(@Parameter(description = "Идентификатор рекомендации",
            example = "1") Long id) {
        if (!recommendationRuleService.deleteById(id))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.status(204).build();
    }
}
