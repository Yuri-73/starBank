package com.example.starBank.model;

import java.util.Arrays;
import java.util.Objects;

public class RecommendationWithRules {

    private final Recommendation recommendation;
    private final Rule[] rules;

    public RecommendationWithRules(Recommendation recommendation, Rule[] rules) {
        this.recommendation = recommendation;
        this.rules = rules;
    }

    public Recommendation getRecommendation() {
        return recommendation;
    }

    public Rule[] getRules() {
        return rules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendationWithRules that = (RecommendationWithRules) o;
        return Objects.equals(recommendation, that.recommendation) && Objects.deepEquals(rules, that.rules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recommendation, Arrays.hashCode(rules));
    }

    @Override
    public String toString() {
        return "RecommendationWithRules{" +
                "recommendation=" + recommendation +
                ", rules=" + Arrays.toString(rules) +
                '}';
    }
}
