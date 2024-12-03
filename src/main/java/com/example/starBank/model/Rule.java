package com.example.starBank.model;

import java.util.Arrays;
import java.util.Objects;

public class Rule {
    String query;
    String[] arguments;
    boolean negate;

    public Rule(String query, String[] arguments, boolean negate) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
    }

    public String getQuery() {
        return query;
    }

    public String[] getArguments() {
        return arguments;
    }

    public boolean isNegate() {
        return negate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return negate == rule.negate && Objects.equals(query, rule.query) && Objects.deepEquals(arguments, rule.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, Arrays.hashCode(arguments), negate);
    }

    @Override
    public String toString() {
        return "Rule{" +
                "query='" + query + '\'' +
                ", arguments=" + Arrays.toString(arguments) +
                ", negate=" + negate +
                '}';
    }
}
