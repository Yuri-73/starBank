package com.example.starBank.controllers;

import com.example.starBank.model.Recommendation;
import com.example.starBank.services.RecommendationRuleService;
import com.example.starBank.services.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Yuri-73
 */
@RestController
@RequestMapping("/recommendation")

public class RecommendationController {

    private final RecommendationService service;
    private final RecommendationRuleService ruleService;

    public RecommendationController(RecommendationService service, RecommendationRuleService ruleService) {
        this.service = service;
        this.ruleService = ruleService;
    }

    @Operation(
            summary = "Тестовая проверка функционирования БД JDBC (этап-1 работы)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Значение amount указывает на валидное подключение к JDBC драйверу",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = Recommendation.class))
                            )
                    )
            },
            tags = "recommendations"
    )
    @GetMapping("/test/{id}")
    public int test(@Parameter(description = "Идентификатор клиента",
            example = "1f9b149c-6577-448a-bc94-16bea229b71a") @PathVariable UUID id) {
        return service.get(id);
    }


    @Operation(
            summary = "Поиск внебазовых рекомендаций по идентификатору клиента (этап-1 работы)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденные рекомендации без использования БД",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = Recommendation.class))
                            )
                    )
            },
            tags = "recommendations"
    )
    @GetMapping("{id}")
    public ResponseEntity<String> getUser(@Parameter(description = "Идентификатор клиента",
            example = "1f9b149c-6577-448a-bc94-16bea229b71a")
                                              @PathVariable UUID id) {
        return ResponseEntity.ok("user_id: " + id + ",\n" + "recommendations: " + service.getRecommendation(id).toString());
    }

    @Operation(
        summary = "Поиск базовых рекомендаций по идентификатору клиента (этап-2 работы)",
        responses = {
             @ApiResponse(
                     responseCode = "200",
                     description = "Найденные рекомендации",
                     content = @Content(
                             array = @ArraySchema(schema = @Schema(implementation = Recommendation.class))
                     )
             )
        },
        tags = "recommendations"
    )
    @GetMapping("/new/{id}")
    public ResponseEntity<String> getRecommendationForUser(@Parameter(description = "Идентификатор клиента",
            example = "1f9b149c-6577-448a-bc94-16bea229b71a")
                                                               @PathVariable UUID id) {
        return ResponseEntity.ok("user_id: " + id + ",\n" + "recommendations: " + service.getRecommendation(id, ruleService.getAllRecommendationWithRules()).toString());
    }
}
