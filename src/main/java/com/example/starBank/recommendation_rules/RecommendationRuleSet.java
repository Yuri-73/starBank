package com.example.starBank.recommendation_rules;

import com.example.starBank.model.Recommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    public Optional<Recommendation> getRecommendationByRule(UUID uuid);
}
