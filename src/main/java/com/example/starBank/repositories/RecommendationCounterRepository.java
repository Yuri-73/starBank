package com.example.starBank.repositories;

import com.example.starBank.model.RecommendationCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecommendationCounterRepository extends JpaRepository<RecommendationCounter, Long> {


    @Query(value = "UPDATE recommendation_counter SET counter = counter + 1 WHERE recommendation_with_rules_id = ?1 ",nativeQuery = true)
    RecommendationCounter findByRecommendationWithRulesIdAndIncrementCounter(@Param("id") Long id);

}
