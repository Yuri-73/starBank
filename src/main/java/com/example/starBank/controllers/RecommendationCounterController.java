package com.example.starBank.controllers;

import com.example.starBank.model.CounterForShow;
import com.example.starBank.model.RecommendationCounter;
import com.example.starBank.services.RecommendationCounterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Chowo
 */
@RestController
@RequestMapping("/rule/stats")
public class RecommendationCounterController {
    private final RecommendationCounterService counterService;

    public RecommendationCounterController(RecommendationCounterService counterService) {
        this.counterService = counterService;
    }

    /**
     * Метод подсчёта всех определённых рекомендаций
     * @return Возвращает список  из количества выданных рекомендаций по их идентификатору.
     */
    @GetMapping
    public ResponseEntity<List<CounterForShow>> getCounters() {
        return ResponseEntity.ok(counterService.getCounterList());
    }
}


