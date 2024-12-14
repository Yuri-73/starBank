package com.example.starBank.repositories;

import com.example.starBank.model.RecommendationCounter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationCounterRepositoryJPA extends JpaRepository<RecommendationCounter, Long> {

}
