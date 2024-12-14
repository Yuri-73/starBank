package com.example.starBank.services;

import com.example.starBank.model.RecommendationCounter;
import com.example.starBank.model.RecommendationWithRules;
import com.example.starBank.repositories.RecommendationCounterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationCounterService {
    private final RecommendationCounterRepository counterRepository;

    public RecommendationCounterService(RecommendationCounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    public List<RecommendationCounter> getCounterList() {
        return counterRepository.findAll();
    }

//    public void makeCounter(RecommendationWithRules recommendationWithRules) {
//        counterRepository.save(new RecommendationCounter(recommendationWithRules));
//    }
}
