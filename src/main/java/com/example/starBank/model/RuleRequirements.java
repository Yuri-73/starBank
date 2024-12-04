package com.example.starBank.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "ruleRequirements")
public class RuleRequirements {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "query")
    String query;

    @Column(name = "arguments")
    String arguments;

    @Column(name = "negate")
    boolean negate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recommendationWithRules_id")
    private RecommendationWithRules recommendationWithRules;

    public RuleRequirements(Long id, String query, String arguments, boolean negate, RecommendationWithRules recommendationWithRules) {
        this.id = id;
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
        this.recommendationWithRules = recommendationWithRules;
    }

    public RuleRequirements() {
    }

    public RecommendationWithRules getRecommendationWithRules() {
        return recommendationWithRules;
    }

    public void setRecommendationWithRules(RecommendationWithRules recommendationWithRules) {
        this.recommendationWithRules = recommendationWithRules;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getQuery() {
        return query;
    }

    public String getArguments() {
        return arguments;
    }

    public boolean isNegate() {
        return negate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuleRequirements that = (RuleRequirements) o;
        return negate == that.negate && Objects.equals(query, that.query) && Objects.equals(arguments, that.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, arguments, negate);
    }

    @Override
    public String toString() {
        return "RuleRequirements{" +
                "query='" + query + '\'' +
                ", arguments='" + arguments + '\'' +
                ", negate=" + negate +
                '}';
    }
}