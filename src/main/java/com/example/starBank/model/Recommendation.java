package com.example.starBank.model;

import java.util.Objects;
import java.util.UUID;

public class Recommendation {
    /**
     * Имя рекомендации
     */
    private String name;
    /**
     * Порядковый номер рекомендации
     */
    private UUID id;

    /**
    * Текст рекомендации
    */
    private String text;

    public Recommendation(UUID id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }

    public Recommendation() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation that = (Recommendation) o;
        return Objects.equals(name, that.name) && Objects.equals(id, that.id) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, text);
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
