--liquibase formatted sql

--changeset Yuri:1
CREATE TABLE recommendation_with_rules
(
    id        BIGSERIAL PRIMARY KEY,
    name      TEXT NOT NULL,
    productId TEXT NOT NULL,
    text      TEXT NOT NULL
);

--changeset Yuri:2
CREATE TABLE rule_requirements
(
    id        BIGSERIAL PRIMARY KEY,
    query     TEXT NOT NULL,
    arguments TEXT NOT NULL,
    negate    BOOLEAN,
    recommendation_with_rules_id BIGSERIAL NOT NULL
        constraint recommendation_with_rules_id_fk
        references recommendation_with_rules
);

