package com.example.starBank.repositories;

import com.example.starBank.model.RecommendationWithRules;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationsRuleRepository extends JpaRepository<RecommendationWithRules, Long> {

}
