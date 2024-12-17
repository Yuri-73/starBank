package com.example.starBank.repositories;

import com.example.starBank.model.RecommendationWithRules;
import com.example.starBank.model.RuleRequirements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Yuri-73
 */
@Repository
public interface RecommendationsRuleRepository extends JpaRepository<RecommendationWithRules, Long> {
}
