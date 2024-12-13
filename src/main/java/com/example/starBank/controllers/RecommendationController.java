package com.example.starBank.controllers;

import com.example.starBank.services.RecommendationRuleService;
import com.example.starBank.services.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/recommendation")

public class RecommendationController {

    private final RecommendationService service;
    private final RecommendationRuleService ruleService;

    public RecommendationController(RecommendationService service, RecommendationRuleService ruleService) {
        this.service = service;
        this.ruleService = ruleService;
    }
    /**
     * Метод тестовой проверки функционирования БД JDBC
     * @param id для поиска по id клиента банка
     * @return Выдает четырёхразрядное число, что служит правильному подключению к JDBC драйверу
     */
    @GetMapping("/test/{id}")
    public int test(@PathVariable UUID id) {
        return service.get(id);
    }

    /**
     * Метод формирования рекомендаций клиенту по пользованию продуктами банка
     * @param id для поиска по id клиента банка
     * @return Возвращает клиенту список рекомендаций или пустой список
     */
    @GetMapping("{id}")
    public ResponseEntity<String> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok("user_id: " + id + ",\n" + "recommendations: " + service.getRecommendation(id).toString());
    }

    /**
     * Метод формирования динамических рекомендаций клиенту по пользованию продуктами банка
     * @param id для поиска по id клиента банка
     * @return Возвращает клиенту список рекомендаций или пустой список
     */
    @GetMapping("/new/{id}")
    public ResponseEntity<String> getRecommendationForUser(@PathVariable UUID id) {
        return ResponseEntity.ok("user_id: " + id + ",\n" + "recommendations: " + service.getRecommendation(id, ruleService.getAllRecommendationWithRules()).toString());
    }
}
