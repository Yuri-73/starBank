--liquibase formatted sql

--changeset Yuri:1
CREATE TABLE recommendationWithRules
(
    id        BIGSERIAL PRIMARY KEY,
    name      TEXT NOT NULL,
    productId TEXT NOT NULL,
    text      TEXT NOT NULL
);
--changeset Yuri:2
CREATE TABLE ruleRequirements
(
    id        BIGSERIAL PRIMARY KEY,
    query     TEXT NOT NULL,
    arguments TEXT NOT NULL,
    negate    BOOLEAN
);

