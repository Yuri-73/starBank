package com.example.starBank.model;

import java.util.Objects;

public class CounterForShow {

    private Long rule_id;

    private Long counter;

    public CounterForShow(Long rule_id, Long counter) {
        this.rule_id = rule_id;
        this.counter = counter;
    }

    public CounterForShow() {

    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public Long getRule_id() {
        return rule_id;
    }

    public void setRule_id(Long rule_id) {
        this.rule_id = rule_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CounterForShow that = (CounterForShow) o;
        return Objects.equals(rule_id, that.rule_id) && Objects.equals(counter, that.counter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rule_id, counter);
    }

    @Override
    public String toString() {
        return "CounterForShow{" +
                "recommendation_id=" + rule_id +
                ", counter=" + counter +
                '}';
    }
}
