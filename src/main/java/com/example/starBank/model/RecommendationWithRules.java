package com.example.starBank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.util.*;

@Entity
@Table(name = "recommendation_with_rules")
public class RecommendationWithRules {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)



    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "productId")
    private UUID productId;

    @Column(name = "text")
    private String text;

    @OneToMany()
    @JoinColumn()
    private List<RuleRequirements> ruleRequirements;

    public RecommendationWithRules(Long id, String name, UUID productId, String text) {
        this.id = id;
        this.name = name;
        this.productId = productId;
        this.text = text;
    }

    public RecommendationWithRules() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getText() {
        return text;
    }

    public List<RuleRequirements> getRuleRequirements() {
        return ruleRequirements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendationWithRules that = (RecommendationWithRules) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(productId, that.productId) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, productId, text);
    }

    @Override
    public String toString() {
        return "RecommendationWithRules{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idProduct=" + productId +
                ", text='" + text + '\'' +
                '}';
    }
}
