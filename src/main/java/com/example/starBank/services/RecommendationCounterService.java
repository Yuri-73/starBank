package com.example.starBank.services;

import com.example.starBank.model.CounterForShow;
import com.example.starBank.model.RecommendationCounter;
import com.example.starBank.repositories.RecommendationCounterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationCounterService {
    private final RecommendationCounterRepository counterRepository;


    public RecommendationCounterService(RecommendationCounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    public List<CounterForShow> getCounterList() {
        List<RecommendationCounter> listOfCounters = counterRepository.findAll();
        List<CounterForShow> countersForShow = listOfCounters.stream().map(e -> new CounterForShow(e.getRecommendationWithRules().getId(), e.getCounter())).toList();
        return countersForShow;
    }

//    public void makeCounter(RecommendationWithRules recommendationWithRules) {
//        counterRepository.save(new RecommendationCounter(recommendationWithRules));
//    }
}
