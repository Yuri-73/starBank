--liquibase formatted sql

--changeset Yuri:1

create table if not exists public.recommendation_with_rules
(
    id         BIGSERIAL PRIMARY KEY,
    name       varchar(255),
    product_id uuid,
    text       varchar(4000)
    );


--changeset Yuri:2

create table if not exists public.rule_requirements
(
    id            BIGSERIAL PRIMARY KEY,
    arguments     varchar(255),
    negate        boolean,
    query         varchar(255),
    recommendation_with_rules_id bigint constraint recommendation_with_rules_id_fk references public.recommendation_with_rules
    );

--changeset vhodakovskiy:1

create table if not exists public.recommendation_counter
(
    id                              BIGSERIAL PRIMARY KEY,
    recommendation_with_rules_id    bigint constraint recommendation_with_rules_id_fk references public.recommendation_with_rules,
    counter                         BIGINT
    )




