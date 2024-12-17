package com.example.starBank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 * @author Chowo
 */
@Entity
public class RecommendationCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @OneToOne
    private RecommendationWithRules recommendationWithRules;

    /**
     * Счётчик для рекомендации
     */
    @Column(name = "counter")
    private Long counter;


    public RecommendationCounter(RecommendationWithRules recommendationWithRules) {
        this.counter = 0L;
        this.recommendationWithRules = recommendationWithRules;
    }

    public RecommendationCounter() {

    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public RecommendationWithRules getRecommendationWithRules() {
        return recommendationWithRules;
    }

    public void setRecommendationWithRules(RecommendationWithRules recommendationWithRules) {
        this.recommendationWithRules = recommendationWithRules;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void incrementCounter() {
        Long counter = getCounter() + 1;
        System.out.println("counter = " + counter + "?");
        setCounter( counter);
        System.out.println("counter = " + getCounter());
    }

    @Override
    public String toString() {
        return "RecommendationCounter{" +
                "rule_id=" + recommendationWithRules.getId() +
                ", counter=" + counter +
                '}';
    }
}
