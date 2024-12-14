package com.example.starBank.repositories;

import com.example.starBank.model.RecommendationCounter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecommendationCounterRepository {

    private final JdbcTemplate jdbcTemplate;


    public RecommendationCounterRepository(@Qualifier("recommendationCounterJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public RecommendationCounter incrementCounter(Long id) {
        return jdbcTemplate.queryForObject(
                "UPDATE recommendation_counter SET counter = counter + 1 WHERE recommendation_with_rules_id = ?",
                RecommendationCounter.class, id);
    }
//    @Query(value = "UPDATE recommendation_counter SET counter = counter + 1 WHERE recommendation_with_rules_id = ?1 ",nativeQuery = true)
//    RecommendationCounter findByRecommendationWithRulesIdAndIncrementCounter(@Param("id") Long id);
    public List<RecommendationCounter> findAll() {
    return jdbcTemplate.queryForObject(
            "SELECT counter FROM recommendation_counter",
            List.class);
}
}
