package com.example.starBank.repositories;

import com.example.starBank.model.CounterForShow;
import com.example.starBank.model.RecommendationCounter;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Chowo
 */
public interface RecommendationCounterRepository extends JpaRepository<RecommendationCounter, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE recommendation_counter SET counter = counter + 1 WHERE recommendation_with_rules_id = ?1 ",nativeQuery = true)
    void findByRecommendationWithRulesIdAndIncrementCounter(@Param("id") Long id);

    @Query(value = "SELECT * FROM recommendation_counter", nativeQuery = true)
    List<RecommendationCounter> getCounters ();

}
