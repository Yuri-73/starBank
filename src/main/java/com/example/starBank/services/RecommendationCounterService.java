package com.example.starBank.services;

import com.example.starBank.model.RecommendationCounter;
import com.example.starBank.model.RecommendationWithRules;
import com.example.starBank.repositories.RecommendationCounterRepository;
import com.example.starBank.repositories.RecommendationCounterRepositoryJPA;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationCounterService {
    private final RecommendationCounterRepositoryJPA counterRepositoryJPA;
    private final RecommendationCounterRepository counterRepository;

    public RecommendationCounterService(RecommendationCounterRepository counterRepository, RecommendationCounterRepositoryJPA counterRepositoryJPA) {
        this.counterRepository = counterRepository;
        this.counterRepositoryJPA = counterRepositoryJPA;
    }

    public List<RecommendationCounter> getCounterList() {
        return counterRepositoryJPA.findAll();
    }

//    public void makeCounter(RecommendationWithRules recommendationWithRules) {
//        counterRepository.save(new RecommendationCounter(recommendationWithRules));
//    }
}
