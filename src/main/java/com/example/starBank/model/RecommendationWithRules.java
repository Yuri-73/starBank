package com.example.starBank.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "recommendation_with_rules")
public class RecommendationWithRules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "text")
    private String text;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recommendationWithRules", orphanRemoval = true)
    private List<RuleRequirements> ruleRequirements;

    public RecommendationWithRules(String name, String text, UUID productId) {
        this.name = name;
        this.productId = productId;
        this.text = text;
    }

    public RecommendationWithRules() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public void setText(String text) {
        this.text = text;
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

    public void setRuleRequirements(List<RuleRequirements> ruleRequirements) {
        this.ruleRequirements = ruleRequirements;
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
