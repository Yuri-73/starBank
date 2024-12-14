package com.example.starBank.services;

import com.example.starBank.model.RecommendationCounter;
import com.example.starBank.repositories.RecommendationCounterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationCounterService {
    private final RecommendationCounterRepository counterRepositoryJPA;


    public RecommendationCounterService(RecommendationCounterRepository counterRepositoryJPA) {
        this.counterRepositoryJPA = counterRepositoryJPA;
    }

    public List<RecommendationCounter> getCounterList() {
        return counterRepositoryJPA.findAll();
    }

//    public void makeCounter(RecommendationWithRules recommendationWithRules) {
//        counterRepository.save(new RecommendationCounter(recommendationWithRules));
//    }
}
