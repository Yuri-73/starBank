package com.example.starBank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

/**
 * @author Yuri-73
 */
@Entity
@Table(name = "rule_requirements")
public class RuleRequirements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя правила
     */
    @Column(name = "query")
    String query;

    /**
     * Аргументы для правила
     */
    @Column(name = "arguments")
    String arguments;

    /**
     * Инверсный знак для выбора правила
     */
    @Column(name = "negate")
    boolean negate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
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

    public void setQuery(String query) {
        this.query = query;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
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
