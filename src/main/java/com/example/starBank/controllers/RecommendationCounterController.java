package com.example.starBank.controllers;

import com.example.starBank.model.RecommendationCounter;
import com.example.starBank.services.RecommendationCounterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rule/stats")
public class RecommendationCounterController {
    private final RecommendationCounterService counterService;

    public RecommendationCounterController(RecommendationCounterService counterService) {
        this.counterService = counterService;
    }

    @GetMapping
    public ResponseEntity<List<RecommendationCounter>> getCounters() {
        return ResponseEntity.ok(counterService.getCounterList());
    }
}


