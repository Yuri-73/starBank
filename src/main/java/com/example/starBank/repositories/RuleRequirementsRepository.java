package com.example.starBank.repositories;

import com.example.starBank.model.RecommendationWithRules;
import com.example.starBank.model.RuleRequirements;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Yuri-73
 */
public interface RuleRequirementsRepository extends JpaRepository<RuleRequirements, Long> {
    Optional<RuleRequirements> findByQuery(String userOf);
}
